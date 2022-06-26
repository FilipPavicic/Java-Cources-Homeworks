package hr.fer.oprpp1.custom.scripting;

import static org.junit.jupiter.api.Assertions.*;

import java.io.IOException;
import java.io.InputStream;
import java.nio.charset.StandardCharsets;

import org.junit.jupiter.api.Test;

import hr.fer.oprpp1.custom.scripting.elems.*;
import hr.fer.oprpp1.custom.scripting.nodes.*;
import hr.fer.oprpp1.custom.scripting.parser.*;

public class TestParser {
	
	@Test
	public void simpleTest() {
		String test="Bok {$= i $}";
		SmartScriptParser parser = new SmartScriptParser(test);
		DocumentNode actual = parser.getDocumentNode();
		TextNode tn = new TextNode("Bok ");
		ElementVariable ev = new ElementVariable("i");
		EchoNode en = new EchoNode(new Element[] {ev});
		DocumentNode expected = new DocumentNode();
		expected.addChildNode(tn);
		expected.addChildNode(en);
		assertEquals(expected, actual);
	}
	
	@Test
	public void forTest() {
		String test="{$for i 1 10$} Hej {$= er$}{$end$}";
		SmartScriptParser parser = new SmartScriptParser(test);
		DocumentNode actual = parser.getDocumentNode();
		ElementVariable ev = new ElementVariable("i");
		ElementConstantInteger ev1 = new ElementConstantInteger(1);
		ElementConstantInteger ev2 = new ElementConstantInteger(10);
		ForLoopNode fln = new ForLoopNode(new Element[] {ev,ev1,ev2});
		TextNode tn = new TextNode(" Hej ");
		ElementVariable ev3 = new ElementVariable("er");
		EchoNode en = new EchoNode(new Element[] {ev3});
		fln.addChildNode(tn);
		fln.addChildNode(en);
		DocumentNode expected = new DocumentNode();
		expected.addChildNode(fln);
		assertEquals(expected, actual);
	}
	@Test
	public void allElementsTest() {
		String test="{$for i 1 10$} Hej {$= er @fer-1 0.5\"pero\"$}{$end$}";
		SmartScriptParser parser = new SmartScriptParser(test);
		DocumentNode actual = parser.getDocumentNode();
		ElementVariable ev = new ElementVariable("i");
		ElementConstantInteger ev1 = new ElementConstantInteger(1);
		ElementConstantInteger ev2 = new ElementConstantInteger(10);
		ForLoopNode fln = new ForLoopNode(new Element[] {ev,ev1,ev2});
		TextNode tn = new TextNode(" Hej ");
		ElementVariable ev3 = new ElementVariable("er");
		ElementFunction ev4 = new ElementFunction("fer");
		ElementConstantInteger ev5 = new ElementConstantInteger(-1);
		ElementConstantDouble ev6 = new ElementConstantDouble(0.5);
		ElementString ev7 = new ElementString("pero");
		EchoNode en = new EchoNode(new Element[] {ev3,ev4,ev5,ev6,ev7});
		fln.addChildNode(tn);
		fln.addChildNode(en);
		DocumentNode expected = new DocumentNode();
		expected.addChildNode(fln);
		assertEquals(expected, actual);
	}
	
	@Test
	public void forInForTest() {
		String test="{$for i 1 10$}{$for i 1 10$} Hej {$= er$}{$end$}{$end$}";
		SmartScriptParser parser = new SmartScriptParser(test);
		DocumentNode actual = parser.getDocumentNode();
		ElementVariable ev = new ElementVariable("i");
		ElementConstantInteger ev1 = new ElementConstantInteger(1);
		ElementConstantInteger ev2 = new ElementConstantInteger(10);
		ForLoopNode fln = new ForLoopNode(new Element[] {ev,ev1,ev2});
		TextNode tn = new TextNode(" Hej ");
		ElementVariable ev3 = new ElementVariable("er");
		EchoNode en = new EchoNode(new Element[] {ev3});
		fln.addChildNode(tn);
		fln.addChildNode(en);
		ForLoopNode fln1 = new ForLoopNode(new Element[] {ev,ev1,ev2});
		fln1.addChildNode(fln);
		DocumentNode expected = new DocumentNode();
		expected.addChildNode(fln1);
		assertEquals(expected, actual);
	}
	@Test
	public void notClosedForTest() {
		String test="{$for i 1 10$}{$for i 1 10$} Hej {$= er$}{$end$}";
		assertThrows(SmartScriptParserException.class, ( )->new SmartScriptParser(test));

	}
	
	@Test
	public void Primjer1Test() {
		String text = readExample(1);
		SmartScriptParser parser = new SmartScriptParser(text);
		DocumentNode actual = parser.getDocumentNode();
		TextNode tn = new TextNode("Ovo je \nsve jedan text node\n");
		DocumentNode expected = new DocumentNode();
		expected.addChildNode(tn);
		assertEquals(expected, actual);
	}
	
	@Test
	public void Primjer2Test() {
		String text = readExample(2);
		SmartScriptParser parser = new SmartScriptParser(text);
		DocumentNode actual = parser.getDocumentNode();
		TextNode tn = new TextNode("Ovo je \n"
				+ "sve jedan {$ text node\n"
				+ "");
		DocumentNode expected = new DocumentNode();
		expected.addChildNode(tn);
		assertEquals(expected, actual);
	}
	
	@Test
	public void Primjer3Test() {
		String text = readExample(3);
		SmartScriptParser parser = new SmartScriptParser(text);
		DocumentNode actual = parser.getDocumentNode();
		TextNode tn = new TextNode("Ovo je \n"
				+ "sve jedan \\{$text node\n"
				+ "");
		DocumentNode expected = new DocumentNode();
		expected.addChildNode(tn);
		assertEquals(expected, actual);
	}
	
	@Test
	public void Primjer4Test() {
		String text = readExample(4);
		assertThrows(SmartScriptParserException.class, ( )->new SmartScriptParser(text));
	}
	
	@Test
	public void Primjer5Test() {
		String text = readExample(5);
		assertThrows(SmartScriptParserException.class, ( )->new SmartScriptParser(text));
	}
	
	@Test
	public void Primjer6Test() {
		String text = readExample(6);
		SmartScriptParser parser = new SmartScriptParser(text);
		DocumentNode actual = parser.getDocumentNode();
		TextNode tn = new TextNode("Ovo je OK ");
		ElementTagName etn = new ElementTagName("=");
		ElementString es = new ElementString("String ide\n"
				+ "u više redaka\n"
				+ "čak tri\""); 
		EchoNode en = new EchoNode(new Element[] {es});
		TextNode tn1 = new TextNode("\n");
		DocumentNode expected = new DocumentNode();
		expected.addChildNode(tn);
		expected.addChildNode(en);
		expected.addChildNode(tn1);
		assertEquals(expected, actual);
	}
	
	@Test
	public void Primjer7Test() {
		String text = readExample(7);
		SmartScriptParser parser = new SmartScriptParser(text);
		DocumentNode actual = parser.getDocumentNode();
		TextNode tn = new TextNode("Ovo je isto OK ");
		ElementTagName etn = new ElementTagName("=");
		ElementString es = new ElementString( "String ide"
				+ "\nu \"više\"" 
				+ "\nredaka"
				+ "\novdje a stvarno četiri"); 
		EchoNode en = new EchoNode(new Element[] {es});
		DocumentNode expected = new DocumentNode();
		expected.addChildNode(tn);
		expected.addChildNode(en);
		assertEquals(expected, actual);
	}
	
	@Test
	public void Primjer8Test() {
		String text = readExample(8);
		assertThrows(SmartScriptParserException.class, ( )->new SmartScriptParser(text));
	}
	
	@Test
	public void Primjer9Test() {
		String text = readExample(9);
		assertThrows(SmartScriptParserException.class, ( )->new SmartScriptParser(text));
	}
	
	@Test
	public void invalidForTest() {
		String test="{$for i 1 @fer$} Hej {$= er$}{$end$}";
		assertThrows(SmartScriptParserException.class, ( )->new SmartScriptParser(test));
	}
	
	
	private String readExample(int n) {
		  try(InputStream is = this.getClass().getClassLoader().getResourceAsStream("extra/primjer"+n+".txt")) {
		    if(is==null) throw new RuntimeException("Datoteka extra/primjer"+n+".txt je nedostupna.");
		    byte[] data = is.readAllBytes();
		    String text = new String(data, StandardCharsets.UTF_8);
		    return text;
		  } catch(IOException ex) {
		    throw new RuntimeException("Greška pri čitanju datoteke.", ex);
		  }
		}
}
