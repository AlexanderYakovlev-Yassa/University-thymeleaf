package ua.foxminded.yakovlev.university.util;

import java.io.File;
import java.io.IOException;
import java.util.List;

import com.fasterxml.jackson.core.type.TypeReference;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.dataformat.yaml.YAMLFactory;

import ua.foxminded.yakovlev.university.entity.Position;
import ua.foxminded.yakovlev.university.entity.Lecturer;

public class LecturerGenerator {
	
	private static final String YAML_FILE_PATH = "src/test/resources/entity/lecturers.yaml";

	public Lecturer getLecturer(Long personId, String firstName, String lastName, Position position) {
		
		Lecturer lecturer = new Lecturer();
		lecturer.setPersonId(personId);
		lecturer.setFirstName(firstName);
		lecturer.setLastName(lastName);
		lecturer.setPosition(position);
		
		return lecturer;
	}
	
	public List<Lecturer> getLecturerList() {

		ObjectMapper mapper = new ObjectMapper(new YAMLFactory());
		
		List<Lecturer> lecturerList;
		
		try {
			lecturerList = mapper.readValue(new File(YAML_FILE_PATH), new TypeReference<List<Lecturer>>(){});
		} catch (IOException e) {
			throw new IllegalArgumentException("Bad yaml file " + YAML_FILE_PATH);
		}

		return lecturerList;
	}
}