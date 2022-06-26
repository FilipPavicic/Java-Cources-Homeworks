package hr.fer.zemris.java.hw05.shell.commands;

import java.util.List;

import hr.fer.zemris.java.hw05.shell.Environment;

public interface ShellCommand {
	ShellStatus executeCommand(Environment env, String arguments);
	String getCommandName();
	List<String> getCommandDescription();
}
