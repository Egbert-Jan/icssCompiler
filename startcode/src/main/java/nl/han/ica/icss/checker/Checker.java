package nl.han.ica.icss.checker;

import nl.han.ica.icss.ast.*;
import nl.han.ica.icss.ast.literals.*;

import java.awt.*;
import java.util.ArrayList;
import java.util.HashMap;


//CustomWidth := 250px;
//a {
//    height: 100px;
//    width: CustomWidth;
//}

//TODO: APENDIX GOED DOORLEZEN


interface ICSSProperties {
    String COLOR = "color";
    String BACKGROUND_COLOR = "background-color";
    String WIDTH = "width";
    String HEIGHT = "height";
}


public class Checker {

    private HashMap<String, VariableAssignment> declaredVariablesForScope = new HashMap<>();
    private HashMap<String, Expression> declaredVariablesPerType = new HashMap<>();

//    private IHANLinkedList<HashMap<String, ExpressionType>> variableTypes;

    public void check(AST ast) {
//        variableTypes = new HANLinkedList<>();
//        System.out.println(ast.toString());
        recursivelyCheckNode(ast.root);
    }


    private void recursivelyCheckNode(ASTNode node) {
//        System.out.println(node.getNodeLabel());

        storeVariableType(node);
        checkIfClauseCondition(node);
        checkIfColorsAreUsedInOperations(node);
        checkIfDeclarationsHaveCorrectType(node);

        //Store variables local scope
        ArrayList<String> variableScope = new ArrayList<>();

        //Loop through the tree recursively
        node.getChildren().forEach(childNode -> {

            if (childNode instanceof ElseClause) {
                for (String name : variableScope) {
                    declaredVariablesForScope.remove(name);
                }
            }

            checkIfVariableIsInScope(childNode, variableScope);

            recursivelyCheckNode(childNode);
        });

        //Remove variables from scope
        variableScope.forEach(name -> declaredVariablesForScope.remove(name));
    }

    private void storeVariableType(ASTNode node) {
        if (node instanceof VariableAssignment) {
            var assignment = (VariableAssignment) node;
            if(declaredVariablesPerType.containsKey(assignment.name.name)) {
                var literal = getVariableValueByExpression(declaredVariablesPerType.get(assignment.name.name));
                var rightLiteral = getVariableValueByExpression(assignment.expression);
                if (!literal.getClass().equals(rightLiteral.getClass())) {
                    assignment.setError("Can't assign a value of type " + rightLiteral.getClass() + " to a variable of type " + literal.getClass());
                    return;
                }
            }

            declaredVariablesPerType.put(assignment.name.name, assignment.expression);
        }
    }


    private Expression getVariableValueByName(String name) {
        return declaredVariablesPerType.get(name);
    }

    private Literal getVariableValueByExpression(Expression expression) {

        if (expression instanceof VariableReference) {
            var variable = (VariableReference) expression;

            var variableValue = getVariableValueByName(variable.name);
            if (variableValue instanceof Literal) {
                return (Literal) variableValue;
            } else if (variableValue instanceof VariableReference) {
                return getVariableValueByExpression(variableValue);
            } else if (variableValue instanceof Operation) {
                return getLiteralFromOperation((Operation) variableValue);
            }
        } else if (expression instanceof Literal) {
            return (Literal) expression;
        } else if(expression instanceof Operation) {
            return getLiteralFromOperation((Operation) expression);
        }

        return null;
    }

    private Literal getLiteralFromOperation(Operation operation) {
        var left = operation.lhs;
        var right = operation.rhs;

        if (left instanceof VariableReference) {
            left = getVariableValueByExpression(left);
        }

        if (right instanceof VariableReference) {
            right = getVariableValueByExpression(right);
        }

        if (left instanceof BoolLiteral || right instanceof BoolLiteral)
            operation.setError("Bool is invalid operation");

        if (left instanceof ColorLiteral || right instanceof ColorLiteral)
            operation.setError("Color is an invalid operation");


        if (left instanceof Operation) {
            left = getLiteralFromOperation((Operation) left);
        }

        if (right instanceof Operation) {
            right = getLiteralFromOperation((Operation) right);
        }

        //DOES ONLY RETURN CORRECT LITERAL TYPE. ONLY FOR CHECKING TYPEEEE

        if (left instanceof PixelLiteral && (right instanceof PixelLiteral || right instanceof ScalarLiteral))
            return (PixelLiteral) left;

        if (right instanceof PixelLiteral && (left instanceof PixelLiteral || left instanceof ScalarLiteral))
            return (PixelLiteral) right;


        if (left instanceof PercentageLiteral && (right instanceof PercentageLiteral || right instanceof ScalarLiteral))
            return (PercentageLiteral) left;

        if (right instanceof PercentageLiteral && (left instanceof PercentageLiteral || left instanceof ScalarLiteral))
            return (PercentageLiteral) right;


        return null;
    }
//    div {
//        width: 50px + 2 * 10px - 2px;
//    }

    //Logic should be moved to parser -> g4 file?
    private void checkIfDeclarationsHaveCorrectType(ASTNode node) {
        if (node instanceof Declaration) {
            var declaration = (Declaration) node;

            var propName = declaration.property.name.replace(":", ""); //Whyyyy is there a collonnnn???
            var propValue = getVariableValueByExpression(declaration.expression);

            var propIsColor = propName.equals(ICSSProperties.COLOR) || propName.equals(ICSSProperties.BACKGROUND_COLOR);
            if (propIsColor && !(propValue instanceof ColorLiteral)) {
                node.setError("Color property can only have a color as value");
                return;
            }

            var propIsSize = propName.equals(ICSSProperties.WIDTH) || propName.equals(ICSSProperties.HEIGHT);
            if (propIsSize &&
                    !(propValue instanceof PercentageLiteral
                    || propValue instanceof PixelLiteral)
            ) {
                node.setError("Size must be a percentage, pixel, or scalar value");
            }

        }
    }

    private void checkIfColorsAreUsedInOperations(ASTNode node) {
        if (node instanceof Operation) {
            var operation = (Operation) node;

            if (operation.lhs instanceof ColorLiteral || operation.rhs instanceof ColorLiteral) {
                node.setError("Operation can not include colors");
            }
        }
    }

    private void checkIfVariableIsInScope(ASTNode childNode, ArrayList<String> variableScope) {
        if (childNode instanceof VariableAssignment) {
            var variable = (VariableAssignment) childNode;
            if(!declaredVariablesForScope.containsKey(variable.name.name)) {
                declaredVariablesForScope.put(variable.name.name, variable);
                variableScope.add(variable.name.name);
            }
        }

        if (childNode instanceof VariableReference) {
            var variable = (VariableReference) childNode;
            if (!declaredVariablesForScope.containsKey(variable.name)) {
                childNode.setError("Variable '" + variable.name + "' out of scope or not declared");
            }
        }
    }

    private void checkIfClauseCondition(ASTNode node) {
        if (node instanceof IfClause) {
            var ifClause = (IfClause) node;

            if (!(ifClause.conditionalExpression instanceof BoolLiteral) && !(getVariableValueByExpression(ifClause.conditionalExpression) instanceof BoolLiteral))
                node.setError("If clause condition is not a boolean");
        }
    }
}

//MyVar := FALSE;
//
//VarB := MyVar;
//
//VarC := 10px;
//
//a {
//    if[VarB] {
//        height: VarC;
//    }
//}
