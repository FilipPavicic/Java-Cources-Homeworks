package hr.fer.oprpp1.custom.scripting.nodes;

import hr.fer.oprpp1.custom.scripting.elems.Element;

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
}
