package hr.fer.zemris.lsystems.impl.commands;

import hr.fer.oprpp1.math.Vector2D;
import hr.fer.zemris.lsystems.Painter;
import hr.fer.zemris.lsystems.impl.Command;
import hr.fer.zemris.lsystems.impl.Context;
import hr.fer.zemris.lsystems.impl.TurtleState;

public class SkipCommand implements Command{
	double step;
	
	public SkipCommand(double step) {
		super();
		this.step = step;
	}

	@Override
	public void execute(Context ctx, Painter painter) {
		TurtleState tmp = ctx.getCurrentState();
		double length = this.step * tmp.getEfectiveLength();
		Vector2D newPositon = tmp.getCurrentPozition().added(tmp.getCurrentAngle().scaled(length));
		tmp.setCurrentPozition(newPositon);
	}

}
