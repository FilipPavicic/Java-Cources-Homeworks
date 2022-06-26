package hr.fer.oprpp1.custom.scripting.lexer;

public class Token {
	
	TokenType type;
	Object value;
	
	public Token(TokenType type, Object value) {
		super();
		this.type = type;
		this.value = value;
	}

	public TokenType getType() {
		return type;
	}

	public Object getValue() {
		return value;
	}
	
	
}
