package hr.fer.zemris.java.hw05.shell;

import java.nio.file.Files;
import java.nio.file.Path;

public class FileChecker {
	public static void exists(Path path) {
		if(!Files.exists(path)) throw new ShellIOException("Stavka: "+ path.toString() +" nije pronađena!");
	}
	public static void isDirectory(Path path) {
		if(!Files.isDirectory(path)) throw new ShellIOException("Putanja: "+ path.toString() +" ne pokazuje na direktorij.");

	}
	public static void isRegularFile(Path path) { 
		if(!Files.isRegularFile(path)) throw new ShellIOException("Putanja: "+ path.toString() +" ne pokazuje na datoteku.");

	}

}
