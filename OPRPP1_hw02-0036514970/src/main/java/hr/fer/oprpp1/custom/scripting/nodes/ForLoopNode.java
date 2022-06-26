package hr.fer.oprpp1.custom.scripting.nodes;

import hr.fer.oprpp1.custom.scripting.elems.Element;
import hr.fer.oprpp1.custom.scripting.elems.ElementVariable;

/**
 * razred predstavlja čvor koji izršava For petlju.
 * 
 * @author Filip Pavicic
 *
 */
public class ForLoopNode extends Node {
	
	private ElementVariable  variable;
	private Element  startExpresion;
	private Element  endExpresion;
	private Element  stepExpresion;
	
	private Element[] temporaryArray; //Trenutno spremema tu elemente For petlje jer ne znam FOR sintaksu

	public ForLoopNode(Element[] temporaryArray) {
		super();
		this.temporaryArray = temporaryArray;
	}
	
	@Override
	public String toString() {
		String toReturn = "";
		toReturn += "{$ for ";
		for (Element element : temporaryArray) {
			toReturn += element+" ";
			
		}
		toReturn += "$}";
		for (int i = 0; i < numberOfChildren(); i++) {
			toReturn += getChild(i).toString();
		}
		toReturn += "{$end$}";
		return toReturn;
	}
	
	
	
	

}
