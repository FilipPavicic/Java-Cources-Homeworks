package hr.fer.zemris.java.custom.scripting.nodes;

import hr.fer.zemris.java.custom.scripting.elems.Element;
import hr.fer.zemris.java.custom.scripting.elems.ElementVariable;

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
		variable = (ElementVariable) temporaryArray[0];
		startExpresion = temporaryArray[1];
		endExpresion = temporaryArray[2];
		stepExpresion = temporaryArray[3];
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
	
	@Override
	public void accept(INodeVisitor visitor) {
		visitor.visitForLoopNode(this);
	}

	public Element[] getTemporaryArray() {
		return temporaryArray;
	}

	public ElementVariable getVariable() {
		return variable;
	}

	public Element getStartExpresion() {
		return startExpresion;
	}

	public Element getEndExpresion() {
		return endExpresion;
	}

	public Element getStepExpresion() {
		return stepExpresion;
	}
	
	
	
	
	
	

}
