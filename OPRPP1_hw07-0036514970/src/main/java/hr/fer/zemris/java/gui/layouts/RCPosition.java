package hr.fer.zemris.java.gui.layouts;

public class RCPosition  implements Comparable<RCPosition>{
	private final int row;
	private final int column;
	
	public RCPosition(int row, int column) {
		super();
		checker(row, column);
		this.row = row;
		this.column = column;
	}
	

	public int getRow() {
		return row;
	}


	public int getColumn() {
		return column;
	}


	private void checker(int r, int c) {
		if(r < 1 || r > 5) exp();
		if(c < 1 || c > 7) exp();
		if(r == 1 && (c > 1 && c < 6)) exp();
	}
		
		
	private void exp() {
		throw new CalcLayoutException();
	}


	public static RCPosition parse(String text) {
		try {
			String[] parts = text.split(",");
			int r = Integer.parseInt(parts[0]);
			int c = Integer.parseInt(parts[1]);	
			return new RCPosition(r, c);
		}catch (NumberFormatException e) {
			throw new IllegalArgumentException(e.getMessage());
		}
	}

	@Override
	public boolean equals(Object obj) {
		if (this == obj)
			return true;
		if (!(obj instanceof RCPosition))
			return false;
		RCPosition other = (RCPosition) obj;
		if (column != other.column)
			return false;
		if (row != other.row)
			return false;
		return true;
	}

	@Override
	public String toString() {
		return "RCPosition [row=" + row + ", column=" + column + "]";
	}


	@Override
	public int compareTo(RCPosition o) {
		int rez = Integer.valueOf(this.row).compareTo(o.row);
		if(rez ==  0) rez = Integer.valueOf(this.column).compareTo(o.column);
		return rez;
	}
	
	
	
	
	
	
	
	
	
	

}
