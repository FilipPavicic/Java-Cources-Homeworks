package hr.fer.zemris.java.hw05.shell.commands;

import java.io.IOException;
import java.nio.file.FileAlreadyExistsException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.ArrayList;
import java.util.List;

import hr.fer.zemris.java.hw05.shell.Environment;
import hr.fer.zemris.java.hw05.shell.FileChecker;
import hr.fer.zemris.java.hw05.shell.ShellIOException;

public class MkdirShellCommand implements ShellCommand {

	@Override
	public ShellStatus executeCommand(Environment env, String arguments) {
		String[] args = SplitingUtil.quotationSpliting(arguments);

		if(args.length != 1 ) throw new ShellIOException("naredba mkdir mora sadržavati isključivo jedan argument");

		Path path = Paths.get(args[0]);
		
		
		try {
			Files.createDirectories(path);
			
		}
		catch (FileAlreadyExistsException e) {
			env.writeln("Stavka: " + path + " već postoji, a nije direktorij");
		} catch (IOException e) {
			env.writeln(e.getMessage());
		}
		return ShellStatus.CONTINUE;

		
	}

	@Override
	public String getCommandName() {
		return "mkdir";
	}

	@Override
	public List<String> getCommandDescription() {
		List<String> list = new ArrayList<String>();
		list.add("Naredba stvara strukturu direktorija koji je zadan argumentom");
		return list;
	}

}
