package ua.foxminded.yakovlev.university.util;

import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.util.List;

import org.springframework.stereotype.Component;

import ua.foxminded.yakovlev.university.exception.DaoRuntimeException;

@Component
public class FileReader {

	public List<String> readFile(Path path) {
		
		checkFile(path);
		
		try {
			return Files.readAllLines(path);
		} catch (IOException e) {			
			throw new DaoRuntimeException("can't read " + path.toString(), e);
		}		
	}
	
	private void checkFile(Path path) {
		
		if (path == null) {
			throw new DaoRuntimeException("file is null");
		}
		
		if (!Files.exists(path)) {
			throw new DaoRuntimeException(path.toString() + " does not exist");
		}
		
		if (!Files.isReadable(path)) {
			throw new DaoRuntimeException(path.toString() + " is not readable");
		}
	}
}
