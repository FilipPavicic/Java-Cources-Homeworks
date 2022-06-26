package hr.fer.oprpp1.custom.scripting;

import static org.junit.jupiter.api.Assertions.*;
import org.junit.jupiter.api.Test;

import hr.fer.oprpp1.custom.scripting.elems.*;
import hr.fer.oprpp1.custom.scripting.lexer.*;

public class TestLexer {

	
	@Test
	public void testNotNull() {
		Lexer lexer = new Lexer("");
		
		assertNotNull(lexer.nextToken(), "Token was expected but null was returned.");
	}


	@Test
	public void testNullInput() {
		// must throw!
		assertThrows(NullPointerException.class, () -> new Lexer(null));
	}
	
	@Test
	public void testEmptyText() {
		Lexer lexer = new Lexer("");
		Token correctData[] = {
				new Token(TokenType.EOF, null)
			};

			checkTokenStream(lexer, correctData);
		
	}
	
	@Test
	public void testOnlyText() {
		Lexer lexer = new Lexer("Pozdrav");
		Token correctData[] = {
				new Token(TokenType.TEXT, "Pozdrav"),
				new Token(TokenType.EOF, null)
			};

			checkTokenStream(lexer, correctData);
		
	}
	
	@Test
	public void testSpecialText() {
		Lexer lexer = new Lexer(" \\\\");
		Token correctData[] = {
				new Token(TokenType.TEXT, " \\"),
				new Token(TokenType.EOF, null)
			};

			checkTokenStream(lexer, correctData);
		
	}
	
	@Test
	public void testSpecialText1() {
		Lexer lexer = new Lexer(" \\{$=i$}");
		Token correctData[] = {
				new Token(TokenType.TEXT, " {$=i$}"),
				new Token(TokenType.EOF, null)
			};

			checkTokenStream(lexer, correctData);
		
	}
	@Test
	public void testIncorrectText() {
		Lexer lexer = new Lexer("\\");
		assertThrows(LexerException.class, ()-> lexer.nextToken());
		
	}
	
	@Test
	public void testSimpleTag() {
		Lexer lexer = new Lexer("{$=i$}");
		Token correctData[] = {
				new Token(TokenType.TAG_NAME, new ElementTagName("=")),
				new Token(TokenType.VARIABLE, new ElementVariable("i")),
				new Token(TokenType.EOF, null)
			};

			checkTokenStream(lexer, correctData);
	}
	
	@Test
	public void testFuncionTag() {
		Lexer lexer = new Lexer("{$=@fer$}");
		Token correctData[] = {
				new Token(TokenType.TAG_NAME, new ElementTagName("=")),
				new Token(TokenType.FUNCION, new ElementFunction("fer")),
				new Token(TokenType.EOF, null)
			};

			checkTokenStream(lexer, correctData);
	}
	
	@Test
	public void testSimpleTextTag() {
		Lexer lexer = new Lexer("Bok {$=i$} cao");
		Token correctData[] = {
				new Token(TokenType.TEXT, "Bok "),
				new Token(TokenType.TAG_NAME, new ElementTagName("=")),
				new Token(TokenType.VARIABLE, new ElementVariable("i")),
				new Token(TokenType.TEXT, " cao"),
				new Token(TokenType.EOF, null)
			};

			checkTokenStream(lexer, correctData);		
	}
	
	@Test
	public void testTwoTag() {
		Lexer lexer = new Lexer("{$=i$}{$for 1 er$}");
		Token correctData[] = {
				new Token(TokenType.TAG_NAME, new ElementTagName("=")),
				new Token(TokenType.VARIABLE, new ElementVariable("i")),
				new Token(TokenType.TAG_NAME, new ElementTagName("for")),
				new Token(TokenType.NUMBER, new ElementConstantInteger(Integer.valueOf(1))),
				new Token(TokenType.VARIABLE, new ElementVariable("er")),
				new Token(TokenType.EOF, null)
			};

			checkTokenStream(lexer, correctData);		
	}
	
	@Test
	public void testUpperTagName() {
		Lexer lexer = new Lexer("{$ FOR 1 er$}");
		Token correctData[] = {
				new Token(TokenType.TAG_NAME, new ElementTagName("for")),
				new Token(TokenType.NUMBER, new ElementConstantInteger(Integer.valueOf(1))),
				new Token(TokenType.VARIABLE, new ElementVariable("er")),
				new Token(TokenType.EOF, null)
			};

			checkTokenStream(lexer, correctData);		
	}
	
	@Test
	public void testTagWithoutSpaces() {
		Lexer lexer = new Lexer("{$for-1er+-1.43$}");
		Token correctData[] = {
				new Token(TokenType.TAG_NAME, new ElementTagName("for")),
				new Token(TokenType.NUMBER, new ElementConstantInteger(Integer.valueOf(-1))),
				new Token(TokenType.VARIABLE, new ElementVariable("er")),
				new Token(TokenType.OPERATORS, new ElementOperator("+")),
				new Token(TokenType.NUMBER, new ElementConstantDouble(Double.valueOf(-1.43))),
				new Token(TokenType.EOF, null)
			};

			checkTokenStream(lexer, correctData);		
	}
	
	@Test
	public void testTagWithString() {
		Lexer lexer = new Lexer("{$for \"Pero \\\"Marko\\\" \n\"\"Hej\"$}");
		Token correctData[] = {
				new Token(TokenType.TAG_NAME, new ElementTagName("for")),
				new Token(TokenType.STRING, new ElementString("Pero \"Marko\" \n")),
				new Token(TokenType.STRING, new ElementString("Hej")),
				new Token(TokenType.EOF, null)
			};

			checkTokenStream(lexer, correctData);		
	}
	
	@Test
	public void testTagWithExpression() {
		Lexer lexer = new Lexer("{$=i i+1 i--Zagreb$}");
		Token correctData[] = {
				new Token(TokenType.TAG_NAME, new ElementTagName("=")),
				new Token(TokenType.VARIABLE , new ElementVariable("i")),
				new Token(TokenType.VARIABLE , new ElementVariable("i")),
				new Token(TokenType.OPERATORS,new ElementOperator("+")),
				new Token(TokenType.NUMBER,new ElementConstantInteger(Integer.valueOf(1))),
				new Token(TokenType.VARIABLE , new ElementVariable("i")),
				new Token(TokenType.OPERATORS,new ElementOperator("-")),
				new Token(TokenType.OPERATORS,new ElementOperator("-")),
				new Token(TokenType.VARIABLE , new ElementVariable("Zagreb")),
				new Token(TokenType.EOF, null)
			};

			checkTokenStream(lexer, correctData);		
	}
	@Test
	public void testNotCloseTag() {
		Lexer lexer = new Lexer("{$=i");
		assertThrows(LexerException.class, ()-> {
			lexer.nextToken();
			lexer.nextToken();
			lexer.nextToken();
		});		
	}
	@Test
	public void testNotCloseString() {
		Lexer lexer = new Lexer("{$=i \"Marko$}");
		assertThrows(LexerException.class, ()-> {
			lexer.nextToken();
			lexer.nextToken();
			lexer.nextToken();
		});		
	}
	
	@Test
	public void testInvalidString() {
		Lexer lexer = new Lexer("{$\"Ma \\ rko\"$}");
		assertThrows(LexerException.class, ()-> {
			lexer.nextToken();
		});		
	}
	
	// Helper method for checking if lexer generates the same stream of tokens
	// as the given stream.
	private void checkTokenStream(Lexer lexer, Token[] correctData) {
		int counter = 0;
		for(Token expected : correctData) {
			Token actual = lexer.nextToken();
			String msg = "Checking token "+counter + ":";
			assertEquals(expected.getType(), actual.getType(), msg);
			assertEquals(expected.getValue(), actual.getValue(), msg);
			counter++;
		}
	}
}