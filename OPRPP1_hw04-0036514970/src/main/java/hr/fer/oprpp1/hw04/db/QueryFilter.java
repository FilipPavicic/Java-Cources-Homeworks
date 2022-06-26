package hr.fer.oprpp1.hw04.db;

import java.util.List;
public class QueryFilter implements IFilter{
	List<ConditionalExpression> query;
	
	public QueryFilter(List<ConditionalExpression> query) {
		super();
		this.query = query;
	}



	@Override
	public boolean accepts(StudentRecord record) {
		return query.stream().allMatch(c ->c.getComparisonOperator().satisfied(
								c.getFieldGetter().get(record),
								c.getStringLiteral()));	
	}

}
