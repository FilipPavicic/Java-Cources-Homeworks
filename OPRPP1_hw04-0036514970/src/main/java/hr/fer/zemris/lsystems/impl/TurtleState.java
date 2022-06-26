package hr.fer.zemris.lsystems.impl;

import java.awt.Color;
import hr.fer.oprpp1.math.Vector2D;

/**
 * Razred predstavlja implementaciju stanja kornjače.
 * 
 * @author Filip Pavicic
 *
 */
public class TurtleState {
	Vector2D currentPozition;
	Vector2D currentAngle;
	Color currentColor;
	double efectiveLength;
	
	public TurtleState(Vector2D currentPozition, Vector2D currentAngle,
			Color currentColor, double efectiveLength) {
		super();
		this.currentPozition = currentPozition;
		this.currentAngle = currentAngle;
		this.currentColor = currentColor;
		this.efectiveLength = efectiveLength;
	}


	public Vector2D getCurrentPozition() {
		return currentPozition;
	}


	public void setCurrentPozition(Vector2D currentPozition) {
		this.currentPozition = currentPozition;
	}


	public Color getCurrentColor() {
		return currentColor;
	}


	public void setCurrentColor(Color currentColor) {
		this.currentColor = currentColor;
	}


	public double getEfectiveLength() {
		return efectiveLength;
	}


	public void setEfectiveLength(double efectiveLength) {
		this.efectiveLength = efectiveLength;
	}
	
	public Vector2D getCurrentAngle() {
		return currentAngle;
	}


	public void setCurrentAngle(Vector2D currentAngle) {
		this.currentAngle = currentAngle;
	}


	/**
	 * Stvara kopiju trenutnog vektora.
	 * 
	 * @return nova instanca TurtleState
	 */
	public TurtleState copy() {
		return new TurtleState(this.currentPozition, this.currentAngle, this.currentColor, this.efectiveLength);
	}
}

