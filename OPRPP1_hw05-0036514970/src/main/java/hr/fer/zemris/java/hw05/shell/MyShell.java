package hr.fer.zemris.java.hw05.shell;

import java.io.Console;
import java.util.Collections;
import java.util.Scanner;
import java.util.SortedMap;
import java.util.TreeMap;

import hr.fer.zemris.java.hw05.shell.commands.CatShellCommand;
import hr.fer.zemris.java.hw05.shell.commands.CharsetsShellCommand;
import hr.fer.zemris.java.hw05.shell.commands.CopyShellCommand;
import hr.fer.zemris.java.hw05.shell.commands.ExitShellCommand;
import hr.fer.zemris.java.hw05.shell.commands.HelpShellCommand;
import hr.fer.zemris.java.hw05.shell.commands.HexdumpShellCommand;
import hr.fer.zemris.java.hw05.shell.commands.LsShellCommand;
import hr.fer.zemris.java.hw05.shell.commands.MkdirShellCommand;
import hr.fer.zemris.java.hw05.shell.commands.ShellCommand;
import hr.fer.zemris.java.hw05.shell.commands.ShellStatus;
import hr.fer.zemris.java.hw05.shell.commands.SymbolShellCommand;
import hr.fer.zemris.java.hw05.shell.commands.TreeShellCommand;

public class MyShell {
	static class ShellEnvironment implements Environment{
		SortedMap<String, ShellCommand> commands = new TreeMap<>();
		Scanner scanner = new Scanner(System.in);
		char  multilineSymbol;
		char promptSymbol;
		char morelinesSymbol;

		public ShellEnvironment() {
			commands.put("exit",new ExitShellCommand());
			commands.put("cat",new CatShellCommand());
			commands.put("charsets",new CharsetsShellCommand());
			commands.put("tree",new TreeShellCommand());
			commands.put("copy",new CopyShellCommand());
			commands.put("help",new HelpShellCommand());
			commands.put("hexdump",new HexdumpShellCommand());
			commands.put("ls",new LsShellCommand());
			commands.put("mkdir",new MkdirShellCommand());
			commands.put("symbol",new SymbolShellCommand());
			promptSymbol = '>';
			morelinesSymbol = '\\';
			multilineSymbol = '|';
		}

		@Override
		public String readLine() throws ShellIOException {
			write(getPromptSymbol() + " ");
			String allCommand = "";
			while(true) {
				String line = scanner.nextLine();
				if(!line.endsWith(getMorelinesSymbol().toString())) {
					allCommand += line;
					break;
				}
				write(getMultilineSymbol() + " ");
				allCommand += line.substring(0, line.length()-1);
			}
			return allCommand;

		}

		@Override
		public void write(String text) throws ShellIOException {
			System.out.print(text);

		}

		@Override
		public void writeln(String text) throws ShellIOException {
			System.out.println(text);

		}

		@Override
		public SortedMap<String, ShellCommand> commands() {
			return Collections.unmodifiableSortedMap(commands);
		}

		@Override
		public Character getMultilineSymbol() {
			return multilineSymbol;
		}

		@Override
		public void setMultilineSymbol(Character symbol) {
			multilineSymbol = symbol;

		}

		@Override
		public Character getPromptSymbol() {
			return promptSymbol;
		}

		@Override
		public void setPromptSymbol(Character symbol) {
			promptSymbol = symbol;

		}

		@Override
		public Character getMorelinesSymbol() {
			return morelinesSymbol;
		}

		@Override
		public void setMorelinesSymbol(Character symbol) {
			morelinesSymbol = symbol;

		}	
	}

	public static void main(String[] args) {
		ShellEnvironment environment = new ShellEnvironment();
		environment.writeln("Welcome to MyShell v 1.0");
		while(true) {
			String allCommand = environment.readLine();
			int firstSpace = allCommand.indexOf(' ');
			String commandName = null;
			String arguments = null;
			if (firstSpace == -1) {
				commandName = allCommand;
			}else {
				commandName = allCommand.substring(0, firstSpace);
				arguments = allCommand.substring(firstSpace + 1, allCommand.length());
			}
			ShellCommand command = environment.commands().get(commandName);

			if(command == null) {
				environment.writeln("Naredba " + commandName + " ne postoji");
				continue;
			}

			ShellStatus status = command.executeCommand(environment, arguments);
			if(status == ShellStatus.TERMINATE) break;
		}
	}
}
