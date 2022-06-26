package hr.fer.zemris.java.gui.charts;

import java.util.Comparator;
import java.util.List;
import java.util.NoSuchElementException;

public class BarChart {
	List<XYValue> values;
	String xText;
	String yText;
	int Ymin;
	int Ymax;
	int Ydelta;
			
	public BarChart(List<XYValue> values, String xText, String yText, int ymin,
			int ymax, int ydelta) {
		
		if(ymin < 0) exception();
		if(ymax <= ymin) exception();
		if(values.stream().mapToInt(e -> e.getY()).min().orElseThrow(NoSuchElementException::new) < ymin) exception();
		
		this.values = values;
		this.xText = xText;
		this.yText = yText;
		Ydelta = ydelta;
		Ymin = ymin;
		Ymax =  (int) ((ymax - ymin) % ydelta == 0 ? ymax :  Math.ceil((ymax - ymin) * 1.09 / ydelta) * ydelta) ;
	}
	
	public BarChart() {
		// TODO Auto-generated constructor stub
	}

	private void exception() {
		throw new IllegalArgumentException();
		
	}
	
	
	
	
}
