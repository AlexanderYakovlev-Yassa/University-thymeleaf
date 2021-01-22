package ua.foxminded.yakovlev.university.repository;

import static org.junit.jupiter.api.Assertions.*;

import java.util.List;
import java.util.Optional;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest;
import org.springframework.boot.test.autoconfigure.orm.jpa.TestEntityManager;
import org.springframework.test.context.jdbc.Sql;

import ua.foxminded.yakovlev.university.entity.Course;
import ua.foxminded.yakovlev.university.util.CourseGenerator;

@DataJpaTest
class CourseRepositoryIntegrationTest {
	
	private static final CourseGenerator COURSE_GENERATOR = new CourseGenerator();
	
	@Autowired
    private TestEntityManager testEntityManager;
	
    @Autowired
    protected CourseRepository repository;
        
    @Test
    @Sql("insertCourses.sql")
    void findCourseByNameShoudReturnCourseIfOneExists() {	
    	String expectedName = repository.findAll().get(0).getName();
    	assertEquals(expectedName, repository.findCourseByName(expectedName).getName());
    }
    
    @Test
    @Sql("insertCourses.sql")
    void findCourseByNameShoudReturnNullIfOneDoesNotExist() {    	
    	String courseName = "Such name doesn't exist";   	    	
    	Course course = repository.findCourseByName(courseName);    	
    	assertNull(course);
    }
    
    @Test
    @Sql("insertCourses.sql")
    void findByIdShoudReturnOptionalCourseIfOneExists() {
    	Long id = repository.findAll().get(1).getId();	
    	String expectedName = repository.findAll().get(1).getName();
    	assertEquals(expectedName, repository.findById(id).orElse(new Course()).getName());
    }
    
    @Test
    @Sql("insertCourses.sql")
    void findByIdShoudReturnEmptyOptionalIfOneDoesNotExist() {    	
    	Long id = 999L;
    	Optional<Course> course = repository.findById(id);
    	assertEquals(Optional.empty(), course);
    }
    
    @Test
    @Sql("insertCourses.sql")
    void findAllShouldReturnListOfCourses() {
    	List<Course> courseList = repository.findAll();
    	assertEquals(4, courseList.size());
    	assertNotNull(courseList.get(0).getName());
    	assertNotNull(courseList.get(0).getDescription());
    }
    
    @Test
    @Sql("insertCourses.sql")
    void saveSholdSaveAndReturnSavedCourse() {    	
    	Course expected = COURSE_GENERATOR.getCourse(null, "New course", "New course description");
    	Course actual = repository.save(expected);
    	expected.setId(testEntityManager.getId(expected, Long.class));
    	assertEquals(expected, actual);    	
    }
    
    @Test
    @Sql("insertCourses.sql")
    void saveAndFlushShoudUpdateAndReturnCourse() {
    	Course expected = repository.findAll().get(0);
    	expected.setName("New name");
    	expected.setDescription("New desription");
    	Course actual = repository.saveAndFlush(expected);
    	assertEquals(expected, actual);
    }
    
    @Test
    @Sql("insertCourses.sql")
    void deleteShouldDeleteCourse() {
    	
    	List<Course> expected = repository.findAll();
    	Course course = expected.get(1);
    	expected.remove(1);
    	
    	repository.delete(course);
    	List<Course> actual = repository.findAll();
    	assertEquals(expected, actual);
    }
}