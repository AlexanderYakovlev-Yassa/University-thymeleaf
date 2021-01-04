package ua.foxminded.yakovlev.university.service.impl;

import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import ua.foxminded.yakovlev.university.entity.Course;
import ua.foxminded.yakovlev.university.repository.CourseRepository;
import ua.foxminded.yakovlev.university.service.CourseService;

@Service
@Transactional
public class CourseServiceImpl extends AbstractServiceJpa<Course, Long> implements CourseService {
	
	private static final String ENTITY_NAME = "Course";
	private final CourseRepository dao;

	public CourseServiceImpl(CourseRepository courseDao) {
		super(courseDao);
		this.dao = courseDao;
	}

	@Override
	public Course findCourseByName(String courseName) {		
		return dao.findCourseByName(courseName);
	}

	@Override
	protected String getEntityName() {		
		return ENTITY_NAME;
	}
}
