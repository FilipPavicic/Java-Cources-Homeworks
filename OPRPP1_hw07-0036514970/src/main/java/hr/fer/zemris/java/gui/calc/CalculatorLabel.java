package hr.fer.zemris.java.gui.calc;

import java.awt.Color;

import javax.swing.JLabel;
import javax.swing.SwingConstants;

import hr.fer.zemris.java.gui.calc.model.CalcModel;

public class CalculatorLabel extends JLabel {
	
	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;

	public CalculatorLabel(CalcModel calcModel) {
		super();
		calcModel.addCalcValueListener((m) -> setText(m.toString()));
		this.setFont(this.getFont().deriveFont(30f));
		this.setBackground(Color.YELLOW);
		this.setOpaque(true);
		this.setHorizontalAlignment(SwingConstants.RIGHT);
	}
}
