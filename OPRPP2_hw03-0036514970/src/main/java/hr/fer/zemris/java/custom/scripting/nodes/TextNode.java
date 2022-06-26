package hr.fer.zemris.java.custom.scripting.nodes;

/**
 * razred predstavlja tekstualni čvor
 * 
 * @author Filip Pavicic
 *
 */
public class TextNode extends Node {
	
	private String text;

	public String getText() {
		return text;
	}

	public TextNode(String text) {
		super();
		this.text = text;
	}

	@Override
	public String toString() {
		return text;
	}

	@Override
	public boolean equals(Object obj) {
		if (this == obj)
			return true;
		if (!super.equals(obj))
			return false;
		if (!(obj instanceof TextNode))
			return false;
		TextNode other = (TextNode) obj;
		if (text == null) {
			if (other.text != null)
				return false;
		} else if (!text.equals(other.text))
			return false;
		return true;
	}
	
	@Override
	public void accept(INodeVisitor visitor) {
		visitor.visitTextNode(this);
	}
	
	
	
	
}
