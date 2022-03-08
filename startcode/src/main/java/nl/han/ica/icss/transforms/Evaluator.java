package nl.han.ica.icss.transforms;

import nl.han.ica.datastructures.IHANLinkedList;
import nl.han.ica.icss.ast.*;
import nl.han.ica.icss.ast.literals.*;
import nl.han.ica.icss.ast.operations.AddOperation;
import nl.han.ica.icss.ast.operations.MultiplyOperation;
import nl.han.ica.icss.ast.operations.SubtractOperation;

import java.util.HashMap;
import java.util.LinkedList;

//div {
//        width: 50px + 2 * 10px - 2px;
//        height: 10px;
//        }


//
//MyVar := 100px;
//
//div {
//    height: MyVar;
//    width: 10 + 20px;
//}
public class Evaluator implements Transform {

    private IHANLinkedList<HashMap<String, Literal>> variableValues;//variableValues = new HANLinkedList<>();
    private HashMap<String, Expression> declaredVariablesPerType = new HashMap<>();

    private HashMap<Expression, Literal> expressionsToReplace = new HashMap<>();

    private HashMap<VariableAssignment, Literal> variables = new HashMap<>();

    public Evaluator() { }

    @Override
    public void apply(AST ast) {
//        variableValues = new HANLinkedList<>();
        System.out.println(ast);

        recursivelyCheckTree(ast.root);

        System.out.println(ast);
//        expressionsToReplace.forEach((a, b) -> {
//            var ast.root.removeChild(a);
//        });
//        var node = ast.root.removeChild(expressionsToReplace.get((Expression) ast.root.getChildren().get(0)));
//        ast.root = node.getChildren().get(0);
    }

    private void recursivelyCheckTree(ASTNode node) {
        storeVariableTypeIfAssignment(node);

        transformReassignments(node);
        transformExpressionWithLiteral(node);

        node.getChildren().forEach(childNode -> {
            recursivelyCheckTree(childNode);
        });
    }

    public void transformReassignments(ASTNode node) {

    }

    public void transformExpressionWithLiteral(ASTNode node) {
        if(node instanceof Declaration) {
            var declaration = (Declaration) node;
//            if(declaration.expression instanceof VariableReference) {
//                declaration.expression = getVariableValueByExpression(declaration.expression);
//            }
//
            declaration.expression = getVariableValueByExpression(declaration.expression);
        }
    }

    private Literal getVariableValueByExpression(Expression expression) {
        if (expression instanceof VariableReference) {
            var variable = (VariableReference) expression;

            var variableValue = declaredVariablesPerType.get(variable.name);
            if (variableValue instanceof Literal) {
                return (Literal) variableValue;
            } else if (variableValue instanceof VariableReference) {
                return getVariableValueByExpression(variableValue);
            } else if(variableValue instanceof Operation) {
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


        if (left instanceof Operation) {
            left = getLiteralFromOperation((Operation) left);
        }

        if (right instanceof Operation) {
            right = getLiteralFromOperation((Operation) right);
        }


        //Calculations:
        //Here left and right are literals

        if (left instanceof PixelLiteral || right instanceof PixelLiteral) {
            return new PixelLiteral(calculateOperation(operation, (Literal) left, (Literal) right));
        } else if(left instanceof PercentageLiteral || right instanceof PercentageLiteral) {
            return new PercentageLiteral(calculateOperation(operation, (Literal) left, (Literal) right));
//            return new PercentageLiteral(getValue(left) + getValue(right));
        }

        return null;
    }

    private Integer calculateOperation(Operation operation, Literal left, Literal right) {
        if(operation instanceof AddOperation) {
            return getValue(left) + getValue(right);
        } else if (operation instanceof MultiplyOperation) {
            return getValue(left) * getValue(right);
        } else if (operation instanceof SubtractOperation) {
            return getValue(left) - getValue(right);
        }

        return null;
    }


    private Integer getValue(Expression literalExpression) {
        return getValue((Literal) literalExpression);
    }

    private Integer getValue(Literal literal) {
        if(literal instanceof PercentageLiteral) {
            return ((PercentageLiteral) literal).value;
        } else if(literal instanceof PixelLiteral) {
            return ((PixelLiteral) literal).value;
        } else if(literal instanceof ScalarLiteral) {
            return ((ScalarLiteral) literal).value;
        }

        return null;
    }

    private void storeVariableTypeIfAssignment(ASTNode node) {
        if (node instanceof VariableAssignment) {
            var assignment = (VariableAssignment) node;
            declaredVariablesPerType.put(assignment.name.name, assignment.expression);
        }
    }
}



//AdjustWidth := TRUE;
//WidthVar := 0px;
//
//p {
//    if [AdjustWidth] {
//        WidthVar := 200px;
//    } else {
//        WidthVar := 250px;
//    }
//
//    width: WidthVar;
//}


//MyHeight := 10px;
//MyBool := TRUE;
//
//div {
//    width: 50px + 2 * 10px - 2px;
//}
//
//#menu {
//    if[MyBool] {
//        MyHeight:= 100px;
//       height: 20px + MyHeight * 2;
//    }
//}