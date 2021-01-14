package ua.foxminded.yakovlev.university.util;

import java.util.ArrayList;
import java.util.List;

import ua.foxminded.yakovlev.university.entity.Course;

public class CourseGenerator {

	public Course getCourse(Long id, String name, String description) {
		
		Course course = new Course();
		course.setId(id);
		course.setName(name);
		course.setDescription(description);
		
		return course;
	}
	
	public List<Course> getCourseList() {
		
		List<Course> courseList = new ArrayList<>();
		courseList.add(getCourse(1L, "Course 1 name", "Course 1 description"));
		courseList.add(getCourse(2L, "Course 2 name", "Course 2 description"));
		
		return courseList;
	}
}
