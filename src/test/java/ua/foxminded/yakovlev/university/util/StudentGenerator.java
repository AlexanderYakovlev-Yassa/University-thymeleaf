package ua.foxminded.yakovlev.university.util;

import java.io.File;
import java.io.IOException;
import java.util.List;

import com.fasterxml.jackson.core.type.TypeReference;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.dataformat.yaml.YAMLFactory;

import ua.foxminded.yakovlev.university.entity.Group;
import ua.foxminded.yakovlev.university.entity.Student;

public class StudentGenerator {
	
	private static final String YAML_FILE_PATH = "src/test/resources/entity/students.yaml";

	public Student getStudent(Long personId, String firstName, String lastName, Group group) {
		
		Student student = new Student();
		student.setPersonId(personId);
		student.setFirstName(firstName);
		student.setLastName(lastName);
		student.setGroup(group);
		
		return student;
	}
	
	public List<Student> getStudentList() {

		ObjectMapper mapper = new ObjectMapper(new YAMLFactory());
		
		List<Student> studentList;
		
		try {
			studentList = mapper.readValue(new File(YAML_FILE_PATH), new TypeReference<List<Student>>(){});
		} catch (IOException e) {
			throw new IllegalArgumentException("Bad yaml file " + YAML_FILE_PATH);
		}

		return studentList;
	}
}