package ua.foxminded.yakovlev.university.service.impl;

import static org.junit.jupiter.api.Assertions.*;

import java.util.ArrayList;
import java.util.List;

import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.context.annotation.AnnotationConfigApplicationContext;

import ua.foxminded.yakovlev.university.entity.Group;
import ua.foxminded.yakovlev.university.exception.ServiceAlreadyExistsException;
import ua.foxminded.yakovlev.university.exception.ServiceConstrainException;
import ua.foxminded.yakovlev.university.exception.ServiceNotFoundException;
import ua.foxminded.yakovlev.university.init.AppConfiguration;
import ua.foxminded.yakovlev.university.service.GroupService;
import ua.foxminded.yakovlev.university.util.DatabaseGenerator;

class GroupServiceImplTest {

	private static AnnotationConfigApplicationContext context;
	private static DatabaseGenerator generator;
	private static GroupService service;

	@BeforeAll
	static void initTestCase() {
		context = new AnnotationConfigApplicationContext(AppConfiguration.class);
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
		List<Group> actual = null;
		
		try {
			actual = service.findAll();
		} catch (ServiceNotFoundException e) {
			fail(e);
		}
		
		assertEquals(expected, actual);
	}
	
	@Test
	void findByIdShouldReturnCertainGroup() {
		
		Group expected = getAllGroups().get(1);
		Group actual = null;
		
		try {
			actual = service.findById(2L);
		} catch (ServiceNotFoundException e) {
			fail(e);
		}
		
		assertEquals(expected, actual);
	}
	
	@Test
	void deleteShouldDeleteCertainGroup() {
		
		List<Group> expected = getAllGroups();
		expected.remove(3);
		
		List<Group> actual = null;
		
		try {
			service.delete(4L);
			actual = service.findAll();
		} catch (ServiceNotFoundException | ServiceConstrainException e) {
			fail(e);
		}
		
		assertEquals(expected, actual);
	}
	
	@Test
	void findByNameShouldReturnCertainGroup() {
		
		Group expected = getGroup(1L, "aa-01");
		
		Group actual = null;
		
		try {
			actual = service.findGroupByName("aa-01");
		} catch (ServiceNotFoundException e) {
			fail(e);
		}
		
		assertEquals(expected, actual);
	}
		
	@Test
	void saveShouldSaveCertainGroup() {
		
		List<Group> expected = getAllGroups();
		Group newGroup = getGroup(5L, "ne-66");
		expected.add(newGroup);
		Group groupToAdd = getGroup(0L, "ne-66");
		
		List<Group> actual = null;
		
		try {
			service.save(groupToAdd);
			actual = service.findAll();
		} catch (ServiceNotFoundException | ServiceAlreadyExistsException | ServiceConstrainException e) {
			fail(e);
		}
		
		assertEquals(expected, actual);
	}
	
	@Test
	void saveShouldReturnJustSavedGroup() {
		
		Group groupToAdd = getGroup(0L, "ne-66");
		
		Group expected = getGroup(5L, "ne-66");
		Group actual = null;
		
		try {
			actual = service.save(groupToAdd);
		} catch (ServiceNotFoundException | ServiceAlreadyExistsException | ServiceConstrainException e) {
			fail(e);
		}		
		
		assertEquals(expected, actual);
	}
	
	@Test
	void updateShouldUpdateCertainFieldsOfGroup() {
		
		Group expected = getGroup(2L, "ne-66");
				
		Group actual = null;
		
		try {
			service.update(expected);
			actual = service.findById(expected.getId());
		} catch (ServiceNotFoundException | ServiceAlreadyExistsException e) {
			fail(e);
		}
		
		assertEquals(expected, actual);
	}
	
	@Test
	void updateShouldReturnUpdatedGroup() {
		
		Group changedGroup = getGroup(2L, "ne-66");
		
		Group expected = null;		
		Group actual = null;
		
		try {
			expected = service.update(changedGroup);		
			actual = service.findById(changedGroup.getId());
		} catch (ServiceNotFoundException | ServiceAlreadyExistsException e) {
			fail(e);
		}
		
		assertEquals(expected, actual);
	}
	
	@Test
	void findByIdShouldThrowsServiceNotFoundExceptionWhenGroupIdIsNotExisting() {		
		assertThrows(ServiceNotFoundException.class, () -> service.findById(99L));
	}
	
	@Test
	void deleteShouldReturnsServiceConstrainExceptionWhenDeletingGroupIsInUseInAnotherTable() {		
		assertThrows(ServiceConstrainException.class, () -> service.delete(1L));
	}
	
	@Test
	void findByNameShouldThrowsServiceNotFoundExceptionWhenGroupWithSuchNameIsNotExist() {
		assertThrows(ServiceNotFoundException.class, () -> service.findGroupByName("aa-11"));
	}
	
	@Test
	void saveShouldThrowsServiceAlreadyExistsExceptionWhenSavingGroupNameAlreadyExists() {
		
		Group group = getGroup(null, "aa-02");
		
		assertThrows(ServiceAlreadyExistsException.class, () -> service.save(group));
	}
	
	@Test
	void updateShouldThrowsServiceAlreadyExistsExceptionWhenNewUpdatingGroupNameHasAlreadyExisted() {
		
		Group group = getGroup(2L, "aa-01");
		
		assertThrows(ServiceAlreadyExistsException.class, () -> service.update(group));
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
