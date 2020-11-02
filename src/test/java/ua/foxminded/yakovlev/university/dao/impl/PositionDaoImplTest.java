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
import ua.foxminded.yakovlev.university.init.AppConfiguration;
import ua.foxminded.yakovlev.university.testutil.TestDatabaseGenerator;

class PositionDaoImplTest {

	private static AnnotationConfigApplicationContext context;
	private static TestDatabaseGenerator generator;
	private static PositionDao dao;

	@BeforeAll
	static void initTestCase() {
		context = new AnnotationConfigApplicationContext(AppConfiguration.class);
		generator = context.getBean("databaseGenerator", TestDatabaseGenerator.class);
		dao = context.getBean("positionDao", PositionDaoImpl.class);
	}

	@BeforeEach
	void initDB() {
		generator.generate();
	}
	
	@Test
	void findAllShouldReturnCertainListOfPositions() {
		
		List<Position> expected = getAllPositions();
		List<Position> actual = dao.findAll();
		
		assertEquals(expected, actual);
	}
	
	@Test
	void findByIdShouldReturnCertainPosition() {
		
		Position expected = getAllPositions().get(1);
		Position actual = dao.findById(2L);
		
		assertEquals(expected, actual);
	}
	
	@Test
	void deleteShouldDeleteCertainPosition() {
		
		List<Position> expected = getAllPositions();
		expected.remove(3);
		
		dao.delete(4L);
		
		List<Position> actual = dao.findAll();
		
		assertEquals(expected, actual);
	}
	
	@Test
	void findByNameShouldReturnCertainPosition() {
		
		Position expected = getPosition(4L, "ASSISTANT_PROFESSOR");
		
		Position actual = dao.findPositionByName("ASSISTANT_PROFESSOR");
		
		assertEquals(expected, actual);
	}
		
	@Test
	void saveShouldSaveCertainPosition() {
		
		List<Position> expected = getAllPositions();
		Position newPosition = getPosition(10L, "TEST");
		expected.add(newPosition);
		Position positionToAdd = getPosition(0L, "TEST");
		
		dao.save(positionToAdd);
		
		List<Position> actual = dao.findAll();
		
		assertEquals(expected, actual);
	}
	
	@Test
	void saveShouldReturnJustSavedPosition() {
		
		Position positionToAdd = getPosition(0L, "TEST");
		
		Position expected = getPosition(10L, "TEST");
		Position actual = dao.save(positionToAdd);		
		
		assertEquals(expected, actual);
	}
	
	@Test
	void updateShouldUpdateCertainFieldsOfPosition() {
		
		Position expected = getPosition(2L, "TEST");
				
		dao.update(expected);
		
		Position actual = dao.findById(expected.getId());
		
		assertEquals(expected, actual);
	}
	
	@Test
	void updateShouldReturnUpdatedPosition() {
		
		Position changedPosition = getPosition(2L, "TEST");
		
		Position expected = dao.update(changedPosition);		
		Position actual = dao.findById(changedPosition.getId());
		
		assertEquals(expected, actual);
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
