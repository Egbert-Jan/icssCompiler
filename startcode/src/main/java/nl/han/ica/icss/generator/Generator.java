package nl.han.ica.icss.generator;


import nl.han.ica.icss.ast.*;
import nl.han.ica.icss.ast.literals.ColorLiteral;
import nl.han.ica.icss.ast.literals.PercentageLiteral;
import nl.han.ica.icss.ast.literals.PixelLiteral;

import java.awt.*;
import java.util.stream.Collectors;

public class Generator {

	public String generate(AST ast) {
//		System.out.println(ast);
		return recursivelyCheckTree(ast.root);
	}

	int indent = 0;
	private String recursivelyCheckTree(ASTNode node) {
		StringBuilder stringBuilder = new StringBuilder();

//		var childrenWithoutVariableAssignments = node.getChildren().stream().filter(n -> !(n instanceof VariableAssignment)).collect(Collectors.toList());
		for (ASTNode child : node.getChildren()) {
			if (child instanceof Selector) {
				indent = 0;
				indent++;
				stringBuilder
						.append(child)
						.append(" {")
						.append(System.lineSeparator());
			} else if(child instanceof Declaration) {
				stringBuilder
						.append("\t".repeat(Math.max(0, indent)))
						.append(generateDecleration((Declaration) child))
						.append(System.lineSeparator());

				//Last child
				if (node.getChildren().get(node.getChildren().size()-1) == child) {
					stringBuilder
							.append("}")
							.append(System.lineSeparator().repeat(2));
				}
			}

			stringBuilder.append(recursivelyCheckTree(child));
		}

		return stringBuilder.toString();
	}

	private String generateDecleration(Declaration child) {
		if (child.expression instanceof PixelLiteral) {
			return child.property.name + " " + ((PixelLiteral) child.expression).value + "px;";
		} else if (child.expression instanceof PercentageLiteral) {
			return child.property.name + " " + ((PercentageLiteral) child.expression).value + "%;";
		} if (child.expression instanceof ColorLiteral) {
			return child.property.name + " " + ((ColorLiteral) child.expression).value + ";";
		}
		return "oops";
	}
}

