package hr.fer.zemris.java.custom.scripting.demo;

import hr.fer.zemris.java.custom.scripting.elems.*;
import hr.fer.zemris.java.custom.scripting.nodes.*;

public class DocumentToString {

	public static void main(String[] args) {
		TextNode t1 = new TextNode("This is sample text.\r\n");
		ElementTagName etn1 = new ElementTagName("for");
		ElementVariable ev1 = new ElementVariable("i");
		ElementConstantInteger eci1 = new ElementConstantInteger(1);
		ElementConstantInteger eci2 = new ElementConstantInteger(10);
		ElementConstantInteger eci3 = new ElementConstantInteger(1);
		ForLoopNode fln = new ForLoopNode(new Element[] {ev1,eci1,eci2,eci3});
		TextNode t2 = new TextNode("\tThis is ");
		ElementVariable ev2 = new ElementVariable("i");
		EchoNode en1 = new EchoNode(new Element[] {ev2});
		TextNode t3 = new TextNode("-th time this message is generated.");
		fln.addChildNode(t2);
		fln.addChildNode(en1);
		fln.addChildNode(t3);
		DocumentNode dn = new DocumentNode();
		dn.addChildNode(t1);
		dn.addChildNode(fln);
		
		System.out.println(dn);
	}

}
