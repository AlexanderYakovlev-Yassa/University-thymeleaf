package ua.foxminded.yakovlev.university.service.impl;

import static org.junit.jupiter.api.Assertions.*;

import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;
import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.context.annotation.AnnotationConfigApplicationContext;
import ua.foxminded.yakovlev.university.entity.Course;
import ua.foxminded.yakovlev.university.entity.Group;
import ua.foxminded.yakovlev.university.entity.Lecturer;
import ua.foxminded.yakovlev.university.entity.Position;
import ua.foxminded.yakovlev.university.entity.TimetableRecord;
import ua.foxminded.yakovlev.university.exception.ServiceAlreadyExistsException;
import ua.foxminded.yakovlev.university.exception.ServiceConstrainException;
import ua.foxminded.yakovlev.university.exception.ServiceNotFoundException;
import ua.foxminded.yakovlev.university.init.AppConfiguration;
import ua.foxminded.yakovlev.university.service.TimetableRecordService;
import ua.foxminded.yakovlev.university.util.DatabaseGenerator;

class TimetableRecordServiceImplTest {

	private static AnnotationConfigApplicationContext context;
	private static DatabaseGenerator generator;
	private static TimetableRecordService service;

	@BeforeAll
	static void initTestCase() {
		context = new AnnotationConfigApplicationContext(AppConfiguration.class);
		generator = context.getBean("databaseGenerator", DatabaseGenerator.class);
		service = context.getBean("timetableRecordService", TimetableRecordServiceImpl.class);
	}

	@BeforeEach
	void initDB() {
		generator.generate();
	}
	
	@Test
	void findAllShouldReturnCertainListOfTimetableRecords() {
		
		List<TimetableRecord> expected = getAllTimetableRecords();
		List<TimetableRecord> actual = null;
		
		try {
			actual = service.findAll();
		} catch (ServiceNotFoundException e) {
			fail(e);
		}
		
		assertEquals(expected, actual);
	}
	
	@Test
	void findByIdShouldReturnCertainTimetableRecord() {
		
		TimetableRecord expected = getAllTimetableRecords().get(1);
		TimetableRecord actual = null;
		
		try {
			actual = service.findById(2L);
		} catch (ServiceNotFoundException e) {
			fail(e);
		}
		
		assertEquals(expected, actual);
	}
	
	@Test
	void deleteShouldDeleteCertainTimetableRecord() {
		
		List<TimetableRecord> expected = getAllTimetableRecords();
		expected.remove(2);
		
		List<TimetableRecord> actual = null;
		
		try {
			service.delete(3L);
			actual = service.findAll();
		} catch (ServiceNotFoundException | ServiceConstrainException e) {
			fail(e);
		}
		
		assertEquals(expected, actual);
	}
	
	@Test
	void findByPeriodOfTimeShouldReturnCertainTimetableRecordList() {
		
		List<TimetableRecord> expected = getAllTimetableRecords();
		expected.remove(2);
		expected.remove(0);
		
		LocalDateTime firstDate = LocalDateTime.parse("2020-10-16T09:30:00");
		LocalDateTime secondDate = LocalDateTime.parse("2020-10-16T11:30:00");
		
		List<TimetableRecord> actual = null;
		
		try {
			actual = service.findByPeriodOfTime(firstDate, secondDate);
		} catch (ServiceNotFoundException e) {
			fail(e);
		}
		
		assertEquals(expected, actual);
	}
	
	@Test
	void saveShouldSaveCertainTimetableRecord() {
		
		List<TimetableRecord> expected = getAllTimetableRecords();
		TimetableRecord newTimetableRecord = getTimetableRecord(7L, "INSTRUCTOR", 6L, 1L, "Андрей", "Аксенов", 3L, "Музыка", "Основы музыкальной грамоты", 4L, "2020-10-16T15:00:00", getAllGroups());
		expected.add(newTimetableRecord);
		TimetableRecord timetableRecordToAdd = getTimetableRecord(7L, "INSTRUCTOR", 6L, 1L, "Андрей", "Аксенов", 3L, "Музыка", "Основы музыкальной грамоты", 0L, "2020-10-16T15:00:00", getAllGroups());
		
		List<TimetableRecord> actual = null;
		
		try {
			service.save(timetableRecordToAdd);
			actual = service.findAll();
		} catch (ServiceNotFoundException | ServiceAlreadyExistsException | ServiceConstrainException e) {
			fail(e);
		}
		
		assertEquals(expected, actual);
	}
	
	@Test
	void saveShouldReturnJustSavedTimetableRecord() {
		
		TimetableRecord timetableRecordToAdd = getTimetableRecord(7L, "INSTRUCTOR", 6L, 1L, "Андрей", "Аксенов", 3L, "Музыка", "Основы музыкальной грамоты", 0L, "2020-10-16T15:00:00", getAllGroups());
		
		TimetableRecord expected = getTimetableRecord(7L, "INSTRUCTOR", 6L, 1L, "Андрей", "Аксенов", 3L, "Музыка", "Основы музыкальной грамоты", 4L, "2020-10-16T15:00:00", getAllGroups());
		TimetableRecord actual = null;
		
		try {
			actual = service.save(timetableRecordToAdd);
		} catch (ServiceNotFoundException | ServiceAlreadyExistsException | ServiceConstrainException e) {
			fail(e);
		}		
		
		assertEquals(expected, actual);
	}
	
	@Test
	void saveShouldThrowServiceAlreadyExistsExceptionWhenAddRecordWithSameTimeAndLecturer() {
		
		List<TimetableRecord> allRecords = getAllTimetableRecords();
		TimetableRecord recordForAdd = allRecords.get(0);
		recordForAdd.setId(0L);
		recordForAdd.setCourse(allRecords.get(1).getCourse());
		recordForAdd.setGroupList(allRecords.get(1).getGroupList());
		
		assertThrows(ServiceAlreadyExistsException.class, () -> service.save(recordForAdd));
	}
	
	@Test
	void updateShouldUpdateCertainFieldsOfTimetableRecord() {
		
		TimetableRecord expected = getAllTimetableRecords().get(0);
		expected.setDate(LocalDateTime.parse("2020-10-17T09:00:00"));
				
		TimetableRecord actual = null;
		
		try {
			service.update(expected);
			actual = service.findById(expected.getId());
		} catch (ServiceNotFoundException | ServiceAlreadyExistsException e) {
			fail(e);
		}
		
		assertEquals(expected, actual);
	}
	
	@Test
	void updateShouldThrowServiceAlreadyExistsExceptionWhenLecturerAndTimeInUpdatingRecordSameAsInOtherExistingRecord() {
		
		List<TimetableRecord> allRecords = getAllTimetableRecords();
		TimetableRecord updatingRecord = allRecords.get(0);
		TimetableRecord donorRecord = allRecords.get(1);
		updatingRecord.setDate(donorRecord.getDate());
		updatingRecord.setLecturer(donorRecord.getLecturer());
		
		assertThrows(ServiceAlreadyExistsException.class, () -> service.update(updatingRecord));
	}
	
	@Test
	void updateShouldReturnUpdatedTimetableRecord() {
		
		TimetableRecord expected = getAllTimetableRecords().get(0);
		expected.setDate(LocalDateTime.parse("2020-10-17T09:00:00"));
				
		TimetableRecord actual = null;
		
		try {
			service.update(expected);
			actual = service.findById(expected.getId());
		} catch (ServiceNotFoundException | ServiceAlreadyExistsException e) {
			fail(e);
		}
		
		assertEquals(expected, actual);
	}
	
	@Test
	void addGroupShouldAddCertainRecord() {
		
		Group groupForAdd = getGroup(3L, "ab-03");
		TimetableRecord expected = null;
		TimetableRecord actual = null;
		
		try {
			expected = service.findById(2L);
			expected.getGroupList().add(groupForAdd);
			service.addGroup(3L, 2L);
			actual = service.findById(2L);
		} catch (ServiceNotFoundException | ServiceConstrainException e) {
			fail(e);
		}
		
		assertEquals(expected, actual);
	}
	
	@Test
	void addGroupShouldReturnUpdatedTimetableRecord() {
		
		Group groupForAdd = getGroup(3L, "ab-03");
		
		TimetableRecord expected = null;
		TimetableRecord actual = null;
		
		try {
			expected = service.findById(2L);
			expected.getGroupList().add(groupForAdd);
			actual = service.addGroup(3L, 2L);
		} catch (ServiceNotFoundException | ServiceConstrainException e) {
			fail(e);
		}
		
		assertEquals(expected, actual);
	}
	
	@Test
	void addGroupShouldTrowsServiceConstrainExceptionWhenSuchGroupIsAlreadyInRecord() {
		
		TimetableRecord record = getAllTimetableRecords().get(0);
		Long timetableId = record.getId();
		Long alreadyExistedGroupId = record.getGroupList().get(0).getId();
		
		assertThrows(ServiceConstrainException.class, () -> service.addGroup(alreadyExistedGroupId, timetableId));
	}
	
	@Test
	void removeGroupFromTimeableShouldRemoveCertainRecord() {
		
		TimetableRecord expected = null;
		TimetableRecord actual = null;
		
		try {
			expected = service.findById(1L);
			expected.getGroupList().remove(0);			
			service.removeGroup(1L, 1L);			
			actual = service.findById(1L);
		} catch (ServiceNotFoundException e) {
			fail(e);
		}
		
		assertEquals(expected, actual);
	}
	
	@Test
	void removeGroupFromTimeableShouldReturnUpdatedTimetableRecord() {
		
		TimetableRecord expected = null;
		TimetableRecord actual = null;
		
		try {
			expected = service.findById(1L);
			expected.getGroupList().remove(0);			
			actual = service.removeGroup(1L, 1L);
		} catch (ServiceNotFoundException e) {
			fail(e);
		}
		
		assertEquals(expected, actual);
	}
	
	@Test
	void findByLecturerIdShouldReturnCertainTimetableRecordList() {
		
		List<TimetableRecord> expected = getAllTimetableRecords();
		expected.remove(0);
		expected.remove(0);
		
		LocalDateTime firstDate = LocalDateTime.parse("2020-10-16T09:30:00");
		LocalDateTime secondDate = LocalDateTime.parse("2020-10-16T12:30:00");
		Long lecturerId = 3L;
		
		List<TimetableRecord> actual = null;
		try {
			actual = service.findByLecturer(lecturerId, firstDate, secondDate);
		} catch (ServiceNotFoundException e) {
			fail(e);
		}
		
		assertEquals(expected, actual);
	}
	
	@Test
	void findByStudentIdShouldReturnCertainTimetableRecordList() {
		
		List<TimetableRecord> expected = getAllTimetableRecords();
		expected.remove(2);
		expected.remove(0);
		
		LocalDateTime firstDate = LocalDateTime.parse("2020-10-16T09:30:00");
		LocalDateTime secondDate = LocalDateTime.parse("2020-10-16T13:00:00");
		Long studentId = 3L;
		
		List<TimetableRecord> actual = null;
		try {
			actual = service.findByStudent(studentId, firstDate, secondDate);
		} catch (ServiceNotFoundException e) {
			fail(e);
		}
		
		assertEquals(expected, actual);
	}
	
	List<TimetableRecord> getAllTimetableRecords() {
		
		List<TimetableRecord> allTimetableRecords = new ArrayList<>();
		
		allTimetableRecords.add(getTimetableRecord(7L, "INSTRUCTOR", 6L, 1L, "Андрей", "Аксенов", 3L, "Музыка", "Основы музыкальной грамоты", 1L, "2020-10-16T09:00:00", getAllGroups()));
		List<Group> secondRecordGroupList = getAllGroups();
		secondRecordGroupList.remove(1);
		allTimetableRecords.add(getTimetableRecord(7L, "INSTRUCTOR", 7L, 2L, "Мирон", "Давыдов", 2L, "Физика", "Общий курс физики", 2L, "2020-10-16T10:00:00", secondRecordGroupList));
		allTimetableRecords.add(getTimetableRecord(8L, "RESEARCH_ASSOCIATE", 8L, 3L, "Владимир", "Ойстрах", 1L, "Математика", "Общий курс математики", 3L, "2020-10-16T12:00:00", new ArrayList<>()));
		
		return allTimetableRecords;
	}
	
public TimetableRecord getTimetableRecord(
		Long positionId,
		String positionName,
		Long lecturerPersonId,
		Long lecturerId,
		String lecturerFirstName,
		String lecturerLastName,
		Long courseId,
		String courseName,
		String courseDescription,
		Long timetableRecordId,
		String timetableRecordTimestamp,
		List<Group> groupList) {
		
		TimetableRecord timetableRecord = new TimetableRecord();
		Lecturer lecturer = new Lecturer();
		Position position = new Position();
		Course course = new Course();
		
		position.setId(positionId);
		position.setName(positionName);
		
		lecturer.setPersonId(lecturerPersonId);
		lecturer.setLecturerId(lecturerId);
		lecturer.setFirstName(lecturerFirstName);
		lecturer.setLastName(lecturerLastName);
		lecturer.setPosition(position);
		
		course.setId(courseId);
		course.setName(courseName);
		course.setDescription(courseDescription);
		
		timetableRecord.setId(timetableRecordId);
		timetableRecord.setDate(LocalDateTime.parse(timetableRecordTimestamp));
		timetableRecord.setLecturer(lecturer);
		timetableRecord.setCourse(course);
		timetableRecord.setGroupList(groupList);
		
		return timetableRecord;
	}

	List<Group> getAllGroups() {
	
	List<Group> allGroups = new ArrayList<>();
	
	allGroups.add(getGroup(1L, "aa-01"));
	allGroups.add(getGroup(2L, "aa-02"));

	return allGroups;
	}

	Group getGroup(Long groupId, String groupName) {
	
	Group group = new Group();

	group.setId(groupId);
	group.setName(groupName);
	
	return group;
	}
}
