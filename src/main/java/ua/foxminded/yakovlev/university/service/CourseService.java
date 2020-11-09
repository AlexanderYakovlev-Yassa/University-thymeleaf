package ua.foxminded.yakovlev.university.service;

import ua.foxminded.yakovlev.university.entity.Course;
import ua.foxminded.yakovlev.university.exception.ServiceNotFoundException;

public interface CourseService extends EntityService<Course, Long>{

	Course findCourseByName(String courseName) throws ServiceNotFoundException;
}
