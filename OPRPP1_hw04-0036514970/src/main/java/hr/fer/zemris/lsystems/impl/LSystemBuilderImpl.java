package hr.fer.zemris.lsystems.impl;

import hr.fer.zemris.lsystems.LSystem;

import java.awt.Color;
import java.util.Locale;
import java.util.Scanner;
import java.util.stream.Collectors;

import hr.fer.oprpp1.custom.collections.*;
import hr.fer.oprpp1.math.Vector2D;
import hr.fer.zemris.lsystems.LSystemBuilder;
import hr.fer.zemris.lsystems.Painter;
import hr.fer.zemris.lsystems.impl.commands.*;

public class LSystemBuilderImpl implements LSystemBuilder {
	private Dictionary<Character, String> productions = new Dictionary<>();
	private Dictionary<Character, Command> commands =  new Dictionary<>();
	private double unitLength = 0.1;
	private double unitLengthDegreeScaler = 1;
	private Vector2D origin = new Vector2D(0, 0);
	private double angle = 0;
	private String axiom = "";

	private  class LSystemImpl implements LSystem {
		Context context;

		@Override
		public void draw(int arg0, Painter arg1) {
			context = new Context();
			TurtleState firstState = new TurtleState(origin, new Vector2D(1,0).rotated(angle), 
					Color.black, unitLength* (Math.pow(unitLengthDegreeScaler, arg0 * 1.0)));
			context.pushState(firstState);
			String generatedString  = generate(arg0);
			generatedString.chars()
			.mapToObj(c -> (char) c)
			.forEach(c -> {
				Command command  = commands.get(c);
				if(command != null) command.execute(context, arg1);
			});
		}



		@Override
		public String generate(int arg0) {
			if(arg0 == 0) return axiom;

			return generate(arg0-1).chars()
					.mapToObj(c -> (char) c)
					.map( c -> {
						String producrtion =productions.get(c);
						if(producrtion != null) return producrtion;
						else return String.valueOf(c);
					})
					.collect(Collectors.joining());
		}

	}

	@Override
	public LSystem build() {
		System.out.println(this.toString());
		return new LSystemImpl();
	}

	@Override
	public LSystemBuilder configureFromText(String[] arg0) {
		for (String strComand : arg0) {
			try (Scanner scanner = new Scanner(strComand)) {
				scanner.useLocale(Locale.US);
				if(!scanner.hasNext()) continue;
				String commandText = scanner.next();
				switch (commandText) {
					case "origin" -> {
						double firstArg = scanner.nextDouble();
						double secondArg = scanner.nextDouble();
						setOrigin(firstArg, secondArg);
					}
					case "angle" -> setAngle(scanner.nextDouble());
					case "unitLength" -> setUnitLength(scanner.nextDouble());
					case "unitLengthDegreeScaler" -> {
						scanner.useDelimiter("\\s*/\\s*| +");
						double firstArg = scanner.nextDouble();
						if(scanner.hasNextDouble()) {
							double secondArg = scanner.nextDouble();
							firstArg = firstArg / secondArg;
						}
						setUnitLengthDegreeScaler(firstArg);
					}
					case "command" ->  {
						String firstArg = scanner.next();
						if(firstArg.length() != 1) throw new IllegalArgumentException("Simbol naredbe mora sadržavati jedan znak");
						char firstCharArg = firstArg.charAt(0);
						String secondArg = scanner.tokens().collect(Collectors.joining(" "));
						registerCommand(firstCharArg, secondArg);
					}
					case "axiom" -> setAxiom(scanner.next());
					case "production" -> {
						String firstArg = scanner.next();
						if(firstArg.length() != 1) throw new IllegalArgumentException("Simbol produkcije mora sadržavati jedan znak");
						char firstCharArg = firstArg.charAt(0);
						String secondArg = scanner.tokens().collect(Collectors.joining(" "));
						registerProduction(firstCharArg, secondArg);
					}
					default -> throw new IllegalArgumentException("Unexpected value: " + scanner.next());
				}
				if (scanner.hasNext()) {
					throw new IllegalArgumentException("Previše argumenata u konfiguracijskoj liniji");
				}	
			}

		}
		return this;
	}

	@Override
	public LSystemBuilder registerCommand(char arg0, String arg1) {
		commands.put(arg0, createCommandFromString(arg1));
		return this;
	}

	private Command createCommandFromString(String arg1) {
		Command command;
		try (Scanner scanner = new Scanner(arg1)) {
			scanner.useLocale(Locale.US);
			String commandText = scanner.next();
			switch (commandText) {
				case "draw" -> command = new DrawCommand(scanner.nextDouble()); 
				case "skip" -> command =  new SkipCommand(scanner.nextDouble());
				case "scale" -> command = new ScaleCommand(scanner.nextDouble());
				case "rotate" -> command =  new RotateCommand(scanner.nextDouble());
				case "push" -> command = new PushCommand();
				case "pop" -> command = new PopCommand();
				case "color" -> command = new ColorCommand(Color.decode("#" + scanner.next()));
				default ->
				throw new IllegalArgumentException("Unexpected value: " + commandText);
			}

			if (scanner.hasNext()) throw new IllegalArgumentException("Previše argumenata u naredbi");
		}
		return command;
	}

	@Override
	public LSystemBuilder registerProduction(char arg0, String arg1) {
		productions.put(arg0, arg1);
		return this;
	}

	@Override
	public LSystemBuilder setAngle(double arg0) {
		angle = arg0 / 180.0 * Math.PI;
		return this;
	}

	@Override
	public LSystemBuilder setAxiom(String arg0) {
		this.axiom = arg0;
		return this;
	}

	@Override
	public LSystemBuilder setOrigin(double arg0, double arg1) {
		origin = new Vector2D(arg0, arg1);
		return this;
	}

	@Override
	public LSystemBuilder setUnitLength(double arg0) {
		this.unitLength = arg0;
		return this;
	}

	@Override
	public LSystemBuilder setUnitLengthDegreeScaler(double arg0) {
		unitLengthDegreeScaler = arg0;
		return this;
	}

	@Override
	public String toString() {
		return "LSystemBuilderImpl [productions=" + productions + ", commands="
				+ commands + ", unitLength=" + unitLength
				+ ", unitLengthDegreeScaler=" + unitLengthDegreeScaler
				+ ", origin=" + origin + ", angle=" + angle + ", axiom=" + axiom
				+ "]";
	}
	
	

}
