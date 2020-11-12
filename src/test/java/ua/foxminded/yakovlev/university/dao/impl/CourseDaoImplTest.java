package ua.foxminded.yakovlev.university.dao.impl;

import static org.junit.jupiter.api.Assertions.*;

import java.util.ArrayList;
import java.util.List;
import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.context.annotation.AnnotationConfigApplicationContext;
import ua.foxminded.yakovlev.university.dao.CourseDao;
import ua.foxminded.yakovlev.university.entity.Course;
import ua.foxminded.yakovlev.university.exception.AlreadyExistsException;
import ua.foxminded.yakovlev.university.exception.ConstrainException;
import ua.foxminded.yakovlev.university.exception.NotFoundException;
import ua.foxminded.yakovlev.university.init.CourseDaoTestConfiguration;
import ua.foxminded.yakovlev.university.util.DatabaseGenerator;

class CourseDaoImplTest {

	private static DatabaseGenerator generator;
	private static CourseDao dao;
	private static AnnotationConfigApplicationContext context;

	@BeforeAll
	static void initTestCase() {
		context = new AnnotationConfigApplicationContext(CourseDaoTestConfiguration.class);
		generator = context.getBean("databaseGenerator", DatabaseGenerator.class);
		dao = context.getBean("courseDao", CourseDao.class);
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
			actual = dao.findAll();
		} catch (NotFoundException e) {
			fail(e);
		}
		
		assertEquals(expected, actual);
	}
	
	@Test
	void findByIdShouldReturnCertainCourse() {
		
		Course expected = getAllCourses().get(1);
		Course actual = null;
		try {
			actual = dao.findById(2L);
		} catch (NotFoundException e) {
			fail(e);
		}
		
		assertEquals(expected, actual);
	}
	
	@Test
	void findByIdShouldThrowsDaoNotFoundExceptionWhenCourseIdIsNotExisting() {		
		assertThrows(NotFoundException.class, () -> dao.findById(99L));
	}
	
	@Test
	void deleteShouldDeleteCertainCourse() {
		
		List<Course> expected = getAllCourses();
		expected.remove(3);
		List<Course> actual = null;
				
		try {
			dao.delete(4L);
			actual = dao.findAll();
		} catch (ConstrainException | NotFoundException e) {
			fail(e);
		}		
		
		assertEquals(expected, actual);
	}
	
	@Test
	void deleteShouldReturnsDaoConstrainExceptionWhenDeletingCoursIsInUseInAnotherTable() {		
		assertThrows(ConstrainException.class, () -> dao.delete(1L));
	}
	
	@Test
	void findByNameShouldReturnCertainCourse() {
		
		Course expected = getCourse(2L, "Физика", "Общий курс физики");
		
		Course actual = null;
		
		try {
			actual = dao.findCourseByName("Физика");
		} catch (NotFoundException e) {
			fail(e);
		}
		
		assertEquals(expected, actual);
	}
	
	@Test
	void findByNameShouldThrowsDaoNotFoundExceptionWhenCourseWithSuchNameIsNotExist() {
		assertThrows(NotFoundException.class, () -> dao.findCourseByName("Кибернетика"));
	}
		
	@Test
	void saveShouldSaveCertainCourse() {
		
		List<Course> expected = getAllCourses();
		Course newCourse = getCourse(5L, "Химия", "Неорганическая физика");
		expected.add(newCourse);
		Course courseToAdd = getCourse(0L, "Химия", "Неорганическая физика");
		List<Course> actual = null;
		
		try {
			dao.save(courseToAdd);
			actual = dao.findAll();
		} catch (NotFoundException | AlreadyExistsException | ConstrainException e) {
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
			actual = dao.save(courseToAdd);
		} catch (NotFoundException | AlreadyExistsException | ConstrainException e) {
			fail(e);
		}
		
		assertEquals(expected, actual);
	}
	
	@Test
	void saveShouldThrowsDaoAlreadyExistsExceptionWhenSavingCourseNameAlreadyExists() {
		
		Course course = getCourse(null, "Математика", null);
		
		assertThrows(AlreadyExistsException.class, () -> dao.save(course));
	}
	
	@Test
	void updateShouldUpdateCertainFieldsOfCourse() {
		
		Course expected = getCourse(2L, "Химия", "Неорганическая физика");
		Course actual = null;	
		
		try {
			dao.update(expected);
			actual = dao.findById(expected.getId());
		} catch (NotFoundException | AlreadyExistsException e) {
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
			expected = dao.update(changedCourse);
			actual = dao.findById(changedCourse.getId());
		} catch (NotFoundException | AlreadyExistsException e) {
			fail(e);
		}		
		
		assertEquals(expected, actual);
	}
	
	@Test
	void updateShouldThrowsDaoAlreadyExistsExceptionWhenNewUpdatingCourseNameHasAlreadyExisted() {
		
		Course course = getCourse(2L, "Математика", "Общий курс физики");
		
		assertThrows(AlreadyExistsException.class, () -> dao.update(course));
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
