package ua.foxminded.yakovlev.university.service.impl;

import static org.junit.jupiter.api.Assertions.*;

import java.util.ArrayList;
import java.util.List;

import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.context.annotation.AnnotationConfigApplicationContext;

import ua.foxminded.yakovlev.university.entity.Group;
import ua.foxminded.yakovlev.university.init.GroupDaoTestConfiguration;
import ua.foxminded.yakovlev.university.service.GroupService;
import ua.foxminded.yakovlev.university.util.DatabaseGenerator;

class GroupServiceImplTest {

	private static AnnotationConfigApplicationContext context;
	private static DatabaseGenerator generator;
	private static GroupService service;

	@BeforeAll
	static void initTestCase() {
		context = new AnnotationConfigApplicationContext(GroupDaoTestConfiguration.class);
		generator = context.getBean("databaseGenerator", DatabaseGenerator.class);
		service = context.getBean("groupService", GroupServiceImpl.class);
	}

	@BeforeEach
	void initDB() {
		generator.generate();
	}
	
	@Test
	void findAllShouldReturnCertainListOfGroups() {
		
		List<Group> expected = getAllGroups();
		List<Group> actual = service.findAll();
		
		assertEquals(expected, actual);
	}
	
	@Test
	void findByIdShouldReturnCertainGroup() {
		
		Group expected = getAllGroups().get(1);
		Group actual = service.findById(2L);
		
		assertEquals(expected, actual);
	}
	
	@Test
	void deleteShouldDeleteCertainGroup() {
		
		List<Group> expected = getAllGroups();
		expected.remove(3);
		
		service.delete(4L);
		
		List<Group> actual = service.findAll();
		
		assertEquals(expected, actual);
	}
	
	@Test
	void findByNameShouldReturnCertainGroup() {
		
		Group expected = getGroup(1L, "aa-01");
		
		Group actual = service.findGroupByName("aa-01");
		
		assertEquals(expected, actual);
	}
		
	@Test
	void saveShouldSaveCertainGroup() {
		
		List<Group> expected = getAllGroups();
		Group newGroup = getGroup(5L, "ne-66");
		expected.add(newGroup);
		Group groupToAdd = getGroup(0L, "ne-66");
		
		service.save(groupToAdd);
		
		List<Group> actual = service.findAll();
		
		assertEquals(expected, actual);
	}
	
	@Test
	void saveShouldReturnJustSavedGroup() {
		
		Group groupToAdd = getGroup(0L, "ne-66");
		
		Group expected = getGroup(5L, "ne-66");
		Group actual = service.save(groupToAdd);		
		
		assertEquals(expected, actual);
	}
	
	@Test
	void updateShouldUpdateCertainFieldsOfGroup() {
		
		Group expected = getGroup(2L, "ne-66");
				
		service.update(expected);
		
		Group actual = service.findById(expected.getId());
		
		assertEquals(expected, actual);
	}
	
	@Test
	void updateShouldReturnUpdatedGroup() {
		
		Group changedGroup = getGroup(2L, "ne-66");
		
		Group expected = service.update(changedGroup);		
		Group actual = service.findById(changedGroup.getId());
		
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
