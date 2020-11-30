package ua.foxminded.yakovlev.university.jpaDao;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import ua.foxminded.yakovlev.university.entity.Course;

public interface CourseRepository extends JpaRepository<Course, Long>{
	
	@Query("select c from courses c where c.course_name = :name")
    Course findCourseByName(@Param("name") String name);
}