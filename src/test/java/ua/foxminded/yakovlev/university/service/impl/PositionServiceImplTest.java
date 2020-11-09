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
import ua.foxminded.yakovlev.university.service.PositionService;
import ua.foxminded.yakovlev.university.util.DatabaseGenerator;

class PositionServiceImplTest {

	private static AnnotationConfigApplicationContext context;
	private static DatabaseGenerator generator;
	private static PositionService service;

	@BeforeAll
	static void initTestCase() {
		context = new AnnotationConfigApplicationContext(AppConfiguration.class);
		generator = context.getBean("databaseGenerator", DatabaseGenerator.class);
		service = context.getBean("positionService", PositionServiceImpl.class);
	}

	@BeforeEach
	void initDB() {
		generator.generate();
	}
	
	@Test
	void findAllShouldReturnCertainListOfPositions() {
		
		List<Position> expected = getAllPositions();
		List<Position> actual = null;
		
		try {
			actual = service.findAll();
		} catch (ServiceNotFoundException e) {
			fail(e);
		}
		
		assertEquals(expected, actual);
	}
	
	@Test
	void findByIdShouldReturnCertainPosition() {
		
		Position expected = getAllPositions().get(1);
		Position actual = null;
		
		try {
			actual = service.findById(2L);
		} catch (ServiceNotFoundException e) {
			fail(e);
		}
		
		assertEquals(expected, actual);
	}
	
	@Test
	void deleteShouldDeleteCertainPosition() {
		
		List<Position> expected = getAllPositions();
		expected.remove(3);
		
		List<Position> actual = null;
		
		try {
			service.delete(4L);
			actual = service.findAll();
		} catch (ServiceNotFoundException | ServiceConstrainException e) {
			fail(e);
		}
		
		assertEquals(expected, actual);
	}
	
	@Test
	void findByNameShouldReturnCertainPosition() {
		
		Position expected = getPosition(4L, "ASSISTANT_PROFESSOR");
		
		Position actual = null;
		
		try {
			actual = service.findPositionByName("ASSISTANT_PROFESSOR");
		} catch (ServiceNotFoundException e) {
			fail(e);
		}
		
		assertEquals(expected, actual);
	}
		
	@Test
	void saveShouldSaveCertainPosition() {
		
		List<Position> expected = getAllPositions();
		Position newPosition = getPosition(10L, "TEST");
		expected.add(newPosition);
		Position positionToAdd = getPosition(0L, "TEST");
		
		List<Position> actual = null;
		
		try {
			service.save(positionToAdd);
			actual = service.findAll();
		} catch (ServiceNotFoundException | ServiceAlreadyExistsException | ServiceConstrainException e) {
			fail(e);
		}
		
		assertEquals(expected, actual);
	}
	
	@Test
	void saveShouldReturnJustSavedPosition() {
		
		Position positionToAdd = getPosition(0L, "TEST");
		
		Position expected = getPosition(10L, "TEST");
		Position actual = null;
		
		try {
			actual = service.save(positionToAdd);
		} catch (ServiceNotFoundException | ServiceAlreadyExistsException | ServiceConstrainException e) {
			fail(e);
		}		
		
		assertEquals(expected, actual);
	}
	
	@Test
	void updateShouldUpdateCertainFieldsOfPosition() {
		
		Position expected = getPosition(2L, "TEST");
				
		Position actual = null;
		
		try {
			service.update(expected);
			actual = service.findById(expected.getId());
		} catch (ServiceNotFoundException | ServiceAlreadyExistsException e) {
			fail(e);
		}
		
		assertEquals(expected, actual);
	}
	
	@Test
	void updateShouldReturnUpdatedPosition() {
		
		Position changedPosition = getPosition(2L, "TEST");
		
		Position expected = null;		
		Position actual = null;
		
		try {
			expected = service.update(changedPosition);
			actual = service.findById(changedPosition.getId());
		} catch (ServiceNotFoundException | ServiceAlreadyExistsException e) {
			fail(e);
		}		
		
		assertEquals(expected, actual);
	}
	
	@Test
	void findByIdShouldThrowsServiceNotFoundExceptionWhenPositionIdIsNotExisting() {		
		assertThrows(ServiceNotFoundException.class, () -> service.findById(99L));
	}
	
	@Test
	void deleteShouldReturnsServiceConstrainExceptionWhenDeletingPositionIsInUseInAnotherTable() {		
		assertThrows(ServiceConstrainException.class, () -> service.delete(8L));
	}
	
	@Test
	void findByNameShouldThrowsServiceNotFoundExceptionWhenPositionWithSuchNameIsNotExist() {
		assertThrows(ServiceNotFoundException.class, () -> service.findPositionByName("Gelmeister"));
	}
	
	@Test
	void saveShouldThrowsServiceAlreadyExistsExceptionWhenSavingPositionNameAlreadyExists() {
		
		Position position = getPosition(null, "PROFESSOR");
		
		assertThrows(ServiceAlreadyExistsException.class, () -> service.save(position));
	}
	
	@Test
	void updateShouldThrowsServiceAlreadyExistsExceptionWhenNewUpdatingPositionNameHasAlreadyExisted() {
		
		Position group = getPosition(2L, "UNIVERSITY_PROFESSOR");
		
		assertThrows(ServiceAlreadyExistsException.class, () -> service.update(group));
	}
	
	List<Position> getAllPositions() {
		
		List<Position> allPositions = new ArrayList<>();
		
		allPositions.add(getPosition(1L, "UNIVERSITY_PROFESSOR"));
		allPositions.add(getPosition(2L, "PROFESSOR"));
		allPositions.add(getPosition(3L, "ASSOCIATE_PROFESSOR"));
		allPositions.add(getPosition(4L, "ASSISTANT_PROFESSOR"));
		allPositions.add(getPosition(5L, "MASTER_INSTRUCTOR"));
		allPositions.add(getPosition(6L, "SENIOR_INSTRUCTOR"));
		allPositions.add(getPosition(7L, "INSTRUCTOR"));
		allPositions.add(getPosition(8L, "RESEARCH_ASSOCIATE"));
		allPositions.add(getPosition(9L, "ADJUNCT_PROFESSOR"));

		return allPositions;
	}
	
	Position getPosition(Long positionId, String positionName) {
				
		Position position = new Position();

		position.setId(positionId);
		position.setName(positionName);
		
		return position;
	}
}
