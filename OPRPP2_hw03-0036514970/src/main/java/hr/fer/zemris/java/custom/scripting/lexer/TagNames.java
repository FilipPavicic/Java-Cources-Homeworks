package hr.fer.zemris.java.custom.scripting.lexer;

public class TagNames {
	
	private static final String[] tagNames = {
			"for",
			"end",
			"="
	};
		
	public static boolean checkIfTagName(String s) {
		for (String string : tagNames) {
			if(s.equals(string)) return true;
		}
		return false;
	}
	
}
