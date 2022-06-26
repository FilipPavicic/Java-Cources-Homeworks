package hr.fer.oprpp1.custom.scripting.nodes;

import hr.fer.oprpp1.custom.collections.ArrayIndexedCollection;

/**
 * Razred predstavlja genričku imlementaciju čvorova 
 * 
 * @author Filip Pavicic
 *
 */
public class Node {
	
	ArrayIndexedCollection children;
	
	/**
	 * Trenutnom Čvoru dodaje novo dijete.
	 * 
	 * @param child dijete koje se dodaj u kolkciju dijeteta Trenutnom čvoru
	 */
	public void addChildNode(Node child) {
		if(children == null) children=new ArrayIndexedCollection();
		
		children.add(child);
	}
	
	/**
	 * Vraća broj djece od trenutnog roditelja.
	 * 
	 * @return broj dijce.
	 */
	public int numberOfChildren() {
		return children.size();
	}
	
	/**
	 * Vraća dijete na određenom indeksu.
	 * 
	 * @param index indeks dijeteta koji se traži.
	 * @return Dijete na traženom indeksu.
	 */
	public Node getChild(int index) {
		return (Node) children.get(index);
	}


	@Override
	public boolean equals(Object obj) {
		if (this == obj)
			return true;
		if (!(obj instanceof Node))
			return false;
		Node other = (Node) obj;
		if (children == null) {
			if (other.children != null)
				return false;
		} else if (!children.equals(other.children))
			return false;
		return true ;
	}
	

	
	
}
