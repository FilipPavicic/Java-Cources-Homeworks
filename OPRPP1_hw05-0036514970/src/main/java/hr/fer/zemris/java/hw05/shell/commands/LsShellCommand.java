package hr.fer.zemris.java.hw05.shell.commands;

import java.io.IOException;
import java.nio.file.DirectoryStream;
import java.nio.file.Files;
import java.nio.file.LinkOption;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.nio.file.attribute.BasicFileAttributeView;
import java.nio.file.attribute.BasicFileAttributes;
import java.nio.file.attribute.FileTime;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import java.util.function.Predicate;

import hr.fer.zemris.java.hw05.shell.Environment;
import hr.fer.zemris.java.hw05.shell.FileChecker;
import hr.fer.zemris.java.hw05.shell.ShellIOException;

public class LsShellCommand implements ShellCommand {
	SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");

	@Override
	public ShellStatus executeCommand(Environment env, String arguments) {
		try {
			String[] args = SplitingUtil.quotationSpliting(arguments);

			if(args.length != 1 ) throw new ShellIOException("naredba ls mora sadržavati isključivo jedan argument");
			
			Path path = Paths.get(args[0]);

			FileChecker.exists(path);
			FileChecker.isDirectory(path);

			try (DirectoryStream<Path> stream = Files.newDirectoryStream(path)) {
				for (Path filePath : stream) {
					createFileRecord(filePath,env);
				}
			} 

		}catch (Exception e) {
			env.writeln(e.getMessage());
		}
		return ShellStatus.CONTINUE;
	}

	private void createFileRecord(Path filePath, Environment env) throws IOException {
		BasicFileAttributeView faView = Files.
				getFileAttributeView(filePath, 
						BasicFileAttributeView.class, LinkOption.NOFOLLOW_LINKS
						);
		BasicFileAttributes attributes = faView.readAttributes();
		FileTime fileTime = attributes.creationTime();
		String formattedDateTime = sdf.format(new Date(fileTime.toMillis()));

		String record = "";
		record += checkflag("d", filePath, (p) -> Files.isDirectory(p));
		record += checkflag("r", filePath, (p) -> Files.isReadable(p));
		record += checkflag("w", filePath, (p) -> Files.isWritable(p));
		record += checkflag("x", filePath, (p) -> Files.isExecutable(p));
		record += String.format("%10d ", Files.size(filePath));
		record += formattedDateTime + " ";
		record += filePath.getFileName();
		env.writeln(record);
	}

	private String checkflag(String flag, Path filePath,
			Predicate<Path> p) {
		if(p.test(filePath)) return flag;
		return "-";

	}

	@Override
	public String getCommandName() {
		return "ls";
	}

	@Override
	public List<String> getCommandDescription() {
		List<String> list = new ArrayList<String>();
		list.add("Naredba ispisuje sadržaj direktorija");
		return list;
	}

}
