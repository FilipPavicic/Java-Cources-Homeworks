package hr.fer.oprpp1.hw02.prob1;

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
		state = LexerState.BASIC;
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
		
		String current = "";
		char tokenType = 'x';
		
		
		boolean isBackslashActive = false;
		
		for (; currentIndex < data.length; currentIndex++) {
			if(tokenType == 'x' ) {
				tokenType = charType(data[currentIndex]);
				if(tokenType == 'x') continue;
			}
			
			if(state == LexerState.BASIC) {
				if(tokenType == 's' ) {
					current += data[currentIndex];
					currentIndex++;
					break;
				}
				
				if(isBackslashActive) {
					if(charType(data[currentIndex]) == 'n') current += data[currentIndex];
					else if(data[currentIndex] == '\\' ) current += '\\';
					else throw new LexerException("Nakon navedenog \\ mora se pojaviti ili broj ili još jedan \\, a  pojavilo se "
							+data[currentIndex]);
					isBackslashActive = false;
					continue;
				}
				
				if(tokenType == charType(data[currentIndex])) {
					if(data[currentIndex] == '\\') isBackslashActive = true;
					else current += data[currentIndex];
					continue;
				}
			}
			else {
				if(tokenType == 's' && data[currentIndex] == '#') {
					current += data[currentIndex];
					currentIndex++;
					break;
				}
				
				tokenType = 'w';
				
				if(charType(data[currentIndex]) == 'x' || data[currentIndex] == '#') break;
				
				current += data[currentIndex];
				continue;
			}
				
			break;
		}
		if(isBackslashActive) throw new LexerException("Nakon navedenog \\ mora se pojaviti ili broj ili još jedan \\,"
				+ ",a ulazni tekst je došao do kraja");
		
		if(current == "#") changeState();
		
		token = tokenByType(tokenType,current);
		return token;
		
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
	 * Na temelju parametra <code>tokenType</code> definira parametre 
	 * za kreiranje novog Objekta tipa Token 
	 * 
	 * @param tokenType vrsta Tokena koji se kreira
	 * @param current String koji se parsira u određeni tip
	 * @return  novi instanca objekta Token 
	 * @throws IllegalAccessException tokenType nije prepoznati.
	 * 
	 */
	private Token tokenByType(char tokenType, String current) {
		
		switch (tokenType) {
			case 'n' : {
				try {
				return new Token(TokenType.NUMBER, Long.parseLong(current));
				}catch (NumberFormatException e) {
					throw new LexerException(e.getMessage());
				}
			}
			case 'w' : return new Token(TokenType.WORD, current);
			case 's' : return new Token(TokenType.SYMBOL, Character.valueOf(current.charAt(0)));
			case 'x' : return new Token(TokenType.EOF, null);
			default :
				throw new IllegalArgumentException("Unexpected value: " + tokenType);
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
	private char  charType(Character c)  {
		if(c >= '0' && c <= '9') return 'n';
		if(c >= 'a' && c <= 'z') return 'w';
		if(c >= 'A' && c <= 'Z') return 'w';
		if(c == 'č' || c == 'Č') return 'w';
		if(c == 'ć' || c == 'Ć') return 'w';
		if(c == 'đ' || c == 'Đ') return 'w';
		if(c == 'š' || c == 'Š') return 'w';
		if(c == 'ž' || c == 'Ž') return 'w';
		if(c == '\\') return 'w';
		if(c =='\r' || c == '\n' || c == '\t' || c== ' ') return 'x';
		return 's';
	}
	
	private void changeState() {
		if(state == LexerState.BASIC) state = LexerState.EXTENDED;
		else state = LexerState.BASIC;
	}
	

}
