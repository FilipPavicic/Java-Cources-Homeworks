package hr.fer.oprpp1.hw04.db;

import static org.junit.jupiter.api.Assertions.*; 
import java.util.ArrayList;
import java.util.List;

import org.junit.jupiter.api.Test;

public class TestParser {
	
	@Test
	public void correctOneQuery() {
		QueryParser qp = new QueryParser("firstName LIKE\"Pe*\"");
		List<ConditionalExpression> expected = new ArrayList<>();
		expected.add(new ConditionalExpression(FieldValueGetters.FIRST_NAME, "Pe*", ComparisonOperators.LIKE));
		assertEquals(expected, qp.getQuery());
	}
	
	@Test
	public void correctTwoQuery() {
		QueryParser qp = new QueryParser("firstName LIKE\"Pe*\" aNd jmbag!=\"0012345678\"");
		List<ConditionalExpression> expected = new ArrayList<>();
		expected.add(new ConditionalExpression(FieldValueGetters.FIRST_NAME, "Pe*", ComparisonOperators.LIKE));
		expected.add(new ConditionalExpression(FieldValueGetters.JMBAG, "0012345678", ComparisonOperators.NOT_EQUALS));
		assertEquals(expected, qp.getQuery());
	}
	
	@Test
	public void correctMathOperatorQuery() {
		QueryParser qp = new QueryParser("firstName>\"Pe*\" aNd jmbag    <=          \"0012345678\"");
		List<ConditionalExpression> expected = new ArrayList<>();
		expected.add(new ConditionalExpression(FieldValueGetters.FIRST_NAME, "Pe*", ComparisonOperators.GREATER));
		expected.add(new ConditionalExpression(FieldValueGetters.JMBAG, "0012345678", ComparisonOperators.LESS_OR_EQUALS));
		assertEquals(expected, qp.getQuery());
	}

}
