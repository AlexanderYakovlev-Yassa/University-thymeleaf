package ua.foxminded.yakovlev.university.repository;

import static org.junit.jupiter.api.Assertions.*;

import java.util.List;
import java.util.Optional;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest;
import org.springframework.boot.test.autoconfigure.orm.jpa.TestEntityManager;
import ua.foxminded.yakovlev.university.entity.Course;
import ua.foxminded.yakovlev.university.util.CourseGenerator;
import ua.foxminded.yakovlev.university.util.PersistedEntityCache;

@DataJpaTest
class CourseRepositoryIntegrationTest {
	
	private static final CourseGenerator COURSE_GENERATOR = new CourseGenerator();
	
	@Autowired
    private TestEntityManager testEntityManager;
	
    @Autowired
    protected CourseRepository repository;

    List<Course> courseList;
    
    @BeforeEach
    void init() {
    	PersistedEntityCache cache = new PersistedEntityCache(testEntityManager);
    	courseList = COURSE_GENERATOR.getCourseList();
    	cache.persistCourseList(courseList);
    }
    
    @Test
    void findCourseByNameShoudReturnCourseIfOneExists() {    	
    	String expectedName = courseList.get(0).getName();  	
    	assertEquals(expectedName, repository.findCourseByName(expectedName).getName());
    }
    
    @Test
    void findCourseByNameShoudReturnNullIfOneDoesNotExist() {    	
    	String courseName = "Such name doesn't exist";   	    	
    	Course course = repository.findCourseByName(courseName);    	
    	assertNull(course);
    }
    
    @Test
    void findByIdShoudReturnOptionalCourseIfOneExists() {
    	Long id = courseList.get(1).getId();	
    	String expectedName = courseList.get(1).getName();
    	assertEquals(expectedName, repository.findById(id).orElse(new Course()).getName());
    }
    
    @Test
    void findByIdShoudReturnEmptyOptionalIfOneDoesNotExist() {    	
    	Long id = 999L;
    	Optional<Course> course = repository.findById(id);
    	assertEquals(Optional.empty(), course);
    }
    
    @Test
    void findAllShouldReturnListOfCourses() {
    	assertEquals(courseList, repository.findAll());
    }
    
    @Test
    void saveSholdSaveAndReturnSavedCourse() {    	
    	Course expected = COURSE_GENERATOR.getCourse(null, "New course", "New course description");
    	Course actual = repository.save(expected);
    	expected.setId(testEntityManager.getId(expected, Long.class));
    	assertEquals(expected, actual);    	
    }
    
    @Test
    void saveAndFlushShoudUpdateAndReturnCourse() {
    	Course expected = courseList.get(0);
    	expected.setName("New name");
    	expected.setDescription("New desription");
    	Course actual = repository.saveAndFlush(expected);
    	assertEquals(expected, actual);
    }
    
    @Test
    void deleteShouldDeleteCourse() {
    	
    	List<Course> expected = courseList;
    	Course course = expected.get(1);
    	expected.remove(1);
    	
    	repository.delete(course);
    	List<Course> actual = repository.findAll();
    	assertEquals(expected, actual);
    }
}