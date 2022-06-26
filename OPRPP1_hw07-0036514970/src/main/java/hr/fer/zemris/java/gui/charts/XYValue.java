package hr.fer.zemris.java.gui.charts;

public class XYValue {
	private final int x;
	private final int y;
	
	public XYValue(int x, int y) {
		super();
		this.x = x;
		this.y = y;
	}
	
	
	public int getX() {
		return x;
	}



	public int getY() {
		return y;
	}

	public static XYValue parser(String text) {
		try {
			String[] parts = text.split(",");
			int x = Integer.parseInt(parts[0]);
			int y = Integer.parseInt(parts[1]);	
			return new XYValue(x, y);
		}catch (NumberFormatException e) {
			throw new IllegalArgumentException(e.getMessage());
		}
	}
	
	
	
}
