package hr.fer.oprpp1.hw01;

import static org.junit.jupiter.api.Assertions.*;
import org.junit.jupiter.api.Test;

public class TesterComplexNumber {
	
	@Test
	public void testConstructor() {
		double real=4.5443;
		double complex=-1.34;
		ComplexNumber cn=new ComplexNumber(real, complex);
		assertEquals(real, cn.getReal());
		assertEquals(complex, cn.getImaginary());
	}
	
	@Test
	public void testMethodFromReal() {
		double real=4.5443;
		ComplexNumber cn=ComplexNumber.fromReal(real);
		assertEquals(real, cn.getReal());
		assertEquals(0, cn.getImaginary());
		
	}
	
	@Test
	public void testMethodFromImaginairy() {
		double complex=4.5443;
		ComplexNumber cn=ComplexNumber.fromImaginary(complex);
		assertEquals(0, cn.getReal());
		assertEquals(complex, cn.getImaginary());
		
	}
	
	@Test
	public void testMethodFromMagnitudeAndAngle() {
		ComplexNumber cn = new ComplexNumber(1, 0);
		assertEquals(cn, ComplexNumber.fromMagnitudeAndAngle(1, 0));
		ComplexNumber cn1 = new ComplexNumber(1, 1);
		assertEquals(cn1, ComplexNumber.fromMagnitudeAndAngle(Math.sqrt(2), Math.PI/4));
		ComplexNumber cn2 = new ComplexNumber(3, 4);
		assertEquals(cn2, ComplexNumber.fromMagnitudeAndAngle(5, 0.92729));
		ComplexNumber cn3 = new ComplexNumber(-3, 5.196152);
		assertEquals(cn3, ComplexNumber.fromMagnitudeAndAngle(6, 2 * Math.PI / 3));
		ComplexNumber cn4 = new ComplexNumber(-1, 0);
		assertEquals(cn4, ComplexNumber.fromMagnitudeAndAngle(1, Math.PI));
		ComplexNumber cn5 = new ComplexNumber(-1, -1);
		assertEquals(cn5, ComplexNumber.fromMagnitudeAndAngle(Math.sqrt(2), 5 * Math.PI/4));
		ComplexNumber cn6 = new ComplexNumber(9, -12);
		assertEquals(cn6, ComplexNumber.fromMagnitudeAndAngle(15, 5.35589));
	}
	
	@Test
	public void testMethodFromMagnitudeAndAngleAngleError() {
		assertThrows(IllegalArgumentException.class, () -> ComplexNumber.fromMagnitudeAndAngle(15, -0.01));
		assertThrows(IllegalArgumentException.class, () -> ComplexNumber.fromMagnitudeAndAngle(15, 2 * Math.PI));
	}
	
	@Test
	public void testMethodParse() {
		ComplexNumber cn = new ComplexNumber(1, 0);
		assertEquals(cn, ComplexNumber.parse("1"));
		ComplexNumber cn1 = new ComplexNumber(1, 1);
		assertEquals(cn1, ComplexNumber.parse("1+i"));
		ComplexNumber cn2 = new ComplexNumber(0, -1);
		assertEquals(cn2, ComplexNumber.parse("-i"));
		ComplexNumber cn3 = new ComplexNumber(-3, 1);
		assertEquals(cn3, ComplexNumber.parse("-3+i"));
		ComplexNumber cn4 = new ComplexNumber(-1, -1);
		assertEquals(cn4, ComplexNumber.parse("-1-i"));
		ComplexNumber cn5 = new ComplexNumber(3.14, -3.14);
		assertEquals(cn5, ComplexNumber.parse("+3.14-3.14i"));
	}
	
	@Test
	public void testMethodParseArgumentError() {
		assertThrows(IllegalArgumentException.class, () -> ComplexNumber.parse(" i"));
		assertThrows(IllegalArgumentException.class, () -> ComplexNumber.parse("1+ i"));
		assertThrows(IllegalArgumentException.class, () -> ComplexNumber.parse("1+2"));
		assertThrows(IllegalArgumentException.class, () -> ComplexNumber.parse("3i+i"));
		assertThrows(IllegalArgumentException.class, () -> ComplexNumber.parse("1+i3"));
		assertThrows(IllegalArgumentException.class, () -> ComplexNumber.parse("broj+i"));
	}
	
	@Test 
	public void testMethodGetMagnitude() {
		ComplexNumber cn = new ComplexNumber(3, 4);
		assertEquals(5, cn.getMagnitude());
		ComplexNumber cn1 = new ComplexNumber(3.4, 4.8);
		assertTrue(Math.abs(5.882176-cn1.getMagnitude())<0.0001);
	}
	
	@Test 
	public void testMethodGetAngle() {
		ComplexNumber cn = new ComplexNumber(3, 3);
		assertEquals(Math.PI / 4, cn.getAngle());
		ComplexNumber cn1 = new ComplexNumber(3.4, 4.8);
		assertTrue(Math.abs(0.954499-cn1.getAngle())<0.0001);
		ComplexNumber cn2 = new ComplexNumber(-3.4, 4.8);
		assertTrue(Math.abs(2.18709-cn2.getAngle())<0.0001);
		ComplexNumber cn3 = new ComplexNumber(-3.4, -4.8);
		assertTrue(Math.abs(4.09609-cn3.getAngle())<0.0001);
		ComplexNumber cn4 = new ComplexNumber(3.4, -4.8);
		assertTrue(Math.abs(5.32868-cn4.getAngle())<0.0001);
	}
	
	@Test
	public void testMethodAdd() {
		ComplexNumber cn = new ComplexNumber(0.2, -1.3);
		ComplexNumber cn1 = new ComplexNumber(9, -12);
		assertEquals(new ComplexNumber(9.2, -13.3), cn.add(cn1));
	}
	
	@Test
	public void testMethodSub() {
		ComplexNumber cn = new ComplexNumber(0.2, -1.3);
		ComplexNumber cn1 = new ComplexNumber(9, -12);
		assertEquals(new ComplexNumber(-8.8, 10.7), cn.sub(cn1));
	}
	
	@Test
	public void testMethodMul() {
		ComplexNumber cn = new ComplexNumber(0.2, -1.3);
		ComplexNumber cn1 = new ComplexNumber(9, -12);
		assertEquals(new ComplexNumber(-13.8,-14.1), cn.mul(cn1));
	}
	
	@Test
	public void testMethodDiv() {
		ComplexNumber cn = new ComplexNumber(0.2, -1.3);
		ComplexNumber cn1 = new ComplexNumber(9, -12);
		assertEquals(new ComplexNumber(0.07733, -0.041333), cn.div(cn1));
	}
	
	@Test
	public void testMethodPow() {
		ComplexNumber cn = new ComplexNumber(0.2, -1.3);
		assertEquals(new ComplexNumber(-1.006, 2.041), cn.power(3));
	}
	
	@Test
	public void testMethodRoot() {
		ComplexNumber cn = new ComplexNumber(0.2, -1.3);
		ComplexNumber[] roots=cn.root(5);
		for (int i = 0; i < roots.length-1; i++) {
			for (int j = i+1; j < roots.length; j++) {
				if(roots[i].getReal() > roots[j].getReal()) {
					var tmp = roots[i];
					roots[i] = roots[j];
					roots[j] = tmp;
				}
			}
		}
		ComplexNumber[] expected= {
				new ComplexNumber(-0.994208, -0.356942),
				new ComplexNumber(-0.646699, 0.835247),
				new ComplexNumber(0.032244, -1.055849),
				new ComplexNumber(0.594526, 0.873153),
				new ComplexNumber(1.01413, -0.295608)
		};
		assertArrayEquals(expected, roots);
	}
	
	
	
	
}
