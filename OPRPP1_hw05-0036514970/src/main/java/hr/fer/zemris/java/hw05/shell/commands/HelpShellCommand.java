package hr.fer.zemris.java.hw05.shell.commands;

import java.util.ArrayList;
import java.util.List;

import hr.fer.zemris.java.hw05.shell.Environment;
import hr.fer.zemris.java.hw05.shell.ShellIOException;

public class HelpShellCommand implements ShellCommand {

	@Override
	public ShellStatus executeCommand(Environment env, String arguments) {
		try {
			String[] args = SplitingUtil.spaceSpliting(arguments);
			
			if(args.length > 1 ) {
				throw new ShellIOException("naredba cat može sadržavati isključivo jedan ili nitijedan argument");
			}
			
			if (args.length == 0) {
				env.commands().values().stream()
					.forEach((c) -> env.writeln(c.getCommandName()));
			} else {
				ShellCommand com =  env.commands().get(args[0]);
				if(com == null) throw new ShellIOException("Naredba " + args[0] + " ne postoji");
				
				env.writeln(com.getCommandName());
				for (String desc : com.getCommandDescription()) {
					env.writeln(desc);
				}
			}
		} catch (Exception e) {
			env.writeln(e.getMessage());
		}
		return ShellStatus.CONTINUE;
	}

	@Override
	public String getCommandName() {
		// TODO Auto-generated method stub
		return "help";
	}

	@Override
	public List<String> getCommandDescription() {
		List<String> list = new ArrayList<String>();
		list.add("Naredba ispisuje sadržaj direktorija");
		return list;
	}

}
