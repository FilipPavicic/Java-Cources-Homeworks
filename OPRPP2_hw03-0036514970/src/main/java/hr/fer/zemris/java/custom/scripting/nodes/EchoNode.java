package hr.fer.zemris.java.custom.scripting.nodes;

import hr.fer.zemris.java.custom.scripting.elems.Element;

/**
 * razred predstavlja čvor koji dinamički generira tekst
 * 
 * @author Filip Pavicic
 *
 */
public class EchoNode extends Node {

	private Element[] elements;

	public EchoNode(Element[] elements) {
		super();
		this.elements = elements;
	}
	
	@Override
	public String toString() {
		String toReturn = "";
		toReturn += "{$= ";
		for (Element element : elements) {
			toReturn += element+" ";
			
		}
		toReturn += "$}";
		return toReturn;
	}
	
	@Override
	public void accept(INodeVisitor visitor) {
		visitor.visitEchoNode(this);
	}

	public Element[] getElements() {
		return elements;
	}
	
	
}
