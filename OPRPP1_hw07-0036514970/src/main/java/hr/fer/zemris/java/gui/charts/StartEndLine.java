package hr.fer.zemris.java.gui.charts;

/**
 * Klasa koja sprema informacije o Liniji početnu i krajnju točku linije
 * 
 * @author Filip Pavicic
 *
 */
public class StartEndLine {
	XYValue start;
	XYValue end;
	
	public StartEndLine(XYValue start, XYValue end) {
		super();
		this.start = start;
		this.end = end;
	}
	
	public StartEndLine(int x1, int y1, int x2, int y2) {
		start =  new XYValue(x1, y1);
		end = new XYValue(x2, y2);
	}
	
	
	public int x1() {
		return start.getX();
	}
	
	public int x2() {
		return end.getX();
	}
	
	public int y1() {
		return start.getY();
	}
	
	public int y2() {
		return end.getY();
	}

	@Override
	public String toString() {
		return "x1: " + x1() + ", y1: " + y1() + ", x2: " + x2() + ", y2: " + y2();
	}
	
	
	

}
