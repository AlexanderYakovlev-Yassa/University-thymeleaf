package ua.foxminded.yakovlev.university.dao.impl;

import static org.junit.jupiter.api.Assertions.*;

import java.util.ArrayList;
import java.util.List;
import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.context.annotation.AnnotationConfigApplicationContext;
import ua.foxminded.yakovlev.university.dao.GroupDao;
import ua.foxminded.yakovlev.university.entity.Group;
import ua.foxminded.yakovlev.university.exception.AlreadyExistsException;
import ua.foxminded.yakovlev.university.exception.ConstrainException;
import ua.foxminded.yakovlev.university.exception.NotFoundException;
import ua.foxminded.yakovlev.university.init.GroupDaoTestConfiguration;
import ua.foxminded.yakovlev.university.util.DatabaseGenerator;

class GroupDaoImplTest {

	private static AnnotationConfigApplicationContext context;
	private static DatabaseGenerator generator;
	private static GroupDao dao;

	@BeforeAll
	static void initTestCase() {
		context = new AnnotationConfigApplicationContext(GroupDaoTestConfiguration.class);
		generator = context.getBean("databaseGenerator", DatabaseGenerator.class);
		dao = context.getBean("groupDao", GroupDaoImpl.class);
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
			actual = dao.findAll();
		} catch (NotFoundException e) {
			fail(e);
		}
		
		assertEquals(expected, actual);
	}
	
	@Test
	void findByIdShouldReturnCertainGroup() {
		
		Group expected = getAllGroups().get(1);
		Group actual = null;
		
		try {
			actual = dao.findById(2L);
		} catch (NotFoundException e) {
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
			dao.delete(4L);
			actual = dao.findAll();
		} catch (ConstrainException | NotFoundException e) {
			fail(e);
		}		
		
		assertEquals(expected, actual);
	}
	
	@Test
	void findByNameShouldReturnCertainGroup() {
		
		Group expected = getGroup(1L, "aa-01");
		Group actual = null;
		
		try {
			actual = dao.findGroupByName("aa-01");
		} catch (NotFoundException e) {
			fail(e);
		}
		
		assertEquals(expected, actual);
	}
		
	@Test
	void saveShouldSaveCertainGroup() {
		
		List<Group> expected = getAllGroups();
		Group newGroup = getGroup(5L, "new-6");
		expected.add(newGroup);
		Group groupToAdd = getGroup(0L, "new-6");		
		
		List<Group> actual = null;
		try {
			dao.save(groupToAdd);
			actual = dao.findAll();
		} catch (NotFoundException | AlreadyExistsException | ConstrainException e) {
			fail(e);
		}
		
		assertEquals(expected, actual);
	}
	
	@Test
	void saveShouldReturnJustSavedGroup() {
		
		Group groupToAdd = getGroup(0L, "new-6");
		
		Group expected = getGroup(5L, "new-6");
		Group actual = null;
		
		try {
			actual = dao.save(groupToAdd);
		} catch (NotFoundException | AlreadyExistsException | ConstrainException e) {
			fail(e);
		}		
		
		assertEquals(expected, actual);
	}
	
	@Test
	void updateShouldUpdateCertainFieldsOfGroup() {
		
		Group expected = getGroup(2L, "Измененное");
		Group actual = null;
		
		try {
			dao.update(expected);
			actual = dao.findById(expected.getId());
		} catch (NotFoundException | AlreadyExistsException e) {
			fail(e);
		}
		
		assertEquals(expected, actual);
	}
	
	@Test
	void updateShouldReturnUpdatedGroup() {
		
		Group changedGroup = getGroup(2L, "Измененное");
		
		Group expected = null;		
		Group actual = null;
		
		try {
			expected = dao.update(changedGroup);
			actual = dao.findById(changedGroup.getId());
		} catch (NotFoundException | AlreadyExistsException e) {
			fail(e);
		}		
		
		assertEquals(expected, actual);
	}
	
	@Test
	void findByIdShouldThrowsDaoNotFoundExceptionWhenGroupIdIsNotExisting() {		
		assertThrows(NotFoundException.class, () -> dao.findById(99L));
	}
	
	@Test
	void deleteShouldReturnsDaoConstrainExceptionWhenDeletingGroupIsInUseInAnotherTable() {		
		assertThrows(ConstrainException.class, () -> dao.delete(1L));
	}
	
	@Test
	void findByNameShouldThrowsDaoNotFoundExceptionWhenGroupWithSuchNameIsNotExist() {
		assertThrows(NotFoundException.class, () -> dao.findGroupByName("aa-11"));
	}
	
	@Test
	void saveShouldThrowsDaoAlreadyExistsExceptionWhenSavingGroupNameAlreadyExists() {
		
		Group group = getGroup(null, "aa-02");
		
		assertThrows(AlreadyExistsException.class, () -> dao.save(group));
	}
	
	@Test
	void updateShouldThrowsDaoAlreadyExistsExceptionWhenNewUpdatingGroupNameHasAlreadyExisted() {
		
		Group group = getGroup(2L, "aa-01");
		
		assertThrows(AlreadyExistsException.class, () -> dao.update(group));
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
