package ua.foxminded.yakovlev.university.testutil;

import java.nio.file.Path;
import java.nio.file.Paths;

import ua.foxminded.yakovlev.university.dao.impl.ScriptExecutor;
import ua.foxminded.yakovlev.university.util.FileReader;

public class TestDatabaseGenerator {
	
	private static final String TEST_DATABASE_SCRIPT_PATH = "src/test/resources/university_test_db_initialiser.sql";
	
	private final FileReader fileReader;
	private final ScriptExecutor scriptExecutor;
	
	public TestDatabaseGenerator(FileReader fileReader, ScriptExecutor scriptExecutor) {
		this.fileReader = fileReader;
		this.scriptExecutor = scriptExecutor;
	}

	public void generate() {
		
		Path scriptPath = Paths.get(TEST_DATABASE_SCRIPT_PATH);
		String script = String.join("\n", fileReader.readFile(scriptPath));
		scriptExecutor.execute(script);
	}
}
