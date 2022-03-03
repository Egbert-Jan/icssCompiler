package nl.han.ica.icss.parser;

import java.util.Stack;


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
import org.antlr.v4.runtime.ParserRuleContext;

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
		System.out.println("enterStylesheet");
		currentContainer.push(new Stylesheet());
	}

	@Override
	public void exitStylesheet(ICSSParser.StylesheetContext ctx) {
		System.out.println("exitStylesheet");
		//Check if it's stylesheet??
		var stylesheet = (Stylesheet) currentContainer.pop();
		ast.setRoot(stylesheet);
	}

	@Override
	public void enterSelector(ICSSParser.SelectorContext ctx) {
		System.out.println("enterSelector");
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
		System.out.println("exitSelector");
		popAndAddChild();
	}

	@Override
	public void enterObject(ICSSParser.ObjectContext ctx) {
		System.out.println("enterObject");
		currentContainer.push(new Stylerule());
	}

	@Override
	public void exitObject(ICSSParser.ObjectContext ctx) {
		System.out.println("exitObject");
		popAndAddChild();
	}

	@Override
	public void enterDecleration(ICSSParser.DeclerationContext ctx) {
		System.out.println("enterDecleration");
		currentContainer.push(new Declaration());
	}

	@Override
	public void exitDecleration(ICSSParser.DeclerationContext ctx) {
		System.out.println("exitDecleration");
		popAndAddChild();
	}

	@Override
	public void enterKey(ICSSParser.KeyContext ctx) {
		System.out.println("enterKey:" + ctx.getText());
		currentContainer.push(new PropertyName(ctx.getText()));
	}

	@Override
	public void exitKey(ICSSParser.KeyContext ctx) {
		System.out.println("exitKey");
		popAndAddChild();
	}

	@Override
	public void enterValue(ICSSParser.ValueContext ctx) { }

	@Override
	public void enterColorLiteral(ICSSParser.ColorLiteralContext ctx) {
		System.out.println("enterColorLiteral");
		currentContainer.push(new ColorLiteral(ctx.getText()));
	}

	@Override
	public void exitColorLiteral(ICSSParser.ColorLiteralContext ctx) {
		System.out.println("exitColorLiteral");
		popAndAddChild();
	}

	@Override
	public void enterPixelLiteral(ICSSParser.PixelLiteralContext ctx) {
		System.out.println("enterPixelLiteral");
		// the px gets removed in the constructor
		currentContainer.push(new PixelLiteral(ctx.getText()));
	}

	@Override
	public void exitPixelLiteral(ICSSParser.PixelLiteralContext ctx) {
		System.out.println("exitPixelLiteral");
		popAndAddChild();
	}


//	@Override
//	public void exitEveryRule(ParserRuleContext ctx) {
//		popAndAddChild();
//	}

	private void popAndAddChild() {
		ASTNode tag = currentContainer.pop();

		if (!(tag  instanceof Stylesheet))
			currentContainer.peek().addChild(tag);
	}
}