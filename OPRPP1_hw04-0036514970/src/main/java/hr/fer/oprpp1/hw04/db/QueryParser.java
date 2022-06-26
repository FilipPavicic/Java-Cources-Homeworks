package hr.fer.oprpp1.hw04.db;

import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;
import java.util.NoSuchElementException;

/**
 * Razred predstavlja implemtaciju parser za upite nad bauom
 * 
 * @author Filip Pavicic
 *
 */
public class QueryParser {
	private List<ConditionalExpression> query;

	/**
	 * Konstruktor parsira dobiveni query u listu izraza
	 * 
	 * @param queryString dobiveni query
	 */
	public QueryParser (String queryString) {
		query = new ArrayList<ConditionalExpression>();
		parser(queryString);
	}

	private void parser(String queryString) {
		IFieldValueGetter fieldValue;
		IComparisonOperator compOper;
		String literal;
		Iterator<String> tokens  = lexer(queryString).iterator();
		while(tokens.hasNext()) {
			try {
				String field = tokens.next();
				switch (field) {
					case "jmbag" -> fieldValue = FieldValueGetters.JMBAG;
					case "firstName" -> fieldValue = FieldValueGetters.FIRST_NAME;
					case "lastName" -> fieldValue = FieldValueGetters.LAST_NAME;
					default -> throw new IllegalArgumentException("Stupac nije pronađen: " + field);
				}
				String operator = tokens.next();
				switch (operator) {
					case "<" -> compOper = ComparisonOperators.LESS;
					case "<=" -> compOper = ComparisonOperators.LESS_OR_EQUALS;
					case ">" -> compOper = ComparisonOperators.GREATER;
					case ">=" -> compOper = ComparisonOperators.GREATER_OR_EQUALS;
					case "=" -> compOper = ComparisonOperators.EQUALS;
					case "!=" -> compOper = ComparisonOperators.NOT_EQUALS;
					case "LIKE" -> compOper = ComparisonOperators.LIKE;
					default -> throw new IllegalArgumentException("Operator nije pronađen: " + operator);
				}
				literal = tokens.next();

				if(tokens.hasNext()) {
					if(!tokens.next().toLowerCase().equals("and")) 
						throw new IllegalArgumentException("Preveliki broj argumenata provjerite da li Vam možda ne fali logički operator");
				}
				query.add(new ConditionalExpression(fieldValue, literal, compOper));

			}
			catch (NoSuchElementException e) {
				throw new IllegalArgumentException("Query Error - query nije potpun");
			}
		}

	}

	public List<String> lexer(String queryString) {
		List<String> tokens = new ArrayList<String>();
		char[] znakovi = queryString.toCharArray();
		int type = Character.SPACE_SEPARATOR;
		String token = "";
		for (int i = 0; i < znakovi.length; i++) {
			char c = znakovi[i];
			if(type == Character.SPACE_SEPARATOR && Character.isSpaceChar(c)) continue;
			
			if(type == Character.SPACE_SEPARATOR) type = Character.getType(c);
			
			switch (type) {
				case Character.LOWERCASE_LETTER,Character.UPPERCASE_LETTER -> {
					if(!Character.isLetter(c)) {
						tokens.add(token);
						token = "";
						type = Character.SPACE_SEPARATOR;
						i--;
						continue;
					}
					token += c;
				}
				case Character.MATH_SYMBOL -> {
					if(Character.getType(c) != Character.MATH_SYMBOL) {
						tokens.add(token);
						type = Character.SPACE_SEPARATOR;
						i--;
						token = "";
						continue;
					}
					token += c;
				}
				case Character.OTHER_PUNCTUATION -> {
					if(c =='!') {
						token += c;
						type = Character.MATH_SYMBOL;
						continue;
					}
						
					for(i++ ; i < znakovi.length; i++) {
						c = znakovi[i];
						if(znakovi[i] == '\"') {
							tokens.add(token);
							type = Character.SPACE_SEPARATOR;
							token = "";
							break;
						}
						token += c;
					}
					if(c != '\"') throw new IllegalArgumentException("Literal nije zatvoren na kraju");
				}
				default -> throw new IllegalArgumentException("Ne postoji operacija koja počinje s " +c);
			}
		}
		return tokens;
	}
	 /**
	  * Vraća listu upita 
	  * 
	  * @return lista upita
	  */
	public List<ConditionalExpression> getQuery() {
		return query;
	}
	
	/**
	 * Provjerava može li se upiti izvršiti direktnom metodom
	 * 
	 * @return <code>true<code> ako postoji samo jedan upit koji je oblika JMBAG=[nešto], inače false
	 */
	public boolean isDirectQuery() {
		if(query.size() != 1) return false;
		
		ConditionalExpression testExp = new ConditionalExpression(FieldValueGetters.JMBAG, query.get(0).getStringLiteral(), ComparisonOperators.EQUALS);
		if(!testExp.equals(query.get(0))) return false;
		
		return true;
	}
	/**
	 * Dohvaća jmbag koji se provjerava ako je moguće direktno provesti upit
	 * 
	 * @return vrijednost JMBAG-a
	 * @throws IllegalStateException ako upit nije provediv direktno
	 */
	public String getQueriedJMBAG() {
		if(!isDirectQuery()) throw new IllegalStateException("Query nije moguće pirmjeniti direktno");
		
		return query.get(0).getStringLiteral();
	}	
}
