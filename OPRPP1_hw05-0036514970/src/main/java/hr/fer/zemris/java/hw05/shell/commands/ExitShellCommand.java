package hr.fer.zemris.java.hw05.shell.commands;

import java.util.ArrayList;
import java.util.List;

import hr.fer.zemris.java.hw05.shell.Environment;

public class ExitShellCommand implements ShellCommand {

	@Override
	public ShellStatus executeCommand(Environment env, String arguments) {
		return ShellStatus.TERMINATE;
	}

	@Override
	public String getCommandName() {
		return "exit";
	}

	@Override
	public List<String> getCommandDescription() {
		List<String> list = new ArrayList<String>();
		list.add("Naredba za izlaz iz konzole");
		return list;
	}

}
