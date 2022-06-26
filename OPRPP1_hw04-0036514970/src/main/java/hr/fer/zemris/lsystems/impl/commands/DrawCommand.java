package hr.fer.zemris.lsystems.impl.commands;

import hr.fer.oprpp1.math.Vector2D;
import hr.fer.zemris.lsystems.Painter;
import hr.fer.zemris.lsystems.impl.Command;
import hr.fer.zemris.lsystems.impl.Context;
import hr.fer.zemris.lsystems.impl.TurtleState;

public class DrawCommand implements Command{
	double step;
	
	public DrawCommand(double step) {
		super();
		this.step = step;
	}

	@Override
	public void execute(Context ctx, Painter painter) {
		TurtleState tmp = ctx.getCurrentState();
		double length = this.step * tmp.getEfectiveLength();
		Vector2D newPositon = tmp.getCurrentPozition().added(tmp.getCurrentAngle().scaled(length));
		painter.drawLine(tmp.getCurrentPozition().getX(), tmp.getCurrentPozition().getY(), 
				newPositon.getX(), newPositon.getY(), tmp.getCurrentColor(), 1f);
		tmp.setCurrentPozition(newPositon);
	}

}
