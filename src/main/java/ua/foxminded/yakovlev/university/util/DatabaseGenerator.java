package ua.foxminded.yakovlev.university.util;

import java.nio.file.Path;
import java.nio.file.Paths;

import ua.foxminded.yakovlev.university.dao.impl.ScriptExecutor;

public class DatabaseGenerator {
	
	private final FileReader fileReader;
	private final ScriptExecutor scriptExecutor;
	private final String scriptFile;
	
	public DatabaseGenerator(FileReader fileReader, ScriptExecutor scriptExecutor, String scriptPath) {
		this.fileReader = fileReader;
		this.scriptExecutor = scriptExecutor;
		this.scriptFile =  scriptPath;
		}

	public void generate() {
		
		Path scriptPath = Paths.get(scriptFile);
		String script = String.join("\n", fileReader.readFile(scriptPath));
		scriptExecutor.execute(script);
	}
}
