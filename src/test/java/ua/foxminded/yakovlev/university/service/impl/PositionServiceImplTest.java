package ua.foxminded.yakovlev.university.service.impl;

import static org.junit.jupiter.api.Assertions.*;

import java.util.ArrayList;
import java.util.List;
import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.context.annotation.AnnotationConfigApplicationContext;
import ua.foxminded.yakovlev.university.entity.Position;
import ua.foxminded.yakovlev.university.init.PositionDaoTestConfiguration;
import ua.foxminded.yakovlev.university.service.PositionService;
import ua.foxminded.yakovlev.university.util.DatabaseGenerator;

class PositionServiceImplTest {

	private static AnnotationConfigApplicationContext context;
	private static DatabaseGenerator generator;
	private static PositionService service;

	@BeforeAll
	static void initTestCase() {
		context = new AnnotationConfigApplicationContext(PositionDaoTestConfiguration.class);
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
		List<Position> actual = service.findAll();
		
		assertEquals(expected, actual);
	}
	
	@Test
	void findByIdShouldReturnCertainPosition() {
		
		Position expected = getAllPositions().get(1);
		Position actual = service.findById(2L);
		
		assertEquals(expected, actual);
	}
	
	@Test
	void deleteShouldDeleteCertainPosition() {
		
		List<Position> expected = getAllPositions();
		expected.remove(3);
		
		service.delete(4L);
		
		List<Position> actual = service.findAll();
		
		assertEquals(expected, actual);
	}
	
	@Test
	void findByNameShouldReturnCertainPosition() {
		
		Position expected = getPosition(4L, "ASSISTANT_PROFESSOR");
		
		Position actual = service.findPositionByName("ASSISTANT_PROFESSOR");
		
		assertEquals(expected, actual);
	}
		
	@Test
	void saveShouldSaveCertainPosition() {
		
		List<Position> expected = getAllPositions();
		Position newPosition = getPosition(10L, "TEST");
		expected.add(newPosition);
		Position positionToAdd = getPosition(0L, "TEST");
		
		service.save(positionToAdd);
		
		List<Position> actual = service.findAll();
		
		assertEquals(expected, actual);
	}
	
	@Test
	void saveShouldReturnJustSavedPosition() {
		
		Position positionToAdd = getPosition(0L, "TEST");
		
		Position expected = getPosition(10L, "TEST");
		Position actual = service.save(positionToAdd);		
		
		assertEquals(expected, actual);
	}
	
	@Test
	void updateShouldUpdateCertainFieldsOfPosition() {
		
		Position expected = getPosition(2L, "TEST");
				
		service.update(expected);
		
		Position actual = service.findById(expected.getId());
		
		assertEquals(expected, actual);
	}
	
	@Test
	void updateShouldReturnUpdatedPosition() {
		
		Position changedPosition = getPosition(2L, "TEST");
		
		Position expected = service.update(changedPosition);		
		Position actual = service.findById(changedPosition.getId());
		
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
