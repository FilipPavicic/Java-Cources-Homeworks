package hr.fer.zemris.java.hw05.shell.commands;

import java.io.IOException;
import java.nio.charset.Charset;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.ArrayList;
import java.util.List;

import hr.fer.zemris.java.hw05.shell.Environment;
import hr.fer.zemris.java.hw05.shell.FileChecker;
import hr.fer.zemris.java.hw05.shell.ShellIOException;

public class CatShellCommand implements ShellCommand {

	@Override
	public ShellStatus executeCommand(Environment env, String arguments) {
		try {
			String[] args = SplitingUtil.quotationSpliting(arguments);
			
			if(args.length < 1 || args.length > 2 ) {
				throw new ShellIOException("naredba cat može sadržavati isključivo jedan ili dva argumenata");
			}
			
			Path path = Paths.get(args[0]);
			
			FileChecker.exists(path);
			FileChecker.isRegularFile(path);
			
			Charset charset = Charset.defaultCharset();
			if(args.length == 2) {
				if(!Charset.isSupported(args[1])) throw new ShellIOException("Skup znakova: "+ args[1] +" nije podržan.");
				
				charset = Charset.forName(args[1]);
			}
			List<String> allLines = Files.readAllLines(path, charset);
			for (String line : allLines) {
				env.writeln(line);
			}
			
		} catch (Exception e) {
			env.writeln(e.getMessage());
		}
		return ShellStatus.CONTINUE;
	}

	@Override
	public String getCommandName() {
		return "cat";
	}

	@Override
	public List<String> getCommandDescription() {
		List<String> list = new ArrayList<String>();
		list.add("Naredba otvara datoteku zadanim skupom znakova");
		list.add("Ako skup znakova nije zadan koristi se UTF-8 skup znakova");
		return list;
	}

}
