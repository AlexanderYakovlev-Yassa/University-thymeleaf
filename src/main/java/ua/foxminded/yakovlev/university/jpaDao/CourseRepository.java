package ua.foxminded.yakovlev.university.jpaDao;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

import ua.foxminded.yakovlev.university.entity.Course;

public interface CourseRepository extends JpaRepository<Course, Long>{
		
	@Query("select c from Course c where c.name = ?1")
    Course findCourseByName(String name);
}