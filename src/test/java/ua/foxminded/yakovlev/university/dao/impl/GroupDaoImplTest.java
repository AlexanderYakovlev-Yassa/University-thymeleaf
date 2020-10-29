package ua.foxminded.yakovlev.university.dao.impl;

import static org.junit.jupiter.api.Assertions.*;

import java.util.ArrayList;
import java.util.List;
import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.context.support.ClassPathXmlApplicationContext;
import ua.foxminded.yakovlev.university.entity.Group;
import ua.foxminded.yakovlev.university.testutil.TestDatabaseGenerator;

class GroupDaoImplTest {

	private static ClassPathXmlApplicationContext context;
	private static TestDatabaseGenerator generator;
	private static GroupDaoImpl dao;

	@BeforeAll
	static void initTestCase() {
		context = new ClassPathXmlApplicationContext("applicationContext.xml");
		generator = context.getBean("databaseGenerator", TestDatabaseGenerator.class);
		dao = context.getBean("groupDao", GroupDaoImpl.class);
	}

	@BeforeEach
	void initDB() {
		generator.generate();
	}
	
	@Test
	void findAllShouldReturnCertainListOfGroups() {
		
		List<Group> expected = getAllGroups();
		List<Group> actual = dao.findAll();
		
		assertEquals(expected, actual);
	}
	
	@Test
	void findByIdShouldReturnCertainGroup() {
		
		Group expected = getAllGroups().get(1);
		Group actual = dao.findById(2L);
		
		assertEquals(expected, actual);
	}
	
	@Test
	void deleteShouldDeleteCertainGroup() {
		
		List<Group> expected = getAllGroups();
		expected.remove(3);
		
		dao.delete(4L);
		
		List<Group> actual = dao.findAll();
		
		assertEquals(expected, actual);
	}
	
	@Test
	void findByNameShouldReturnCertainGroup() {
		
		Group expected = getGroup(1L, "aa-01");
		
		Group actual = dao.findGroupByName("aa-01");
		
		assertEquals(expected, actual);
	}
		
	@Test
	void saveShouldSaveCertainGroup() {
		
		List<Group> expected = getAllGroups();
		Group newGroup = getGroup(5L, "new-6");
		expected.add(newGroup);
		Group groupToAdd = getGroup(0L, "new-6");
		
		dao.save(groupToAdd);
		
		List<Group> actual = dao.findAll();
		
		assertEquals(expected, actual);
	}
	
	@Test
	void saveShouldReturnJustSavedGroup() {
		
		Group groupToAdd = getGroup(0L, "new-6");
		
		Group expected = getGroup(5L, "new-6");
		Group actual = dao.save(groupToAdd);		
		
		assertEquals(expected, actual);
	}
	
	@Test
	void updateShouldUpdateCertainFieldsOfGroup() {
		
		Group expected = getGroup(2L, "Измененное");
				
		dao.update(expected);
		
		Group actual = dao.findById(expected.getId());
		
		assertEquals(expected, actual);
	}
	
	@Test
	void updateShouldReturnUpdatedGroup() {
		
		Group changedGroup = getGroup(2L, "Измененное");
		
		Group expected = dao.update(changedGroup);		
		Group actual = dao.findById(changedGroup.getId());
		
		assertEquals(expected, actual);
	}
	
	List<Group> getAllGroups() {
		
		List<Group> allGroups = new ArrayList<>();
		
		allGroups.add(getGroup(1L, "aa-01"));
		allGroups.add(getGroup(2L, "aa-02"));
		allGroups.add(getGroup(3L, "ab-03"));
		allGroups.add(getGroup(4L, "ab-04"));

		return allGroups;
	}
	
	Group getGroup(Long groupId, String groupName) {
				
		Group group = new Group();

		group.setId(groupId);
		group.setName(groupName);
		
		return group;
	}
}
