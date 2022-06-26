package hr.fer.zemris.java.gui.charts;

public class DoublePoint {
	final double  x;
	final double  y;
	
	public DoublePoint(double x, double y) {
		this.x = x;
		this.y = y;
		
	}
	
	public DoublePoint sum(DoublePoint p) {
		return new DoublePoint(this.x + p.x, this.y + p.y);
	}
	
	public DoublePoint rotateClockwise(double angle) {
		return new DoublePoint(Math.cos(angle) * x + Math.sin(angle) * y, -Math.sin(angle) * x + Math.cos(angle) * y);
	}
	
}
