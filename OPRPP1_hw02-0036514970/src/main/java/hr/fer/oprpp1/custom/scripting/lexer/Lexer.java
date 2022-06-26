package hr.fer.oprpp1.custom.scripting.lexer;


import hr.fer.oprpp1.custom.collections.ArrayIndexedCollection;
import hr.fer.oprpp1.custom.scripting.elems.*;


public class Lexer {
	private char[] data;  // ulazni tekst
	private Token token;  // trenutni token
	private int currentIndex;  // indeks prvog neobrađenog znaka
	private LexerState state;
	
	/**
	 * konstruktor prima ulazni tekst koji se tokenizira
	 * 
	 * @param text ulazni text
	 */
	public Lexer(String text) {
		data=text.toCharArray();
		currentIndex = 0;
		state = LexerState.TEXT;
	}
	
	/**
	 * Vraća polje Tokena za zadani tekst u konstruktoru.
	 * 
	 * @return polje tokena
	 */
	public Token[] productTokens() {
		ArrayIndexedCollection coll= new ArrayIndexedCollection();
		
		this.nextToken();
		while(token.getType() != TokenType.EOF) {
			coll.add(token);
			this.nextToken();
		}
		Object[] objects = coll.toArray();
		Token[] tokens = new Token[objects.length];
		for (int i = 0; i < objects.length; i++) {
			tokens[i] = (Token) objects[i];
		}
		return tokens;
	}
	
	/**
	 * vraća zadnji generirani token; može se pozivati
	 * više puta; ne pokreće generiranje sljedećeg tokena
	 * 
	 * @return
	 */
	public Token getToken() {
		return token;
	}
	
	/**
	 * Metoda postavlja vrijednost načina rada.
	 * 
	 * @param state način rada koji se postavlja.
	 * @throws NullPointerException ako je state <code>null</code>
	 */
	public void setState(LexerState state) {
		if(state == null) throw new NullPointerException("state vrijednost ne može biti null");
		this.state = state;
	}
	/**
	 *  generira i vraća sljedeći token
	 *  
	 * @return Token 
	 * @throws LexerException ako dođe do pogreške
	 */
		
	public Token nextToken() {
		if(token != null && token.type == TokenType.EOF) throw new 
			LexerException("Nije moguće kreirati sljedeći token nakon tokena tipa EOF");
		
		if(state == LexerState.TEXT) return lexerForText();
		
		return lexerForTag();
		
		
	}
	
	
	private Token lexerForTag() {
		TokenType tokenType = TokenType.SPACE;
		boolean isBackslashActive = false;
		boolean isMinusActive = false;
		boolean isStringOpen = false;
		boolean isLeftBraceActive = false;
		String current = "";
		
		for (; currentIndex < data.length; currentIndex++) {
			char ch = data[currentIndex];
			if(tokenType == TokenType.SPACE ) {
				tokenType = charType(ch);
				if(tokenType == TokenType.SPACE) continue;
				if(tokenType == TokenType.STRING) {
					isStringOpen = true;
					continue;
				}
				if(tokenType == TokenType.FUNCION) continue;
			}
			
			if(current == "" && ch == '$') {
				isLeftBraceActive = true;
				continue;
			}
			
			if(isLeftBraceActive) {
				if(ch == '}') {
					changeState();
					isLeftBraceActive = false;
					currentIndex++;
					break;
				}
			}
			
			if(tokenType == TokenType.STRING) {
				if(isBackslashActive) {
					if(ch == '\\')  current += '\\';
					else if(ch == '\"') current += '\"';
					else throw new LexerException("Nakon navedenog \\ mora se pojaviti ili \" ili još jedan \\, a  pojavilo se" + ch);
					isBackslashActive = false;
					continue;
				}
				
				if(ch == '\\') {
					isBackslashActive = true;
					continue;
				}
				
				if(ch == '\"') {
					currentIndex++;
					isStringOpen = false;
					break;
				}
				
				current += ch;
				continue;
			}
			
			if(tokenType == TokenType.NUMBER) {
				boolean hasDot = false;
				if(ch == '.' && hasDot == false) {
					hasDot = true;
					current += ch;
					continue;
				}
				
				if(charType(ch) == TokenType.NUMBER) {
					current += ch;
					continue;
				};
				
				break;
			}
			
			if(tokenType == TokenType.OPERATORS) {
				if(ch == '-' && isMinusActive == false) {
					isMinusActive = true;
					current += ch;
					continue;
				}
				
				if(isMinusActive) {
					isMinusActive = false;
					
					if(charType(ch) == TokenType.NUMBER) {	
						current += ch;
						tokenType = TokenType.NUMBER;
						continue;
					}
					break;
				}
				
				current += ch;
				currentIndex++;
				break;
			}
			
			if(tokenType == TokenType.FUNCION) {
				if(isValidName(current,ch)) {
					current += ch;
					continue;
				}
				break;
			}
			
			if(tokenType == TokenType.VARIABLE) {
				if(isValidName(current,ch)) {
					current += ch;
					continue;
				}
				
				if(TagNames.checkIfTagName(current.toLowerCase())) 
					{
					current = current.toLowerCase();
					tokenType = TokenType.TAG_NAME;
					}
				break;
			}
			
			if(tokenType == TokenType.TAG_NAME) {
				current += ch;
				currentIndex++;
				break;
			}
			
			throw new LexerException(ch +" nije dozvoljen znak u Tag elementu");
		}
		if(isLeftBraceActive) throw new LexerException("$ nije dozvoljen znak u Tag elementu na zadnjoj poziciji");
		if(isStringOpen) throw new LexerException("String nije zatvoren, a ulazni tekst je došao do kraja");
		if(isBackslashActive) throw new LexerException("Nakon navedenog \\ u Stringu se mora pojaviti ili \\ ili \" "
				+ ",a ulazni tekst je došao do kraja");
		
		if(current == "" && state == LexerState.TEXT) return lexerForText();
		if(current == "" && state == LexerState.TAG) throw new LexerException("Tag nije adekvatno zatvoren");
		token = tokenByType(tokenType, current);
		return token;			
	}
	
	private boolean isValidName(String current, char ch) {
		if(charType(ch) == TokenType.VARIABLE) return true;
		if(current.length() == 0) return false;
		if(charType(ch) == TokenType.UNDERSCORE) return true;
		if(charType(ch) == TokenType.NUMBER) return true;
		return false;
	}

	private Token lexerForText() {
		String current = "";
		boolean isBackslashActive = false;
		boolean isLeftBraceActive = false;
		
		for (; currentIndex < data.length; currentIndex++) {
			if(isBackslashActive) {
				if(data[currentIndex] == '{') current += '{';
				else if(data[currentIndex] == '\\' ) current += '\\';
				
				else throw new LexerException("Nakon navedenog \\ mora se pojaviti ili { ili \\, a pojavilo se "
						+data[currentIndex]);
				
				isBackslashActive = false;
				continue;
			}
			if(isLeftBraceActive) {
				if(data[currentIndex] == '$') {
					changeState();
					currentIndex++;
					isLeftBraceActive = false;
					break;
				}
				
				current += '{';
				isLeftBraceActive = false;
			}
			
			if(data[currentIndex] == '{') {
				isLeftBraceActive = true;
				continue;
			}
			
			if(data[currentIndex] == '\\') {
				isBackslashActive = true;
				continue;
			}
			
			current += data[currentIndex];
			
			
		}
		
		if(isBackslashActive)  throw new LexerException("Text ne smije završiti s jednostrukim pojavljivanjem backslah simbola");
		if(isLeftBraceActive) current += '{';
		
		if(current == "" && state == LexerState.TAG) return lexerForTag();
		
		token = current == "" ? new Token(TokenType.EOF, null) : new Token(TokenType.TEXT, String.valueOf(current));
		return token;
		
	}
	
	/**
	 * Na temelju parametra <code>tokenType</code> definira parametre 
	 * za kreiranje novog Objekta tipa Token 
	 * 
	 * @param tokenType vrsta Tokena koji se kreira
	 * @param current String koji se parsira u određeni tip
	 * @return  novi instanca objekta Token 
	 * @throws IllegalAccessException tokenType nije prepoznati.
	 * 
	 */
	private Token tokenByType(TokenType tokenType, String current) {
		
		switch (tokenType) {
			case NUMBER : {
				try {
					if(current.contains(".")){
						return new Token(TokenType.NUMBER,new  ElementConstantDouble(Double.parseDouble(current)));
					}
					return new Token(TokenType.NUMBER,new ElementConstantInteger(Integer.parseInt(current)));
				}catch (NumberFormatException e) {
					throw new LexerException(e.getMessage());
				}
			}
			case VARIABLE : return new Token(TokenType.VARIABLE,new ElementVariable(String.valueOf(current))); 
			case FUNCION : return new Token(TokenType.FUNCION,new ElementFunction(String.valueOf(current)));
			case STRING : return new Token(TokenType.STRING, new ElementString(String.valueOf(current)));
			case OPERATORS : return new Token(TokenType.OPERATORS,new ElementOperator(String.valueOf(current)));
			case TAG_NAME : return new Token(TokenType.TAG_NAME, new ElementTagName(String.valueOf(current)));
			case EOF : return new Token(TokenType.EOF, null);
			default :
				throw new LexerException("Unexpected value: " + tokenType);
		}
	}
	
	/**
	 * Određuje vrstu znaka
	 * 
	 * @param c 
	 * @return 
	 * 'n' -> znak je broj,
	 * 'w' -> znak je slovo,
	 * 'x' -> znak je specijalan znak koji se zanemariju,
	 * 's' -> znak je simbol,
	 * '#' -> znak je #.
	 */
	private TokenType  charType(char c)  {
		if(c >= '0' && c <= '9') return TokenType.NUMBER;
		if(c >= 'a' && c <= 'z') return TokenType.VARIABLE;
		if(c >= 'A' && c <= 'Z') return TokenType.VARIABLE;
		if(c == 'č' || c == 'Č') return TokenType.VARIABLE;
		if(c == 'ć' || c == 'Ć') return TokenType.VARIABLE;
		if(c == 'đ' || c == 'Đ') return TokenType.VARIABLE;
		if(c == 'š' || c == 'Š') return TokenType.VARIABLE;
		if(c == 'ž' || c == 'Ž') return TokenType.VARIABLE;
		if(c == '=') return TokenType.TAG_NAME;
		if(c == '_') return TokenType.UNDERSCORE;
		if(c == '\"') return TokenType.STRING;
		if(c =='\r' || c == '\n' || c == '\t' || c== ' ') return TokenType.SPACE;
		if(c == '@') return TokenType.FUNCION;
		if(c == '+' || c == '-' || c == '*' || c == '\\' || c == '^') return TokenType.OPERATORS;
		return TokenType.EOF;
	}
	
	private void changeState() {
		if(state == LexerState.TAG) state = LexerState.TEXT;
		else state = LexerState.TAG;
	}
	

}
