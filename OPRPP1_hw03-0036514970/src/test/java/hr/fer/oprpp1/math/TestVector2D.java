package hr.fer.oprpp1.math;

import static org.junit.jupiter.api.Assertions.*;
import org.junit.jupiter.api.Test;
import static java.lang.Math.sqrt;
import static java.lang.Math.hypot;

/**
 * Razred predstavlja model nepromjenjivog 2D-vektora.
 * 
 * @author marcupic
 */
public class TestVector2D {

	@Test
	public void ConstructorTest() {
		Vector2D v = new Vector2D(5,4);
		assertEquals(5, v.getX());
		assertEquals(4, v.getY());
	}
	
	@Test
	public void AddTest() {
		Vector2D v = new Vector2D(5,4);
		v.add(new Vector2D(3, -1));
		assertEquals(8, v.getX());
		assertEquals(3, v.getY());	
	}
	
	@Test
	public void AddedTest() {
		Vector2D v = new Vector2D(5,4);
		Vector2D v1 = v.added(new Vector2D(3, -1));
		assertEquals(8, v1.getX());
		assertEquals(3, v1.getY());	
	}
	
	@Test
	public void rotateTest() {
		Vector2D v = new Vector2D(3,3);
		v.rotate(Math.PI/2);
		assertEquals(v, new Vector2D(-3, 3));
		v.rotate(Math.PI/2);
		assertEquals(v, new Vector2D(-3, -3));
		v.rotate(Math.PI/2);
		assertEquals(v, new Vector2D(3, -3));
		v.rotate(Math.PI/2);
		assertEquals(v, new Vector2D(3, 3));
	}
	
	@Test
	public void rotateedTest() {
		Vector2D v = new Vector2D(3.5,-7.6);
		Vector2D v1 = v.rotated(2 * Math.PI);
		assertEquals(v, v1);
		v1 = v.rotated(Math.PI/2);
		assertEquals(new Vector2D(7.6, 3.5),v1);
	}
	
	@Test
	public void XIsZeroTest() {
		Vector2D v = new Vector2D(0,-7.6);
		Vector2D v1 = v.rotated(Math.PI/2);
		assertEquals(new Vector2D(7.6, 0),v1);
	}
	
	@Test
	public void scaleTest() {
		Vector2D v = new Vector2D(0,-7.6);
		v.scale(2);
		assertEquals(new Vector2D(0, -15.2),v);
	}
	
	@Test
	public void scaledTest() {
		Vector2D v = new Vector2D(0,-7.6);
		var v1 = v.scaled(0.5);
		assertEquals(new Vector2D(0, -3.8),v1);
	}
	
	
}
