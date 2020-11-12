package ua.foxminded.yakovlev.university.dao.impl;

import static org.junit.jupiter.api.Assertions.*;

import java.util.ArrayList;
import java.util.List;
import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.context.annotation.AnnotationConfigApplicationContext;
import ua.foxminded.yakovlev.university.dao.PositionDao;
import ua.foxminded.yakovlev.university.entity.Position;
import ua.foxminded.yakovlev.university.exception.AlreadyExistsException;
import ua.foxminded.yakovlev.university.exception.ConstrainException;
import ua.foxminded.yakovlev.university.exception.NotFoundException;
import ua.foxminded.yakovlev.university.init.PositionDaoTestConfiguration;
import ua.foxminded.yakovlev.university.util.DatabaseGenerator;

class PositionDaoImplTest {

	private static AnnotationConfigApplicationContext context;
	private static DatabaseGenerator generator;
	private static PositionDao dao;

	@BeforeAll
	static void initTestCase() {
		context = new AnnotationConfigApplicationContext(PositionDaoTestConfiguration.class);
		generator = context.getBean("databaseGenerator", DatabaseGenerator.class);
		dao = context.getBean("positionDao", PositionDaoImpl.class);
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
			actual = dao.findAll();
		} catch (NotFoundException e) {
			fail(e);
		}
		
		assertEquals(expected, actual);
	}
	
	@Test
	void findByIdShouldReturnCertainPosition() {
		
		Position expected = getAllPositions().get(1);
		Position actual = null;
		
		try {
			actual = dao.findById(2L);
		} catch (NotFoundException e) {
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
			dao.delete(4L);
			actual = dao.findAll();
		} catch (NotFoundException | ConstrainException e) {
			fail(e);
		}
		
		assertEquals(expected, actual);
	}
	
	@Test
	void findByNameShouldReturnCertainPosition() {
		
		Position expected = getPosition(4L, "ASSISTANT_PROFESSOR");
		
		Position actual = null;
		
		try {
			actual = dao.findPositionByName("ASSISTANT_PROFESSOR");
		} catch (NotFoundException e) {
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
			dao.save(positionToAdd);
			actual = dao.findAll();
		} catch (NotFoundException | AlreadyExistsException | ConstrainException e) {
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
			actual = dao.save(positionToAdd);
		} catch (NotFoundException | AlreadyExistsException | ConstrainException e) {
			fail(e);
		}		
		
		assertEquals(expected, actual);
	}
	
	@Test
	void updateShouldUpdateCertainFieldsOfPosition() {
		
		Position expected = getPosition(2L, "TEST");
				
		
		Position actual = null;
		try {
			dao.update(expected);
			actual = dao.findById(expected.getId());
		} catch (NotFoundException | AlreadyExistsException e) {
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
			expected = dao.update(changedPosition);		
			actual = dao.findById(changedPosition.getId());
		} catch (NotFoundException | AlreadyExistsException e) {
			fail(e);
		}
		
		assertEquals(expected, actual);
	}
	
	@Test
	void findByIdShouldThrowsDaoNotFoundExceptionWhenPositionIdIsNotExisting() {		
		assertThrows(NotFoundException.class, () -> dao.findById(99L));
	}
	
	@Test
	void deleteShouldReturnsDaoConstrainExceptionWhenDeletingPositionIsInUseInAnotherTable() {		
		assertThrows(ConstrainException.class, () -> dao.delete(8L));
	}
	
	@Test
	void findByNameShouldThrowsDaoNotFoundExceptionWhenPositionWithSuchNameIsNotExist() {
		assertThrows(NotFoundException.class, () -> dao.findPositionByName("Gelmeister"));
	}
	
	@Test
	void saveShouldThrowsDaoAlreadyExistsExceptionWhenSavingPositionNameAlreadyExists() {
		
		Position position = getPosition(null, "PROFESSOR");
		
		assertThrows(AlreadyExistsException.class, () -> dao.save(position));
	}
	
	@Test
	void updateShouldThrowsDaoAlreadyExistsExceptionWhenNewUpdatingPositionNameHasAlreadyExisted() {
		
		Position group = getPosition(2L, "UNIVERSITY_PROFESSOR");
		
		assertThrows(AlreadyExistsException.class, () -> dao.update(group));
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
