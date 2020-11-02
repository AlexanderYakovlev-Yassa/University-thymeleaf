package ua.foxminded.yakovlev.university.service.impl;

import ua.foxminded.yakovlev.university.dao.CourseDao;
import ua.foxminded.yakovlev.university.entity.Course;
import ua.foxminded.yakovlev.university.service.CourseService;
import ua.foxminded.yakovlev.university.validator.CourseValidator;

public class CourseServiceImpl extends AbstractService<Course, Long> implements CourseService {
	
	private final CourseDao dao;

	public CourseServiceImpl(CourseDao courseDao, CourseValidator courseValidator) {
		super(courseDao, courseValidator);
		this.dao = courseDao;
	}

	@Override
	public Course findCourseByName(String courseName) {		
		return dao.findCourseByName(courseName);
	}
}
