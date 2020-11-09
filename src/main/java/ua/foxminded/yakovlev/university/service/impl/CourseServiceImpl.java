package ua.foxminded.yakovlev.university.service.impl;

import ua.foxminded.yakovlev.university.dao.CourseDao;
import ua.foxminded.yakovlev.university.entity.Course;
import ua.foxminded.yakovlev.university.exception.DaoNotFoundException;
import ua.foxminded.yakovlev.university.exception.ServiceNotFoundException;
import ua.foxminded.yakovlev.university.service.CourseService;

public class CourseServiceImpl extends AbstractService<Course, Long> implements CourseService {
	
	private final CourseDao dao;

	public CourseServiceImpl(CourseDao courseDao) {
		super(courseDao);
		this.dao = courseDao;
	}

	@Override
	public Course findCourseByName(String courseName) throws ServiceNotFoundException {		
		
		try {
			return dao.findCourseByName(courseName);
		} catch (DaoNotFoundException e) {
			throw new ServiceNotFoundException("Entity not found");
		}
	}
}
