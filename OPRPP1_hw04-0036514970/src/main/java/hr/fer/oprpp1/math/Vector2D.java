package hr.fer.oprpp1.math;

import static java.lang.Math.sqrt;
import static java.lang.Math.hypot;

/**
 * Razred predstavlja model nepromjenjivog 2D-vektora.
 * 
 * @author marcupic
 */
public class Vector2D {

	private double x;
	private double y;
	
	/**
	 * Stvara novi općeniti 2D vektor čije su komponente x i y.
	 * @param x komponenta uz jedinični vektor "i"
	 * @param y komponenta uz jedinični vektor "j"
	 */
	public Vector2D(double x, double y) {
		super();
		this.x = x;
		this.y = y;
	}
	
	public double getX() {
		return x;
	}

	public double getY() {
		return y;
	}
	

	@Override
	public boolean equals(Object obj) {
		if (this == obj)
			return true;
		if (!(obj instanceof Vector2D))
			return false;
		Vector2D other = (Vector2D) obj;
		if (Math.abs(other.x - x) > 0.0001)
			return false;
		if (Math.abs(other.x - x) > 0.0001)
			return false;
		return true;
	}
	
	

	@Override
	public String toString() {
		return "Vector2D [x=" + x + ", y=" + y + "]";
	}

	/**
	 * Dodaje dani vektor trenutnom.
	 * 
	 * @param other drugi vektor koji nadodajemo
	 * @throws NullPointerException ako je other <code>null</code>
	 */
	public void add(Vector2D offset) {
		this.x += offset.x;
		this.y += offset.y;
		return;
	}

	/**
	 * Računa sumu trenutnog vektora i predanog vektora: this+other i vraća rezultat kao novi vektor.
	 * 
	 * @param other drugi vektor koji nadodajemo
	 * @return novi vektor koji je jednak sumi trenutnog i drugog
	 * @throws NullPointerException ako je other <code>null</code>
	 */
	public Vector2D added(Vector2D offset) {
		return new Vector2D(this.x + offset.x, this.y + offset.y);
	}
	
	/**
	 * Trenutni vektor rotira za dani kut
	 * 
	 * @param angle kut za koji se rotira vektor.
	 */
	public void rotate(double angle) {
		double d = Math.hypot(this.x,this.y);
		double start_angle = Math.atan(y/x);
		if(x < 0) start_angle += Math.PI;
		angle += start_angle;
		this.x = Math.cos(angle) * d;
		this.y = Math.sin(angle) * d;
	}
	
	/**
	 * Rotira trenutni vektor za dani kut i rezultat vraća kao novi vektor.
	 * @param angle kut za koji se rotira vektor.
	 * @return novi vektor koji predstavlja trenutni vektor rotiran za kut.
	 */
	public Vector2D rotated(double angle) {
		double d = Math.hypot(this.x,this.y);
		double start_angle = Math.atan(y/x);
		if(x < 0) start_angle += Math.PI;
		angle += start_angle;
		double x = Math.cos(angle) * d;
		double y = Math.sin(angle) * d;
		return new Vector2D(x, y);
	}
	
	/**
	 * Skalira trenutni vektor za danu vrijednost.
	 * 
	 * @param scaler vrijednost skaliranja.
	 */
	public void scale(double scaler) {
		x = x * scaler;
		y = y * scaler;
	}
	
	/**
	 * Skalira trenutni vektor te rezultat vraća kao novi vektor.
	 * 
	 * @param scaler vrijednost skaliranja.
	 * @return rezulantni vektor.
	 */
	public Vector2D scaled(double scaler) {
		return new Vector2D(x * scaler, y * scaler);
	}
	
	/**
	 * Kopira trenutni vektor i vraća ga kao novi instancu vektora
	 * 
	 * @return nova instanca kopiranog vektora.
	 */
	public Vector2D copy() {
		return new Vector2D(x, y);
	}
	
	
	
	
}
