package hr.fer.zemris.java.custom.scripting.parser;

import hr.fer.zemris.java.custom.collections.*;
import hr.fer.zemris.java.custom.scripting.elems.*;
import hr.fer.zemris.java.custom.scripting.lexer.*;
import hr.fer.zemris.java.custom.scripting.nodes.*;

public class SmartScriptParser {
	private String input;
	private ObjectStack stack;

	public SmartScriptParser(String input) {
		super();
		this.input = input;
		stack = new ObjectStack();
		stack.push(new DocumentNode());
		Parser();
	}

	private void Parser() {
		Token[] tokens;
		try {
			Lexer lexer = new Lexer(input);
			tokens = lexer.productTokens();
		}catch (LexerException e) {
			throw new SmartScriptParserException("Lexer error" +e.getMessage());
		}
		
		// 0 - nije niti u jednom Tag-u parser
		// 1 - Parser je u "=" tagu
		// 2 - Parser je u "for" tagu
		short state = 0; 
		short NumberOfOpenFor = 0;
		ArrayIndexedCollection elementsToSave = new ArrayIndexedCollection();
		Node lastOnStack = (Node)stack.peek();
		for (Token token : tokens) {
			if(token.getType() == TokenType.TEXT ) {
				if(state != 0) lastOnStack.addChildNode(saveLastNode(state,elementsToSave));
				lastOnStack = (Node)stack.peek();
				lastOnStack.addChildNode(new TextNode((String)token.getValue()));
				state = 0;
				continue;
			}
			if(token.getType() == TokenType.TAG_NAME) {
				
				if(token.getValue().toString().equals("=")) {
					if(state != 0) lastOnStack.addChildNode(saveLastNode(state,elementsToSave));
					state = 1;
				}
				if(token.getValue().toString().equals("for")) {
					NumberOfOpenFor++;
					if(state != 0) lastOnStack.addChildNode(saveLastNode(state,elementsToSave));
					state = 2;
				}
				if(token.getValue().toString().equals("end")) {
					if(state != 0) lastOnStack.addChildNode(saveLastNode(state,elementsToSave));
					NumberOfOpenFor--;
					ForLoopNode child = (ForLoopNode) stack.pop();
					lastOnStack = (Node)stack.peek();
					state = 0;
				}
				lastOnStack = (Node)stack.peek();
				continue;
			}
			elementsToSave.add(token.getValue());
		}
		if(state == 1) lastOnStack.addChildNode(saveLastNode(state,elementsToSave));
		if(NumberOfOpenFor != 0) throw new SmartScriptParserException("For petlja nije zatovrena");
	}

	private Node saveLastNode(short state, ArrayIndexedCollection elementsToSave) {
		switch (state) {
			case 1 : {
				Element[] elm = copyArray(elementsToSave);
				elementsToSave.clear();
				return new EchoNode(elm);
			}
			case 2 : {
				Element[] elm = copyArray(elementsToSave);
				elementsToSave.clear();
				checkForSintax(elm);
				ForLoopNode node = new ForLoopNode(elm);
				stack.push(node);
				return node;
			}
			default :
				throw new SmartScriptParserException("Unexpected value: " + state);
		}
	};
	
	private void checkForSintax(Element[] elm) {
		if(elm.length < 3) throw new SmartScriptParserException("Pre malo argumenta u For deklaraciji");
		if(elm.length > 4) throw new SmartScriptParserException("Pre više argumenta u For deklaraciji");
		if(!(elm[0] instanceof ElementVariable)) 
			throw new SmartScriptParserException(elm[0] +" nije istanca ElementVariable");
		for (int i = 1; i < elm.length; i++) {
			if(elm[i] instanceof ElementVariable) continue;
			if(elm[i] instanceof ElementConstantInteger) continue;
			if(elm[i] instanceof ElementConstantDouble) continue;
			if(elm[i] instanceof ElementString) continue;
			throw new SmartScriptParserException(elm[i] +" nije valjana instanca");
		}
		
		
	}

	private Element[] copyArray(ArrayIndexedCollection col){
		Object[] objects= col.toArray(); 
		Element[] el = new Element[objects.length];
		for (int i = 0; i < objects.length; i++) {
			el[i] = (Element) objects[i];
		}
		return el;
	}
	
	public DocumentNode getDocumentNode() {
		return (DocumentNode) stack.peek();
	}

}
