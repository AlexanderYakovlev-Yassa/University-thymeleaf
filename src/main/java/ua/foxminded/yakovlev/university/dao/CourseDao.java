package ua.foxminded.yakovlev.university.dao;

import ua.foxminded.yakovlev.university.entity.Course;

public interface CourseDao extends EntityDao<Course, Long> {

	Course findCourseByName(String courseName);
}
