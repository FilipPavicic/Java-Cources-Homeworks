package hr.fer.zemris.java.hw05.shell.commands;

import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.ArrayList;
import java.util.List;

import hr.fer.zemris.java.hw05.shell.Environment;
import hr.fer.zemris.java.hw05.shell.FileChecker;
import hr.fer.zemris.java.hw05.shell.ShellIOException;

public class HexdumpShellCommand implements ShellCommand {

	@Override
	public ShellStatus executeCommand(Environment env, String arguments) {
		try{
			String[] args = SplitingUtil.quotationSpliting(arguments);


			if(args.length != 1 ) throw new ShellIOException("naredba cat mora sadržavati isključivo jedan argument");

			Path path = Paths.get(args[0]);

			FileChecker.exists(path);
			FileChecker.isRegularFile(path);
			byte[] bytes = Files.readAllBytes(path);
			List<String> rows = new ArrayList<String>();
			String row = "";
			String text =" ";
			int i;
			for(i = 1; i<bytes.length+1; i++) {
				if(i == 1)  row += String.format("%07d0: ", 0);
				row += String.format("%02X", bytes[i-1]);
				int charvalue = Byte.toUnsignedInt(bytes[i-1]);
				text += (charvalue < 32 || charvalue > 127) ? "." : (char)charvalue;
				row += i % 16 == 0 ? " " : "";
				row += i % 8 == 0 ? "|" : " ";
				if(i % 16 == 0) {
					row += text;
					rows.add(row);
					row = String.format("%07d0: ", i/16);
					text = " ";
				}
			}
			if(i % 16 != 1) {
				for(i = i % 16; i < 17; i++) {
					row += "  ";
					row += i % 16 == 0 ? " " : "";
					row += i % 8 == 0 ? "|" : " ";
				}
				row += text;
				rows.add(row);
			}
			for (String red : rows) {
				env.writeln(red);
			}
		}catch (Exception e) {
			env.writeln(e.getMessage());
		}
		return ShellStatus.CONTINUE;
	}

	@Override
	public String getCommandName() {
		return "hexdump";
	}

	@Override
	public List<String> getCommandDescription() {
		List<String> list = new ArrayList<String>();
		list.add("Naredba ispisuje sadržaj direktorija po bajtovima zapisnih u haksadeksatskom formatu");
		return list;
	}

}
