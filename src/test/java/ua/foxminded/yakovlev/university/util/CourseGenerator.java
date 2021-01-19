package ua.foxminded.yakovlev.university.util;

import java.io.File;
import java.io.IOException;
import java.util.List;

import com.fasterxml.jackson.core.type.TypeReference;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.dataformat.yaml.YAMLFactory;

import ua.foxminded.yakovlev.university.entity.Course;

public class CourseGenerator {
	
	private static final String YAML_FILE_PATH = "src/test/resources/entity/courses.yaml";


	public Course getCourse(Long id, String name, String description) {
		
		Course course = new Course();
		course.setId(id);
		course.setName(name);
		course.setDescription(description);
		
		return course;
	}
	
	public List<Course> getCourseList() {

		ObjectMapper mapper = new ObjectMapper(new YAMLFactory());
		List<Course> courseList;
		try {
			courseList = mapper.readValue(new File(YAML_FILE_PATH), new TypeReference<List<Course>>(){});
		} catch (IOException e) {
			throw new IllegalArgumentException("Bad yaml file " + YAML_FILE_PATH);
		}
				
		return courseList;
	}
}
