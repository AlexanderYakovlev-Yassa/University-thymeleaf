package ua.foxminded.yakovlev.university.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

import ua.foxminded.yakovlev.university.entity.Course;

public interface CourseRepository extends JpaRepository<Course, Long>{

    Course findCourseByName(String name);
}