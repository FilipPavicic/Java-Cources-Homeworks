package hr.fer.zemris.java.gui.layouts;

import static org.junit.jupiter.api.Assertions.*;

import java.awt.Dimension;

import javax.swing.JLabel;
import javax.swing.JPanel;

import org.junit.jupiter.api.Test;

public class CalcLayoutTest {
	@Test
	public void testPreferredSize() {
		JPanel p = new JPanel(new CalcLayout(2));
		JLabel l1 = new JLabel(""); l1.setPreferredSize(new Dimension(10,30));
		JLabel l2 = new JLabel(""); l2.setPreferredSize(new Dimension(20,15));
		p.add(l1, new RCPosition(2,2));
		p.add(l2, new RCPosition(3,3));
		Dimension dim = p.getPreferredSize();
		assertEquals(new Dimension(152,  158), dim);
	}
	@Test
	public void testPreferredSize2() {
		JPanel p = new JPanel(new CalcLayout(2));
		JLabel l1 = new JLabel(""); l1.setPreferredSize(new Dimension(108,15));
		JLabel l2 = new JLabel(""); l2.setPreferredSize(new Dimension(16,30));
		p.add(l1, new RCPosition(1,1));
		p.add(l2, new RCPosition(3,3));
		Dimension dim = p.getPreferredSize();
		assertEquals(new Dimension(152,  158), dim);
	}
	
	@Test
	public void testGenerate1DArray() {
		assertArrayEquals(new int[] {3, 3, 3, 3, 3, 3, 3}, CalcLayout.generate1DArray(21, 7));
		assertArrayEquals(new int[] {3, 3, 3, 4, 3, 3, 3}, CalcLayout.generate1DArray(22, 7));
		assertArrayEquals(new int[] {3, 3, 4, 3, 4, 3, 3}, CalcLayout.generate1DArray(23, 7));
		assertArrayEquals(new int[] {3, 4, 3, 4, 3, 4, 3}, CalcLayout.generate1DArray(24, 7));
		assertArrayEquals(new int[] {4, 3, 4, 3, 4, 3, 4}, CalcLayout.generate1DArray(25, 7));
		assertArrayEquals(new int[] {4, 4, 3, 4, 3, 4, 4}, CalcLayout.generate1DArray(26, 7));
		assertArrayEquals(new int[] {4, 4, 4, 3, 4, 4, 4}, CalcLayout.generate1DArray(27, 7));
		assertArrayEquals(new int[] {4, 5, 4, 5, 4}, CalcLayout.generate1DArray(22, 5));
	}
}
