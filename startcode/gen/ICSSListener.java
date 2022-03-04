// Generated from /Users/egbert-janterpstra/Downloads/icss2022-sep-main/startcode/src/main/antlr4/nl/han/ica/icss/parser/ICSS.g4 by ANTLR 4.9.2
import org.antlr.v4.runtime.tree.ParseTreeListener;

/**
 * This interface defines a complete listener for a parse tree produced by
 * {@link ICSSParser}.
 */
public interface ICSSListener extends ParseTreeListener {
	/**
	 * Enter a parse tree produced by {@link ICSSParser#stylesheet}.
	 * @param ctx the parse tree
	 */
	void enterStylesheet(ICSSParser.StylesheetContext ctx);
	/**
	 * Exit a parse tree produced by {@link ICSSParser#stylesheet}.
	 * @param ctx the parse tree
	 */
	void exitStylesheet(ICSSParser.StylesheetContext ctx);
	/**
	 * Enter a parse tree produced by {@link ICSSParser#selector}.
	 * @param ctx the parse tree
	 */
	void enterSelector(ICSSParser.SelectorContext ctx);
	/**
	 * Exit a parse tree produced by {@link ICSSParser#selector}.
	 * @param ctx the parse tree
	 */
	void exitSelector(ICSSParser.SelectorContext ctx);
	/**
	 * Enter a parse tree produced by {@link ICSSParser#object}.
	 * @param ctx the parse tree
	 */
	void enterObject(ICSSParser.ObjectContext ctx);
	/**
	 * Exit a parse tree produced by {@link ICSSParser#object}.
	 * @param ctx the parse tree
	 */
	void exitObject(ICSSParser.ObjectContext ctx);
	/**
	 * Enter a parse tree produced by {@link ICSSParser#objectItem}.
	 * @param ctx the parse tree
	 */
	void enterObjectItem(ICSSParser.ObjectItemContext ctx);
	/**
	 * Exit a parse tree produced by {@link ICSSParser#objectItem}.
	 * @param ctx the parse tree
	 */
	void exitObjectItem(ICSSParser.ObjectItemContext ctx);
	/**
	 * Enter a parse tree produced by {@link ICSSParser#key}.
	 * @param ctx the parse tree
	 */
	void enterKey(ICSSParser.KeyContext ctx);
	/**
	 * Exit a parse tree produced by {@link ICSSParser#key}.
	 * @param ctx the parse tree
	 */
	void exitKey(ICSSParser.KeyContext ctx);
	/**
	 * Enter a parse tree produced by {@link ICSSParser#value}.
	 * @param ctx the parse tree
	 */
	void enterValue(ICSSParser.ValueContext ctx);
	/**
	 * Exit a parse tree produced by {@link ICSSParser#value}.
	 * @param ctx the parse tree
	 */
	void exitValue(ICSSParser.ValueContext ctx);
	/**
	 * Enter a parse tree produced by {@link ICSSParser#decleration}.
	 * @param ctx the parse tree
	 */
	void enterDecleration(ICSSParser.DeclerationContext ctx);
	/**
	 * Exit a parse tree produced by {@link ICSSParser#decleration}.
	 * @param ctx the parse tree
	 */
	void exitDecleration(ICSSParser.DeclerationContext ctx);
	/**
	 * Enter a parse tree produced by {@link ICSSParser#ifStatement}.
	 * @param ctx the parse tree
	 */
	void enterIfStatement(ICSSParser.IfStatementContext ctx);
	/**
	 * Exit a parse tree produced by {@link ICSSParser#ifStatement}.
	 * @param ctx the parse tree
	 */
	void exitIfStatement(ICSSParser.IfStatementContext ctx);
	/**
	 * Enter a parse tree produced by {@link ICSSParser#elseStatement}.
	 * @param ctx the parse tree
	 */
	void enterElseStatement(ICSSParser.ElseStatementContext ctx);
	/**
	 * Exit a parse tree produced by {@link ICSSParser#elseStatement}.
	 * @param ctx the parse tree
	 */
	void exitElseStatement(ICSSParser.ElseStatementContext ctx);
	/**
	 * Enter a parse tree produced by {@link ICSSParser#booleanLiteral}.
	 * @param ctx the parse tree
	 */
	void enterBooleanLiteral(ICSSParser.BooleanLiteralContext ctx);
	/**
	 * Exit a parse tree produced by {@link ICSSParser#booleanLiteral}.
	 * @param ctx the parse tree
	 */
	void exitBooleanLiteral(ICSSParser.BooleanLiteralContext ctx);
	/**
	 * Enter a parse tree produced by {@link ICSSParser#pixelLiteral}.
	 * @param ctx the parse tree
	 */
	void enterPixelLiteral(ICSSParser.PixelLiteralContext ctx);
	/**
	 * Exit a parse tree produced by {@link ICSSParser#pixelLiteral}.
	 * @param ctx the parse tree
	 */
	void exitPixelLiteral(ICSSParser.PixelLiteralContext ctx);
	/**
	 * Enter a parse tree produced by {@link ICSSParser#colorLiteral}.
	 * @param ctx the parse tree
	 */
	void enterColorLiteral(ICSSParser.ColorLiteralContext ctx);
	/**
	 * Exit a parse tree produced by {@link ICSSParser#colorLiteral}.
	 * @param ctx the parse tree
	 */
	void exitColorLiteral(ICSSParser.ColorLiteralContext ctx);
	/**
	 * Enter a parse tree produced by {@link ICSSParser#scalarLiteral}.
	 * @param ctx the parse tree
	 */
	void enterScalarLiteral(ICSSParser.ScalarLiteralContext ctx);
	/**
	 * Exit a parse tree produced by {@link ICSSParser#scalarLiteral}.
	 * @param ctx the parse tree
	 */
	void exitScalarLiteral(ICSSParser.ScalarLiteralContext ctx);
	/**
	 * Enter a parse tree produced by {@link ICSSParser#percentageLiteral}.
	 * @param ctx the parse tree
	 */
	void enterPercentageLiteral(ICSSParser.PercentageLiteralContext ctx);
	/**
	 * Exit a parse tree produced by {@link ICSSParser#percentageLiteral}.
	 * @param ctx the parse tree
	 */
	void exitPercentageLiteral(ICSSParser.PercentageLiteralContext ctx);
	/**
	 * Enter a parse tree produced by {@link ICSSParser#variableName}.
	 * @param ctx the parse tree
	 */
	void enterVariableName(ICSSParser.VariableNameContext ctx);
	/**
	 * Exit a parse tree produced by {@link ICSSParser#variableName}.
	 * @param ctx the parse tree
	 */
	void exitVariableName(ICSSParser.VariableNameContext ctx);
	/**
	 * Enter a parse tree produced by {@link ICSSParser#variable}.
	 * @param ctx the parse tree
	 */
	void enterVariable(ICSSParser.VariableContext ctx);
	/**
	 * Exit a parse tree produced by {@link ICSSParser#variable}.
	 * @param ctx the parse tree
	 */
	void exitVariable(ICSSParser.VariableContext ctx);
	/**
	 * Enter a parse tree produced by {@link ICSSParser#multiplyOperation}.
	 * @param ctx the parse tree
	 */
	void enterMultiplyOperation(ICSSParser.MultiplyOperationContext ctx);
	/**
	 * Exit a parse tree produced by {@link ICSSParser#multiplyOperation}.
	 * @param ctx the parse tree
	 */
	void exitMultiplyOperation(ICSSParser.MultiplyOperationContext ctx);
	/**
	 * Enter a parse tree produced by {@link ICSSParser#addOperation}.
	 * @param ctx the parse tree
	 */
	void enterAddOperation(ICSSParser.AddOperationContext ctx);
	/**
	 * Exit a parse tree produced by {@link ICSSParser#addOperation}.
	 * @param ctx the parse tree
	 */
	void exitAddOperation(ICSSParser.AddOperationContext ctx);
	/**
	 * Enter a parse tree produced by {@link ICSSParser#subtractOperation}.
	 * @param ctx the parse tree
	 */
	void enterSubtractOperation(ICSSParser.SubtractOperationContext ctx);
	/**
	 * Exit a parse tree produced by {@link ICSSParser#subtractOperation}.
	 * @param ctx the parse tree
	 */
	void exitSubtractOperation(ICSSParser.SubtractOperationContext ctx);
	/**
	 * Enter a parse tree produced by {@link ICSSParser#expression}.
	 * @param ctx the parse tree
	 */
	void enterExpression(ICSSParser.ExpressionContext ctx);
	/**
	 * Exit a parse tree produced by {@link ICSSParser#expression}.
	 * @param ctx the parse tree
	 */
	void exitExpression(ICSSParser.ExpressionContext ctx);
}