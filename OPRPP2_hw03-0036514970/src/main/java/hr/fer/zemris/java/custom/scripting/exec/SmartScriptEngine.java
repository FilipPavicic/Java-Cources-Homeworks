package hr.fer.zemris.java.custom.scripting.exec;

import java.io.IOException;
import java.text.DecimalFormat;
import java.text.DecimalFormatSymbols;
import java.util.LinkedList;
import java.util.Locale;
import java.util.Stack;
import java.util.stream.Collectors;

import hr.fer.zemris.java.custom.scripting.elems.Element;
import hr.fer.zemris.java.custom.scripting.elems.ElementOperator;
import hr.fer.zemris.java.custom.scripting.elems.ElementString;
import hr.fer.zemris.java.custom.scripting.nodes.DocumentNode;
import hr.fer.zemris.java.custom.scripting.nodes.EchoNode;
import hr.fer.zemris.java.custom.scripting.nodes.ForLoopNode;
import hr.fer.zemris.java.custom.scripting.nodes.INodeVisitor;
import hr.fer.zemris.java.custom.scripting.nodes.TextNode;
import hr.fer.zemris.java.webserver.RequestContext;

public class SmartScriptEngine {
	private DocumentNode documentNode;
	private RequestContext requestContext;
	private ObjectMultistack multistack = new ObjectMultistack();
	private INodeVisitor visitor = new INodeVisitor() {

		@Override
		public void visitTextNode(TextNode node) {
			try {
				requestContext.write(node.getText());
			} catch (IOException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
		}

		@Override
		public void visitForLoopNode(ForLoopNode node) {
			String variable = node.getVariable().toString();
			ValueWrapper start = new ValueWrapper(node.getStartExpresion().toString());
			ValueWrapper end = new ValueWrapper(node.getEndExpresion().toString());
			ValueWrapper step = new ValueWrapper(node.getStepExpresion().toString());
			multistack.push(variable , start);
			for (ValueWrapper i = multistack.peek(variable); i.numCompare(end.value)<= 0; multistack.peek(variable).add(step.value),i = multistack.peek(variable)) {
				for (int j = 0; j < node.numberOfChildren(); j++) {
					node.getChild(j).accept(this);
				}
			}
			multistack.pop(variable);

			
			
		}

		@Override
		public void visitEchoNode(EchoNode node) {
			Stack<Object> tempStack = new Stack<>();
			for(Element el : node.getElements()) {
				switch (el.getClass().getSimpleName()) {
				case "ElementString" -> tempStack.push(el.getName());
				case "ElementConstantDouble" , "ElementConstantInteger" -> tempStack.push(el.getName());
				case "ElementVariable" -> tempStack.push(multistack.peek(el.getName()).value);
				case "ElementOperator" -> {
					ValueWrapper o1 = new ValueWrapper(tempStack.pop());
					ValueWrapper o2 = new ValueWrapper(tempStack.pop());
					switch (el.getName()) {
					case "+" ->  o1.add(o2.value);
					case "-" ->  o1.subtract(o2.value);
					case "*" ->  o1.multiply(o2.value);
					case "/" ->  o1.divide(o2.value);
					default ->
						throw new IllegalArgumentException("Unexpected value: " + el.getName());
					}
					tempStack.push(o1.value.toString());
				}
				case "ElementFunction" -> tempStack = resolveFunction(el.getName(),tempStack);
				default ->
					throw new IllegalArgumentException("Unexpected value: " + el.getClass().getSimpleName());
				}
				
			}
			tempStack.stream().
			collect(Collectors.toCollection(LinkedList::new)).
			forEach((o) -> {
				try {
					requestContext.write(o.toString());
				} catch (IOException e) {
					// TODO Auto-generated catch block
					e.printStackTrace();
				}
			});
			
		}


		@Override
		public void visitDocumentNode(DocumentNode node) {
			for (int i = 0; i < node.numberOfChildren(); i++) {
				node.getChild(i).accept(this);
			}
			
		}
	};
	
	public SmartScriptEngine(DocumentNode documentNode, RequestContext requestContext) {
		super();
		this.documentNode = documentNode;
		this.requestContext = requestContext;
	}

	public void execute() {
		documentNode.accept(visitor);
		try {
			requestContext.write("\r\n");
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}
	
	private Stack<Object> resolveFunction(String name, Stack<Object> tempStack) {
		switch (name) {
		case "sin" -> tempStack.push(Math.sin(Math.toRadians(Double.parseDouble(tempStack.pop().toString()))));
		case "decfmt" -> {
			String str = (String)tempStack.pop();
			DecimalFormat f = new DecimalFormat(str,new DecimalFormatSymbols(Locale.ENGLISH));
			Double x = Double.parseDouble(tempStack.pop().toString());
			tempStack.push(f.format(x));
		}
		case "dup" -> tempStack.push(tempStack.peek());
		case "swap" -> {
			Object o1 = tempStack.pop();
			Object o2 = tempStack.pop();
			tempStack.push(o1);
			tempStack.push(o2);
		}
		case "setMimeType" -> requestContext.setMimeType(tempStack.pop().toString());
		case "paramGet" -> {
			Object dv = tempStack.pop();
			String key = tempStack.pop().toString();
			String param = requestContext.getParameter(key);
			tempStack.push(param == null ? dv : param);
		}
		case "pparamGet" -> {
			Object dv = tempStack.pop();
			String key = tempStack.pop().toString();
			String param = requestContext.getPersistentParameter(key);
			tempStack.push(param == null ? dv : param);
		}
		case "pparamSet" -> {
			String key = tempStack.pop().toString();
			String value = tempStack.pop().toString();
			requestContext.setPersistentParameter(key, value);
		}
		case "tparamGet" -> {
			Object dv = tempStack.pop();
			String key = tempStack.pop().toString();
			String param = requestContext.getTemporaryParameter(key);
			tempStack.push(param == null ? dv : param);
		}
		case "tparamSet" -> {
			String key = tempStack.pop().toString();
			String value = tempStack.pop().toString();
			requestContext.setTemporaryParameter(key, value);
		}
		case "tparamDel" -> {
			String value = tempStack.pop().toString();
			requestContext.removeTemporaryParameter(value);
		}
		
		
		default->
			throw new IllegalArgumentException("Unexpected value: " + name);
		}
		return tempStack; 
	}
}