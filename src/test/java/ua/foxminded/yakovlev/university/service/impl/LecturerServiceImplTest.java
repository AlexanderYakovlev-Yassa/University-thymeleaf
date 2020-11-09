package ua.foxminded.yakovlev.university.service.impl;

import static org.junit.jupiter.api.Assertions.*;

import java.util.ArrayList;
import java.util.List;
import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.context.annotation.AnnotationConfigApplicationContext;
import ua.foxminded.yakovlev.university.entity.Position;
import ua.foxminded.yakovlev.university.exception.ServiceAlreadyExistsException;
import ua.foxminded.yakovlev.university.exception.ServiceConstrainException;
import ua.foxminded.yakovlev.university.exception.ServiceNotFoundException;
import ua.foxminded.yakovlev.university.init.AppConfiguration;
import ua.foxminded.yakovlev.university.service.LecturerService;
import ua.foxminded.yakovlev.university.util.DatabaseGenerator;
import ua.foxminded.yakovlev.university.entity.Lecturer;

class LecturerServiceImplTest {

	private static AnnotationConfigApplicationContext context;
	private static DatabaseGenerator generator;
	private static LecturerService service;

	@BeforeAll
	static void initTestCase() {
		context = new AnnotationConfigApplicationContext(AppConfiguration.class);
		generator = context.getBean("databaseGenerator", DatabaseGenerator.class);
		service = context.getBean("lecturerService", LecturerServiceImpl.class);
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
			actual = service.findAll();
		} catch (ServiceNotFoundException e) {
			fail(e);
		}
		
		assertEquals(expected, actual);
	}
	
	@Test
	void findByIdShouldReturnCertainLecturer() {
		
		Lecturer expected = getAllLecturers().get(1);
		Lecturer actual = null;
		
		try {
			actual = service.findById(2L);
		} catch (ServiceNotFoundException e) {
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
			service.delete(4L);
			actual = service.findAll();
		} catch (ServiceNotFoundException | ServiceConstrainException e) {
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
			actual = service.findByPositionId(7L);
		} catch (ServiceNotFoundException e) {
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
			service.save(lecturerToAdd);
			actual = service.findAll();
		} catch (ServiceNotFoundException | ServiceAlreadyExistsException | ServiceConstrainException e) {
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
			service.update(expected);
			actual = service.findById(expected.getLecturerId());
		} catch (ServiceNotFoundException | ServiceAlreadyExistsException e) {
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
			expected = service.update(newLecturer);
			actual = service.findById(newLecturer.getLecturerId());
		} catch (ServiceNotFoundException | ServiceAlreadyExistsException e) {
			fail(e);
		}
		
		assertEquals(expected, actual);
	}
	
	@Test
	void findByIdShouldThrowsServiceNotFoundExceptionWhenLecturerIdIsNotExisting() {		
		assertThrows(ServiceNotFoundException.class, () -> service.findById(99L));
	}
	
	@Test
	void deleteShouldReturnsServiceConstrainExceptionWhenDeletingLecturerIsInUseInAnotherTable() {		
		assertThrows(ServiceConstrainException.class, () -> service.delete(1L));
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
