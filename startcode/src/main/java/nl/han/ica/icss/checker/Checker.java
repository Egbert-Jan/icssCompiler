package nl.han.ica.icss.checker;

import nl.han.ica.icss.ast.*;
import nl.han.ica.icss.ast.literals.*;

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
        System.out.println(ast.toString());
        recursivelyCheckNode(ast.root);
    }


    private void recursivelyCheckNode(ASTNode node) {
//        System.out.println(node.getNodeLabel());

        storeVariableType(node);

        checkIfClauseCondition(node);

        checkIfColorsAreUsedInOperations(node);

        checkIfDeclarationsHaveCorrectType(node);

        //Add variables to scope
        ArrayList<String> variableScope = new ArrayList<>();
        node.getChildren().forEach(childNode -> {

            checkIfVariableIsInScope(childNode, variableScope);

            recursivelyCheckNode(childNode);
        });

        //Remove variables from scope
        variableScope.forEach(name -> { declaredVariablesForScope.remove(name); });
    }

    private void storeVariableType(ASTNode node) {
        if(node instanceof VariableAssignment) {
            var assignment = (VariableAssignment) node;
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
            } else if(variableValue instanceof VariableReference) {
                return getVariableValueByExpression(variableValue);
            }
        }

        return null;
    }



    private void checkIfDeclarationsHaveCorrectType(ASTNode node) {
        if (node instanceof Declaration) {
            var declaration = (Declaration) node;

            var propName = declaration.property.name.replace(":", ""); //Whyyyy is there a collonnnn???
            var propValue = declaration.expression;

            var propIsColor = propName.equals(ICSSProperties.COLOR) || propName.equals(ICSSProperties.BACKGROUND_COLOR);
            if(propIsColor && !(propValue instanceof ColorLiteral)) {
                node.setError("Color property can only have a color as value");
                return;
            }

            var propIsSize = propName.equals(ICSSProperties.WIDTH) || propName.equals(ICSSProperties.HEIGHT);
            if(propIsSize && !(propValue instanceof PercentageLiteral) && !(propValue instanceof PixelLiteral) && !(propValue instanceof ScalarLiteral)) {
                node.setError("Size must be a percentage, pixel, or scalar value");
            }

        }
    }

    private void checkIfColorsAreUsedInOperations(ASTNode node) {
        if (node instanceof Operation) {
            var operation = (Operation) node;

            if(operation.lhs instanceof ColorLiteral || operation.rhs instanceof ColorLiteral) {
                node.setError("Operation can not include colors");
            }
        }
    }

    private void checkIfVariableIsInScope(ASTNode childNode, ArrayList<String> variableScope) {
        if(childNode instanceof VariableAssignment) {
            var variable = (VariableAssignment) childNode;
            declaredVariablesForScope.put(variable.name.name, variable);
            variableScope.add(variable.name.name);
        }

        if (childNode instanceof VariableReference) {
            var variable = (VariableReference) childNode;
            if (!declaredVariablesForScope.containsKey(variable.name)) {
                childNode.setError("Variable '" + variable.name + "' out of scope or not declared");
            }
        }
    }

    private void checkIfClauseCondition(ASTNode node) {
        //TODO: Check if it's a variable it's also a bool
        if(node instanceof IfClause) {
            var ifClause = (IfClause) node;

//            if (ifClause.conditionalExpression instanceof VariableReference) {
//                var variable = (VariableReference) ifClause.conditionalExpression;
//                if (!(getVariableByName(variable.name) instanceof BoolLiteral)) {
//                    node.setError("kut ding geen bool");
//                    return;
//                }
//            }

            if(!(ifClause.conditionalExpression instanceof BoolLiteral) && !(getVariableValueByExpression(ifClause.conditionalExpression) instanceof BoolLiteral))
                node.setError("kut ding geen bool");
        }
    }

//
//    MyVar := FALSE;
//
//    VarB := MyVar;
//
//    a {
//        if[VarB] {
//            height: 10px;
//        }
//    }


//    private void recursivelyCheckNode(ASTNode node) {
//        System.out.println(node.getNodeLabel());
//
//        if (node instanceof VariableReference) {
//            var variable = (VariableReference) node;
//            if(!variables.containsKey(variable.name)) {
//                node.setError("Variable '"+variable.name+"' out of scope or not declared");
//            }
//        }
//
//        if(node instanceof VariableAssignment) {
//            //Sla node op
//            var variable = (VariableAssignment) node;
//            var name = variable.name.name;
//
//            System.out.println("name: " + name);
//            variables.put(name, variable);
//            node.getChildren().forEach(this::recursivelyCheckNode);
//            variables.remove(name);
//
//            return;
//        }
//
//
//
//        node.getChildren().forEach(this::recursivelyCheckNode);
//    }
}

//
//    a {
//        BroHeight := 100px;
//    }
//
//    p {
//        width: BroHeight;
//    }


// Maybe better to do this in the parser???
class AllowedDeclarations {

}