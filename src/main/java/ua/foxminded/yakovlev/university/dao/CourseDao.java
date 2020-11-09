package ua.foxminded.yakovlev.university.dao;

import ua.foxminded.yakovlev.university.entity.Course;
import ua.foxminded.yakovlev.university.exception.DaoNotFoundException;

public interface CourseDao extends EntityDao<Course, Long> {

	Course findCourseByName(String courseName) throws DaoNotFoundException;
}
