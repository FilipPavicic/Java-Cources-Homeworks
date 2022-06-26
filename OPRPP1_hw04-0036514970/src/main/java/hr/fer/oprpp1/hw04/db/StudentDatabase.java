package hr.fer.oprpp1.hw04.db;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

/**
 * Razred predstavlja implentaciju baze podataka za studente 
 *
 * @author Filip Pavicic
 *
 */
public class StudentDatabase {
	private List<StudentRecord> list = new ArrayList<>();
	private Map<String,StudentRecord> indexMap = new HashMap<>();
	
	public StudentDatabase(List<String> rows) {
		rows.stream()
			.forEach(s -> {
				String[] args = s.split("\t");
				StudentRecord sr = new StudentRecord(args[0], args[1], args[2], Integer.parseInt(args[3])) ;
				list.add(sr);
				indexMap.put(args[0], sr);
			});
	}
	public StudentRecord forJMBAG(String jmbag) {
		return indexMap.get(jmbag);
	}
	
	public List<StudentRecord> filter(IFilter filter){
		return this.list
				.stream()
				.filter(c -> filter.accepts(c))
				.collect(Collectors.toList());
	}
	public List<StudentRecord> getList() {
		return list;
	}
	
}
