package ua.foxminded.yakovlev.university.dao.impl;

import static org.junit.jupiter.api.Assertions.*;

import java.util.ArrayList;
import java.util.List;
import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.context.support.ClassPathXmlApplicationContext;
import ua.foxminded.yakovlev.university.dao.CourseDao;
import ua.foxminded.yakovlev.university.entity.Course;
import ua.foxminded.yakovlev.university.testutil.TestDatabaseGenerator;

class CourseDaoImplTest {

	private static TestDatabaseGenerator generator;
	private static CourseDao dao;
	private static ClassPathXmlApplicationContext context;

	@BeforeAll
	static void initTestCase() {
		context = new ClassPathXmlApplicationContext("applicationContext.xml");
		generator = context.getBean("databaseGenerator", TestDatabaseGenerator.class);
		dao = context.getBean("courseDao", CourseDao.class);
	}

	@BeforeEach
	void initDB() {
		generator.generate();
	}
	
	@Test
	void findAllShouldReturnCertainListOfCourses() {
		
		List<Course> expected = getAllCourses();
		List<Course> actual = dao.findAll();
		
		assertEquals(expected, actual);
	}
	
	@Test
	void findByIdShouldReturnCertainCourse() {
		
		Course expected = getAllCourses().get(1);
		Course actual = dao.findById(2L);
		
		assertEquals(expected, actual);
	}
	
	@Test
	void deleteShouldDeleteCertainCourse() {
		
		List<Course> expected = getAllCourses();
		expected.remove(3);
		
		dao.delete(4L);
		
		List<Course> actual = dao.findAll();
		
		assertEquals(expected, actual);
	}
	
	@Test
	void findByNameShouldReturnCertainCourse() {
		
		Course expected = getCourse(2L, "Физика", "Общий курс физики");
		
		Course actual = dao.findCourseByName("Физика");
		
		assertEquals(expected, actual);
	}
		
	@Test
	void saveShouldSaveCertainCourse() {
		
		List<Course> expected = getAllCourses();
		Course newCourse = getCourse(5L, "Химия", "Неорганическая физика");
		expected.add(newCourse);
		Course courseToAdd = getCourse(0L, "Химия", "Неорганическая физика");
		
		dao.save(courseToAdd);
		
		List<Course> actual = dao.findAll();
		
		assertEquals(expected, actual);
	}
	
	@Test
	void saveShouldReturnJustSavedCourse() {
		
		Course courseToAdd = getCourse(0L, "Химия", "Неорганическая физика");
		
		Course expected = getCourse(5L, "Химия", "Неорганическая физика");
		Course actual = dao.save(courseToAdd);		
		
		assertEquals(expected, actual);
	}
	
	@Test
	void updateShouldUpdateCertainFieldsOfCourse() {
		
		Course expected = getCourse(2L, "Химия", "Неорганическая физика");
				
		dao.update(expected);
		
		Course actual = dao.findById(expected.getId());
		
		assertEquals(expected, actual);
	}
	
	@Test
	void updateShouldReturnUpdatedCourse() {
		
		Course changedCourse = getCourse(2L, "Химия", "Неорганическая физика");
		
		Course expected = dao.update(changedCourse);		
		Course actual = dao.findById(changedCourse.getId());
		
		assertEquals(expected, actual);
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
