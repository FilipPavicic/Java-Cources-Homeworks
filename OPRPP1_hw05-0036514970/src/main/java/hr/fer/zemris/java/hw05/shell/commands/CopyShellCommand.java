package hr.fer.zemris.java.hw05.shell.commands;

import java.io.BufferedInputStream;
import java.io.BufferedOutputStream;
import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.ArrayList;
import java.util.List;

import hr.fer.zemris.java.hw05.shell.Environment;
import hr.fer.zemris.java.hw05.shell.FileChecker;
import hr.fer.zemris.java.hw05.shell.ShellIOException;

public class CopyShellCommand implements ShellCommand {

	@Override
	public ShellStatus executeCommand(Environment env, String arguments) {
		try{
			String[] args = SplitingUtil.quotationSpliting(arguments);

			if(args.length != 2 ) {
				throw new ShellIOException("naredba cat mora sadržavati isključivo dva argumenta");
			}

			Path path1 = Paths.get(args[0]);

			FileChecker.exists(path1);
			FileChecker.isRegularFile(path1);

			Path path2 = Paths.get(args[1]);

			if(Files.isDirectory(path2)) path2 = Paths.get(path2.toString(),path1.getFileName().toString());
			String ans = "y";
			if(Files.exists(path2)) {
				env.writeln("Datoteka: " + path2 + " već postoji, želite li ju zamjeniti y/n");
				ans = env.readLine();
				while(!(ans.equals("y") || ans.equals("n"))) {
					env.writeln("Odgovor: "+ ans +" nije prepoznat. odgovorite s y/n");
					ans = env.readLine();
				}	
			}
			if(ans == "y") {
				copyFile(path1,path2);
			}
		} catch (Exception e) {
			env.writeln(e.getMessage());
		}


		return ShellStatus.CONTINUE;
	}

	private void copyFile(Path path1, Path path2) throws IOException {
		Files.createFile(path2);
		try(BufferedInputStream bis = new BufferedInputStream(Files.newInputStream(path1))){

			try(BufferedOutputStream bos = new BufferedOutputStream(Files.newOutputStream(path2))){

				while(true) {
					byte[] buffer = new byte[4096];
					int readedBytes = bis.read(buffer, 0, 4096);

					if(readedBytes == -1) {
						break;
					}

					bos.write(buffer,0,readedBytes);

				}

			}
		}

	}

	@Override
	public String getCommandName() {
		// TODO Auto-generated method stub
		return "copy";
	}

	@Override
	public List<String> getCommandDescription() {
		List<String> list = new ArrayList<String>();
		list.add("Naredba kopira datoteku zadanu prvim argumentom u datoteku zadanu drugim argumentom");
		return list;
	}

}
