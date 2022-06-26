package hr.fer.zemris.lsystems.impl.commands;

import hr.fer.zemris.lsystems.Painter;
import hr.fer.zemris.lsystems.impl.Command;
import hr.fer.zemris.lsystems.impl.Context;
import hr.fer.zemris.lsystems.impl.TurtleState;

public class ScaleCommand implements Command {
	double factor;

	public ScaleCommand(double factor) {
		super();
		this.factor = factor;
	}

	@Override
	public void execute(Context ctx, Painter painter) {
		TurtleState tmp = ctx.getCurrentState();
		tmp.setEfectiveLength(factor);
		
	}
	
	
}
