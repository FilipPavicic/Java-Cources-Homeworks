package hr.fer.zemris.java.hw05.shell.commands;

import java.util.ArrayList;
import java.util.List;
import java.util.function.BiConsumer;
import java.util.function.Consumer;
import java.util.function.Function;

import hr.fer.zemris.java.hw05.shell.Environment;

public class SymbolShellCommand implements ShellCommand {

	@Override
	public ShellStatus executeCommand(Environment env, String arguments) {
		String args[] = SplitingUtil.spaceSpliting(arguments);
		if(args.length < 1 || args.length > 2) {
			env.writeln("Naredba symbol može sadržavati isključivo jedan ili dva argumenta");
			return ShellStatus.CONTINUE;
		}		
		switch (args[0]) {
			case "PROMPT" -> executeCommandForAgument(env, args, env.getPromptSymbol(),(e, c) -> e.setPromptSymbol(c));
			case "MORELINES" -> executeCommandForAgument(env, args, env.getMorelinesSymbol(),(e, c) -> e.setMorelinesSymbol(c));
			case "MULTILINE" -> executeCommandForAgument(env, args, env.getMultilineSymbol(),(e, c) -> e.setMultilineSymbol(c));
			default -> env.writeln("Nepoznati simbol: " + args[0]);
		}
		return ShellStatus.CONTINUE;
	}
	
	private void executeCommandForAgument(Environment env, String[] args, Character oldSymbol, BiConsumer<Environment,Character> setter) {
		if(args.length == 1) {
			env.writeln("Symbol for " + args[0] + " is '"+ oldSymbol + "'");
			return;
		}
		
		if(args[1].length() != 1) {
			env.writeln("Veličina  novog simbola mora biti 1");
			return;
		}
		char newSymbol = args[1].charAt(0);
		env.writeln("Symbol for " + args[0] + " changed from '" + oldSymbol + "' to '" + newSymbol + "'");
		setter.accept(env, newSymbol);
		return;
	}

	@Override
	public String getCommandName() {
		return "symbol";
	}

	@Override
	public List<String> getCommandDescription() {
		List<String> list = new ArrayList<String>();
		list.add("Naredba mijenja specijalne simbole");
		list.add("symbol PROMPT mijenja kursor");
		list.add("symbol MORELINES mijenja oznaku prelaska naredbe u novi red");
		list.add("symbol MULTILINE mijenja oznaku na početku novog reda");
		return list;
	}

}
