package hr.fer.oprpp1.custom.scripting;



import org.junit.jupiter.api.Test;

import hr.fer.oprpp1.custom.scripting.elems.Element;
import hr.fer.oprpp1.custom.scripting.elems.ElementConstantInteger;
import hr.fer.oprpp1.custom.scripting.elems.ElementTagName;
import hr.fer.oprpp1.custom.scripting.elems.ElementVariable;
import hr.fer.oprpp1.custom.scripting.lexer.Lexer;
import hr.fer.oprpp1.custom.scripting.nodes.*;

public class TestToStringDocumentNode {
	@Test
	public void testNotNull() {
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
		
	}

}
