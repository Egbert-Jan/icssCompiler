package nl.han.ica.icss.transforms;

import nl.han.ica.datastructures.IHANLinkedList;
import nl.han.ica.icss.ast.*;
import nl.han.ica.icss.ast.literals.*;
import nl.han.ica.icss.ast.operations.AddOperation;
import nl.han.ica.icss.ast.operations.MultiplyOperation;
import nl.han.ica.icss.ast.operations.SubtractOperation;

import javax.swing.text.Style;
import java.lang.reflect.Array;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.Iterator;
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


//AdjustWidth := TRUE;
//WidthVar := 0px;
//
//p {
//if [AdjustWidth] {
//    height: 100px;
//    WidthVar := 200px;
//} else {
//    WidthVar := 250px;
//}
//
//    width: WidthVar;
//}
//
//
//a {
//    height: 100px;
//    width: 10px;
//}


public class Evaluator implements Transform {

    private IHANLinkedList<HashMap<String, Literal>> variableValues;//variableValues = new HANLinkedList<>();
    private HashMap<String, Expression> declaredVariablesPerType = new HashMap<>();

    private HashMap<Expression, Literal> expressionsToReplace = new HashMap<>();

    private HashMap<VariableAssignment, Literal> variables = new HashMap<>();

    public Evaluator() { }

    @Override
    public void apply(AST ast) {
        System.out.println(ast);
        recursivelyCheckTree(ast.root);
        System.out.println(ast);
    }

    private void recursivelyCheckTree(ASTNode node) {
        storeVariableTypeIfAssignment(node);
        transformExpressionWithLiteral(node);


        var index = 0;
        for (ASTNode childNode : node.getChildren()) {
            if (childNode instanceof IfClause) {
                var ifClause = (IfClause) childNode;
                ifClause.conditionalExpression = getVariableValueByExpression(ifClause.conditionalExpression);

                ArrayList<ASTNode> itemsToAdd = new ArrayList<>();

                var ifExpressionCondition = ((BoolLiteral) ifClause.conditionalExpression).value;
                if (ifExpressionCondition) {
                    if (ifClause.elseClause != null) {
                        ifClause.elseClause.body = new ArrayList<>();
                    }
                    itemsToAdd.addAll(ifClause.body);
                } else {
                    ifClause.body = new ArrayList<>();
                    if (ifClause.elseClause != null) {
                        itemsToAdd.addAll(ifClause.elseClause.body);
                    }
                }

                if (node instanceof Stylerule) {
                    //Add to parent
                    // .stream().filter()
                    ((Stylerule) node).body.addAll(index, itemsToAdd);
                    ((Stylerule) node).body.remove(childNode);

                } else if (node instanceof IfClause) {
                    ((IfClause) node).body.remove(ifClause);
                }

                //Check parent again
                recursivelyCheckTree(node);
            }

            recursivelyCheckTree(childNode);
            index++;
        }
    }

    public void transformExpressionWithLiteral(ASTNode node) {
        if(node instanceof Declaration) {
            var declaration = (Declaration) node;
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
