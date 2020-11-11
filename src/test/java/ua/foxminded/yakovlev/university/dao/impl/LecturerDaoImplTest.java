package ua.foxminded.yakovlev.university.dao.impl;

import static org.junit.jupiter.api.Assertions.*;

import java.util.ArrayList;
import java.util.List;
import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.context.annotation.AnnotationConfigApplicationContext;
import ua.foxminded.yakovlev.university.dao.LecturerDao;
import ua.foxminded.yakovlev.university.entity.Position;
import ua.foxminded.yakovlev.university.exception.AlreadyExistsException;
import ua.foxminded.yakovlev.university.exception.ConstrainException;
import ua.foxminded.yakovlev.university.exception.NotFoundException;
import ua.foxminded.yakovlev.university.init.AppConfiguration;
import ua.foxminded.yakovlev.university.util.DatabaseGenerator;
import ua.foxminded.yakovlev.university.entity.Lecturer;

class LecturerDaoImplTest {

	private static AnnotationConfigApplicationContext context;
	private static DatabaseGenerator generator;
	private static LecturerDao dao;

	@BeforeAll
	static void initTestCase() {
		context = new AnnotationConfigApplicationContext(AppConfiguration.class);
		generator = context.getBean("databaseGenerator", DatabaseGenerator.class);
		dao = context.getBean("lecturerDao", LecturerDaoImpl.class);
	}

	@BeforeEach
	void initDB() {
		generator.generate();
	}
	
	@Test
	void findAllShouldReturnCertainListOfLecturers() {
		
		List<Lecturer> expected = getAllLecturers();
		List<Lecturer> actual = null;
		
		try {
			actual = dao.findAll();
		} catch (NotFoundException e) {
			fail(e);
		}
		
		assertEquals(expected, actual);
	}
	
	@Test
	void findByIdShouldReturnCertainLecturer() {
		
		Lecturer expected = getAllLecturers().get(1);
		Lecturer actual = null;
		
		try {
			actual = dao.findById(2L);
		} catch (NotFoundException e) {
			fail(e);
		}
		
		assertEquals(expected, actual);
	}
	
	@Test
	void deleteShouldDeleteCertainLecturer() {
		
		List<Lecturer> expected = getAllLecturers();
		expected.remove(3);		
		List<Lecturer> actual = null;
		
		try {
			dao.delete(4L);
			actual = dao.findAll();
		} catch (NotFoundException | ConstrainException e) {
			fail(e);
		}
		
		assertEquals(expected, actual);
	}
	
	@Test
	void findByPositionIdShouldReturnCertainListOfLecturers() {
		
		List<Lecturer> expected = getAllLecturers();
		expected.remove(3);
		expected.remove(2);
		
		List<Lecturer> actual = null;
		
		try {
			actual = dao.findByPositionId(7L);
		} catch (NotFoundException e) {
			fail(e);
		}
		
		assertEquals(expected, actual);
	}
	
	
	@Test
	void saveShouldSaveCertainLecturer() {
		
		List<Lecturer> expected = getAllLecturers();
		Lecturer newLecturer = getLecturer(3L, "ASSOCIATE_PROFESSOR", 10L, 5L, "Новый", "Лектор");
		expected.add(newLecturer);
		Lecturer lecturerToAdd = getLecturer(3L, "ASSOCIATE_PROFESSOR", 10L, 5L, "Новый", "Лектор");
		
		List<Lecturer> actual = null;
		
		try {
			dao.save(lecturerToAdd);
			actual = dao.findAll();
		} catch (NotFoundException | AlreadyExistsException | ConstrainException e) {
			fail(e);
		}
		
		assertEquals(expected, actual);
	}
	
	
	@Test
	void updateShouldUpdateCertainFieldsOfLecturer() {
		
		Lecturer expected = getAllLecturers().get(1);
		expected.setFirstName("Измененный");
		expected.setLastName("Студент");
		Position newPosition = getAllLecturers().get(3).getPosition();
		expected.setPosition(newPosition);
		
		Lecturer actual = null;
		
		try {
			dao.update(expected);
			actual = dao.findById(expected.getLecturerId());
		} catch (NotFoundException | AlreadyExistsException e) {
			fail(e);
		}
		
		assertEquals(expected, actual);
	}
	
	@Test
	void updateShouldReturnUpdatedLecturer() {
		
		Lecturer newLecturer = getAllLecturers().get(1);
		newLecturer.setFirstName("Измененный");
		newLecturer.setLastName("Студент");
		Position newPosition = getAllLecturers().get(3).getPosition();
		newLecturer.setPosition(newPosition);
		
		Lecturer expected = null;
		Lecturer actual = null;
		
		try {
			expected = dao.update(newLecturer);
			actual = dao.findById(newLecturer.getLecturerId());
		} catch (NotFoundException | AlreadyExistsException e) {
			fail(e);
		}
		
		assertEquals(expected, actual);
	}
	
	@Test
	void findByIdShouldThrowsDaoNotFoundExceptionWhenLecturerIdIsNotExisting() {		
		assertThrows(NotFoundException.class, () -> dao.findById(99L));
	}
	
	@Test
	void deleteShouldReturnsDaoConstrainExceptionWhenDeletingLecturerIsInUseInAnotherTable() {		
		assertThrows(ConstrainException.class, () -> dao.delete(1L));
	}
	
	List<Lecturer> getAllLecturers() {
		
		List<Lecturer> allLecturers = new ArrayList<>();
		
		allLecturers.add(getLecturer(7L, "INSTRUCTOR", 6L, 1L, "Андрей", "Аксенов"));
		allLecturers.add(getLecturer(7L, "INSTRUCTOR", 7L, 2L, "Мирон", "Давыдов"));
		allLecturers.add(getLecturer(8L, "RESEARCH_ASSOCIATE", 8L, 3L, "Владимир", "Ойстрах"));
		allLecturers.add(getLecturer(3L, "ASSOCIATE_PROFESSOR", 9L, 4L, "Cтанислав", "Кудесник"));
		
		return allLecturers;
	}

	Lecturer getLecturer(Long positionId, String positionName, Long personId, Long lecturerId, String firstName, String lastName) {
		
		Lecturer lecturer = new Lecturer();
		Position position = new Position();

		position.setId(positionId);
		position.setName(positionName);
		lecturer.setPersonId(personId);
		lecturer.setLecturerId(lecturerId);
		lecturer.setFirstName(firstName);
		lecturer.setLastName(lastName);
		lecturer.setPosition(position);
		
		return lecturer;
	}
}
