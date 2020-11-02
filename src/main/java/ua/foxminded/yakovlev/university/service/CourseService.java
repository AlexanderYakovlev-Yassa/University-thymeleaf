package ua.foxminded.yakovlev.university.service;

import ua.foxminded.yakovlev.university.entity.Course;

public interface CourseService extends EntityService<Course, Long>{

	Course findCourseByName(String courseName);
}
