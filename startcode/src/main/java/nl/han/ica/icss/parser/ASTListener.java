package nl.han.ica.icss.parser;


import nl.han.ica.datastructures.IHANStack;
import nl.han.ica.datastructures.impl.HanStack;
import nl.han.ica.icss.ast.*;
import nl.han.ica.icss.ast.literals.*;
import nl.han.ica.icss.ast.operations.AddOperation;
import nl.han.ica.icss.ast.operations.MultiplyOperation;
import nl.han.ica.icss.ast.operations.SubtractOperation;
import nl.han.ica.icss.ast.selectors.ClassSelector;
import nl.han.ica.icss.ast.selectors.IdSelector;
import nl.han.ica.icss.ast.selectors.TagSelector;

/**
 * This class extracts the ICSS Abstract Syntax Tree from the Antlr Parse tree.
 */
public class ASTListener extends ICSSBaseListener {
	
	//Accumulator attributes:
	private AST ast;

	//Use this to keep track of the parent nodes when recursively traversing the ast
	private IHANStack<ASTNode> currentContainer;

	public ASTListener() {
		ast = new AST();
		currentContainer = new HanStack<>();

	}

    public AST getAST() {
        return ast;
    }

	@Override
	public void enterStylesheet(ICSSParser.StylesheetContext ctx) {
		currentContainer.push(new Stylesheet());
	}

	@Override
	public void exitStylesheet(ICSSParser.StylesheetContext ctx) {
		//Check if it's stylesheet??
		var stylesheet = (Stylesheet) currentContainer.pop();
		ast.setRoot(stylesheet);
	}

	@Override
	public void enterSelector(ICSSParser.SelectorContext ctx) {
		char beginChar = ctx.getText().charAt(0);

		if (beginChar == '#') {
			currentContainer.push(new IdSelector(ctx.getText()));
		} else if(beginChar == '.') {
			currentContainer.push(new ClassSelector(ctx.getText()));
		} else {
			currentContainer.push(new TagSelector(ctx.getText()));
		}
	}

	@Override
	public void exitSelector(ICSSParser.SelectorContext ctx) {
		popAndAddAsChild();
	}

	@Override
	public void enterObject(ICSSParser.ObjectContext ctx) {
		currentContainer.push(new Stylerule());
	}

	@Override
	public void exitObject(ICSSParser.ObjectContext ctx) {
		popAndAddAsChild();
	}

	@Override
	public void enterDecleration(ICSSParser.DeclerationContext ctx) {
		currentContainer.push(new Declaration());
	}

	@Override
	public void exitDecleration(ICSSParser.DeclerationContext ctx) {
		popAndAddAsChild();
	}

	@Override
	public void enterKey(ICSSParser.KeyContext ctx) {
		currentContainer.push(new PropertyName(ctx.getText()));
	}

	@Override
	public void exitKey(ICSSParser.KeyContext ctx) {
		popAndAddAsChild();
	}

	@Override public void enterValue(ICSSParser.ValueContext ctx) { }
	@Override public void exitValue(ICSSParser.ValueContext ctx) { }

	@Override
	public void enterColorLiteral(ICSSParser.ColorLiteralContext ctx) {
		currentContainer.push(new ColorLiteral(ctx.getText()));
	}

	@Override
	public void exitColorLiteral(ICSSParser.ColorLiteralContext ctx) {
		popAndAddAsChild();
	}

	@Override
	public void enterPixelLiteral(ICSSParser.PixelLiteralContext ctx) {
		// the px gets removed in the constructor
		currentContainer.push(new PixelLiteral(ctx.getText()));
	}

	@Override
	public void exitPixelLiteral(ICSSParser.PixelLiteralContext ctx) {
		popAndAddAsChild();
	}

	@Override
	public void enterBooleanLiteral(ICSSParser.BooleanLiteralContext ctx) {
		currentContainer.push(new BoolLiteral(ctx.getText()));
	}

	@Override
	public void exitBooleanLiteral(ICSSParser.BooleanLiteralContext ctx) {
		popAndAddAsChild();
	}

	@Override
	public void enterPercentageLiteral(ICSSParser.PercentageLiteralContext ctx) {
		currentContainer.push(new PercentageLiteral(ctx.getText()));
	}

	@Override
	public void exitPercentageLiteral(ICSSParser.PercentageLiteralContext ctx) {
		popAndAddAsChild();
	}

	@Override
	public void enterScalarLiteral(ICSSParser.ScalarLiteralContext ctx) {
		currentContainer.push(new ScalarLiteral(ctx.getText()));
	}

	@Override
	public void exitScalarLiteral(ICSSParser.ScalarLiteralContext ctx) {
		popAndAddAsChild();
	}

	@Override
	public void enterVariable(ICSSParser.VariableContext ctx) {
		currentContainer.push(new VariableAssignment());
	}

	@Override
	public void exitVariable(ICSSParser.VariableContext ctx) {
		popAndAddAsChild();
	}

	@Override
	public void enterVariableName(ICSSParser.VariableNameContext ctx) {
		currentContainer.push(new VariableReference(ctx.getText()));
	}

	@Override
	public void exitVariableName(ICSSParser.VariableNameContext ctx) {
		popAndAddAsChild();
	}

	@Override
	public void enterExpression(ICSSParser.ExpressionContext ctx) {
		// This logic needs to be here because we need to check if the
		// expression has operations. If so we add the operation.
		if(ctx.addOperation() != null) {
			currentContainer.push(new AddOperation());
		} else if(ctx.subtractOperation() != null) {
			currentContainer.push(new SubtractOperation());
		} else if(ctx.multiplyOperation() != null) {
			currentContainer.push(new MultiplyOperation());
		}
	}

	@Override
	public void exitExpression(ICSSParser.ExpressionContext ctx) {
		// If it's an expression with a operation in it then we pop it
		// and add it to the parents child
		if(ctx.addOperation() != null || ctx.multiplyOperation() != null || ctx.subtractOperation() != null) {
			popAndAddAsChild();
		}
	}

	//Moved logic into the expression method
	@Override
	public void enterAddOperation(ICSSParser.AddOperationContext ctx) { }
	@Override
	public void exitAddOperation(ICSSParser.AddOperationContext ctx) { }
	@Override
	public void enterMultiplyOperation(ICSSParser.MultiplyOperationContext ctx) { }
	@Override
	public void exitMultiplyOperation(ICSSParser.MultiplyOperationContext ctx) { }

	@Override
	public void enterIfStatement(ICSSParser.IfStatementContext ctx) {
		currentContainer.push(new IfClause());
	}

	@Override
	public void exitIfStatement(ICSSParser.IfStatementContext ctx) {
		popAndAddAsChild();
	}

	@Override
	public void enterElseStatement(ICSSParser.ElseStatementContext ctx) {
		currentContainer.push(new ElseClause());
	}

	@Override
	public void exitElseStatement(ICSSParser.ElseStatementContext ctx) {
		popAndAddAsChild();
	}

	private void popAndAddAsChild() {
		ASTNode tag = currentContainer.pop();

		if (!(tag  instanceof Stylesheet))
			currentContainer.peek().addChild(tag);
	}
}