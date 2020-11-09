package ua.foxminded.yakovlev.university.service.impl;

import static org.junit.jupiter.api.Assertions.*;

import java.util.ArrayList;
import java.util.List;
import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.context.annotation.AnnotationConfigApplicationContext;
import ua.foxminded.yakovlev.university.entity.Course;
import ua.foxminded.yakovlev.university.exception.ServiceAlreadyExistsException;
import ua.foxminded.yakovlev.university.exception.ServiceConstrainException;
import ua.foxminded.yakovlev.university.exception.ServiceNotFoundException;
import ua.foxminded.yakovlev.university.init.AppConfiguration;
import ua.foxminded.yakovlev.university.service.CourseService;
import ua.foxminded.yakovlev.university.util.DatabaseGenerator;

class CourseServiceImplTest {

	private static DatabaseGenerator generator;
	private static CourseService service;
	private static AnnotationConfigApplicationContext context;

	@BeforeAll
	static void initTestCase() {
		context = new AnnotationConfigApplicationContext(AppConfiguration.class);
		generator = context.getBean("databaseGenerator", DatabaseGenerator.class);
		service = context.getBean("courseService", CourseService.class);
	}

	@BeforeEach
	void initDB() {
		generator.generate();
	}
	
	@Test
	void findAllShouldReturnCertainListOfCourses() {
		
		List<Course> expected = getAllCourses();
		List<Course> actual = null;
		
		try {
			actual = service.findAll();
		} catch (ServiceNotFoundException e) {
			fail(e);
		}
		
		assertEquals(expected, actual);
	}
	
	@Test
	void findByIdShouldReturnCertainCourse() {
		
		Course expected = getAllCourses().get(1);
		Course actual = null;
		
		try {
			actual = service.findById(2L);
		} catch (ServiceNotFoundException e) {
			fail(e);
		}
		
		assertEquals(expected, actual);
	}
	
	@Test
	void findByIdShouldThrowsServiceNotFoundExceptionWhenCourseIdIsNotExisting() {		
		assertThrows(ServiceNotFoundException.class, () -> service.findById(99L));
	}
	
	@Test
	void deleteShouldDeleteCertainCourse() {
		
		List<Course> expected = getAllCourses();
		expected.remove(3);
		
		List<Course> actual = null;
		
		try {
			service.delete(4L);
			actual = service.findAll();
		} catch (ServiceNotFoundException | ServiceConstrainException e) {
			fail(e);
		}
		
		assertEquals(expected, actual);
	}
	
	@Test
	void deleteShouldReturnsServiceConstrainExceptionWhenDeletingCoursIsInUseInAnotherTable() {		
		assertThrows(ServiceConstrainException.class, () -> service.delete(1L));
	}
	
	@Test
	void findByNameShouldReturnCertainCourse() {
		
		Course expected = getCourse(2L, "Физика", "Общий курс физики");
		
		Course actual = null;
		
		try {
			actual = service.findCourseByName("Физика");
		} catch (ServiceNotFoundException e) {
			fail(e);
		}
		
		assertEquals(expected, actual);
	}
	
	@Test
	void findByNameShouldThrowsServiceNotFoundExceptionWhenCourseWithSuchNameIsNotExist() {
		assertThrows(ServiceNotFoundException.class, () -> service.findCourseByName("Кибернетика"));
	}
		
	@Test
	void saveShouldSaveCertainCourse() {
		
		List<Course> expected = getAllCourses();
		Course newCourse = getCourse(5L, "Химия", "Неорганическая физика");
		expected.add(newCourse);
		Course courseToAdd = getCourse(0L, "Химия", "Неорганическая физика");
		
		List<Course> actual = null;
		
		try {
			service.save(courseToAdd);
			actual = service.findAll();
		} catch (ServiceNotFoundException | ServiceAlreadyExistsException | ServiceConstrainException e) {
			fail(e);
		}
		
		assertEquals(expected, actual);
	}
	
	@Test
	void saveShouldReturnJustSavedCourse() {
		
		Course courseToAdd = getCourse(0L, "Химия", "Неорганическая физика");
		
		Course expected = getCourse(5L, "Химия", "Неорганическая физика");
		Course actual = null;
		
		try {
			actual = service.save(courseToAdd);
		} catch (ServiceNotFoundException | ServiceAlreadyExistsException | ServiceConstrainException e) {
			fail(e);
		}		
		
		assertEquals(expected, actual);
	}
	
	@Test
	void saveShouldThrowsServiceAlreadyExistsExceptionWhenSavingCourseNameAlreadyExists() {
		
		Course course = getCourse(null, "Математика", null);
		
		assertThrows(ServiceAlreadyExistsException.class, () -> service.save(course));
	}
	
	@Test
	void updateShouldUpdateCertainFieldsOfCourse() {
		
		Course expected = getCourse(2L, "Химия", "Неорганическая физика");
				
		Course actual = null;
		
		try {
			service.update(expected);
			actual = service.findById(expected.getId());
		} catch (ServiceNotFoundException | ServiceAlreadyExistsException e) {
			fail(e);
		}
		
		assertEquals(expected, actual);
	}
	
	@Test
	void updateShouldReturnUpdatedCourse() {
		
		Course changedCourse = getCourse(2L, "Химия", "Неорганическая физика");
		
		Course expected = null;		
		Course actual = null;
		
		try {
			expected = service.update(changedCourse);		
			actual = service.findById(changedCourse.getId());
		} catch (ServiceNotFoundException | ServiceAlreadyExistsException e) {
			fail(e);
		}
		
		assertEquals(expected, actual);
	}
	
	@Test
	void updateShouldThrowsServiceAlreadyExistsExceptionWhenNewUpdatingCourseNameHasAlreadyExisted() {
		
		Course course = getCourse(2L, "Математика", "Общий курс физики");
		
		assertThrows(ServiceAlreadyExistsException.class, () -> service.update(course));
	}
	
	List<Course> getAllCourses() {
		
		List<Course> allCourses = new ArrayList<>();
		
		allCourses.add(getCourse(1L, "Математика", "Общий курс математики"));
		allCourses.add(getCourse(2L, "Физика", "Общий курс физики"));
		allCourses.add(getCourse(3L, "Музыка", "Основы музыкальной грамоты"));
		allCourses.add(getCourse(4L, "Биология", "Общий курс биологии"));

		return allCourses;
	}
	
	Course getCourse(Long courseId, String courseName, String courseDescription) {
				
		Course course = new Course();

		course.setId(courseId);
		course.setName(courseName);
		course.setDescription(courseDescription);
		
		return course;
	}
}
