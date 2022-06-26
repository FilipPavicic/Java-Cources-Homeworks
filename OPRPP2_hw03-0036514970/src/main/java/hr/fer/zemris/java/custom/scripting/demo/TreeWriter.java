package hr.fer.zemris.java.custom.scripting.demo;

import java.io.IOException;
import java.nio.charset.StandardCharsets;
import java.nio.file.Files;
import java.nio.file.Paths;

import hr.fer.zemris.java.custom.scripting.elems.Element;
import hr.fer.zemris.java.custom.scripting.nodes.DocumentNode;
import hr.fer.zemris.java.custom.scripting.nodes.EchoNode;
import hr.fer.zemris.java.custom.scripting.nodes.ForLoopNode;
import hr.fer.zemris.java.custom.scripting.nodes.INodeVisitor;
import hr.fer.zemris.java.custom.scripting.nodes.TextNode;
import hr.fer.zemris.java.custom.scripting.parser.SmartScriptParser;
import hr.fer.zemris.java.custom.scripting.parser.SmartScriptParserException;

public class TreeWriter {

	private static class WriterVisitor implements INodeVisitor{

		@Override
		public void visitTextNode(TextNode node) {
			System.out.print(node);

		}

		@Override
		public void visitForLoopNode(ForLoopNode node) {
			String toReturn = "";
			toReturn += "{$ FOR ";
			for (Element element : node.getTemporaryArray()) {
				toReturn += element+" ";

			}
			toReturn += "$}";
			System.out.print(toReturn);
			for (int i = 0; i < node.numberOfChildren(); i++) {
				node.getChild(i).accept(this);
			}
			System.out.print("{$END$}");

		}

		@Override
		public void visitEchoNode(EchoNode node) {
			System.out.print(node);

		}

		@Override
		public void visitDocumentNode(DocumentNode node) {
			for (int i = 0; i < node.numberOfChildren(); i++) {
				node.getChild(i).accept(this);
			}

		}

	}

	private static void testVisitor(String path) {
		String docBody= "";
		try {
			docBody = new String(
					Files.readAllBytes(Paths.get(path)),
					StandardCharsets.UTF_8
					);
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		System.out.println(docBody);
		SmartScriptParser parser = null;

		try {
			parser = new SmartScriptParser(docBody);
		} catch(SmartScriptParserException e) {
			System.out.println("Unable to parse document!");
			System.exit(-1);
		} catch(Exception e) {
			System.out.println("If this line ever executes, you have failed this class!");
			System.exit(-1);
		}
		SmartScriptParser p = new SmartScriptParser(docBody);
		WriterVisitor visitor = new WriterVisitor();
		p.getDocumentNode().accept(visitor);


	}
	
	public static void main(String[] args) {
		testVisitor("example/document1.txt");
	}
}
