package ua.foxminded.yakovlev.university.util;

import java.io.File;
import java.io.IOException;
import java.util.List;

import com.fasterxml.jackson.core.type.TypeReference;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.dataformat.yaml.YAMLFactory;

import ua.foxminded.yakovlev.university.entity.Position;

public class PositionGenerator {
	
	private static final String YAML_FILE_PATH = "src/test/resources/entity/positions.yaml";

	public Position getPosition(Long id, String name) {
		
		Position position = new Position();
		position.setId(id);
		position.setName(name);
		
		return position;
	}
	
	public List<Position> getPositionList() {
		
		ObjectMapper mapper = new ObjectMapper(new YAMLFactory());
		List<Position> positionList;
		try {
			positionList = mapper.readValue(new File(YAML_FILE_PATH), new TypeReference<List<Position>>(){});
		} catch (IOException e) {
			throw new IllegalArgumentException("Bad yaml file " + YAML_FILE_PATH);
		}
		
		return positionList;
	}
}