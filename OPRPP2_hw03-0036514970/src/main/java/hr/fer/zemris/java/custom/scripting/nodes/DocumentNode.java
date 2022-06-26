package hr.fer.zemris.java.custom.scripting.nodes;

/**
 * razred predstavlja vršni čvor u
 * kojeg se pohranjuje čitavi ulazni kod
 * 
 * @author Filip Pavicic
 *
 */
public class DocumentNode extends Node {

	@Override
	public String toString() {
		String toReturn = "";
		for (int i = 0; i < numberOfChildren(); i++) {
			toReturn += getChild(i).toString();
		}
		return toReturn;
	}
	
	@Override
	public void accept(INodeVisitor visitor) {
		visitor.visitDocumentNode(this);
		
	}
	
	
	
}
