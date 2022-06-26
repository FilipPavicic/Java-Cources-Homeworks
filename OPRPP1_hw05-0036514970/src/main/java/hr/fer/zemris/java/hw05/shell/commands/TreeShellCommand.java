package hr.fer.zemris.java.hw05.shell.commands;

import java.io.IOException;
import java.nio.file.FileVisitResult;
import java.nio.file.FileVisitor;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.nio.file.attribute.BasicFileAttributes;
import java.util.ArrayList;
import java.util.List;

import hr.fer.zemris.java.hw05.shell.Environment;
import hr.fer.zemris.java.hw05.shell.FileChecker;
import hr.fer.zemris.java.hw05.shell.ShellIOException;

public class TreeShellCommand implements ShellCommand {

	@Override
	public ShellStatus executeCommand(Environment env, String arguments) {
		try {
			String[] args = SplitingUtil.quotationSpliting(arguments);

			if(args.length != 1 ) {
				throw new ShellIOException("naredba cat mora sadržavati isključivo jedan argument");
			}

			Path path = Paths.get(args[0]);

			FileChecker.exists(path);
			FileChecker.isDirectory(path);

			Files.walkFileTree(path, new FileVisitor<Path>() {
				int razina=0;
				private void ispisi(Path file) {
					System.out.printf("%s%s%n", " ".repeat(razina * 2),file.getFileName());

				}
				@Override
				public FileVisitResult preVisitDirectory(Path dir,
						BasicFileAttributes attrs) throws IOException {
					ispisi(dir);
					razina++;
					return FileVisitResult.CONTINUE;
				}

				@Override
				public FileVisitResult visitFile(Path file,
						BasicFileAttributes attrs) throws IOException {
					ispisi(file);
					return FileVisitResult.CONTINUE;
				}

				@Override
				public FileVisitResult visitFileFailed(Path file, IOException exc) throws IOException {
					throw new IOException("Datotek: " + path + " nije moguće posjetiti");
				}

				@Override
				public FileVisitResult postVisitDirectory(Path dir, IOException exc)
						throws IOException {
					razina--;
					return FileVisitResult.CONTINUE;
				}
			});
		} catch (Exception e) {
			env.writeln(e.getMessage());
		}

		return ShellStatus.CONTINUE;
	}

	@Override
	public String getCommandName() {
		return "tree";
	}

	@Override
	public List<String> getCommandDescription() {
		List<String> list = new ArrayList<String>();
		list.add("Naredba prolazi kroz zadani direktorij i ispisuje sve stavke u direktoriju i svim poddirektorijima");
		return list;
	}

}
