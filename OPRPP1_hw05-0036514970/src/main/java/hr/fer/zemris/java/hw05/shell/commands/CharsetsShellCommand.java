package hr.fer.zemris.java.hw05.shell.commands;

import java.nio.charset.Charset;
import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;
import java.util.Map;

import hr.fer.zemris.java.hw05.shell.Environment;

public class CharsetsShellCommand implements ShellCommand {

	@Override
	public ShellStatus executeCommand(Environment env, String arguments) {
		// Creates a map of charsets 
        Map<String, Charset> charsets  = Charset.availableCharsets(); 
        Iterator<Charset> iterator   = charsets.values().iterator(); 
        while (iterator.hasNext()) { 
            Charset all = (Charset)iterator.next(); 
            env.writeln(all.displayName()); 
        } 
		return ShellStatus.CONTINUE;
	}

	@Override
	public String getCommandName() {
		return "charsets";
	}

	@Override
	public List<String> getCommandDescription() {
		List<String> list = new ArrayList<String>();
		list.add("Naredba ispisuje sve moguće skupove znakova koje konzola podržava");
		return list;
	}

}
