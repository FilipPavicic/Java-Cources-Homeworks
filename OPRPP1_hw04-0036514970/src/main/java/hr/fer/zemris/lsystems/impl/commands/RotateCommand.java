package hr.fer.zemris.lsystems.impl.commands;

import hr.fer.zemris.lsystems.Painter;
import hr.fer.zemris.lsystems.impl.Command;
import hr.fer.zemris.lsystems.impl.Context;
import hr.fer.zemris.lsystems.impl.TurtleState;

public class RotateCommand implements Command{
	double angle;
	
	public RotateCommand(double angle) {
		super();
		this.angle = angle / 180.0 * Math.PI;
	}

	@Override
	public void execute(Context ctx, Painter painter) {
		TurtleState tmp =  ctx.getCurrentState();
		tmp.setCurrentAngle(tmp.getCurrentAngle().rotated(angle));
	}
}
