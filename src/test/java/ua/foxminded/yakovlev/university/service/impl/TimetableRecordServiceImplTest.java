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
import ua.foxminded.yakovlev.university.init.AppConfiguration;
import ua.foxminded.yakovlev.university.service.TimetableRecordService;
import ua.foxminded.yakovlev.university.testutil.TestDatabaseGenerator;

class TimetableRecordServiceImplTest {

	private static AnnotationConfigApplicationContext context;
	private static TestDatabaseGenerator generator;
	private static TimetableRecordService service;

	@BeforeAll
	static void initTestCase() {
		context = new AnnotationConfigApplicationContext(AppConfiguration.class);
		generator = context.getBean("databaseGenerator", TestDatabaseGenerator.class);
		service = context.getBean("timetableRecordService", TimetableRecordServiceImpl.class);
	}

	@BeforeEach
	void initDB() {
		generator.generate();
	}
	
	@Test
	void findAllShouldReturnCertainListOfTimetableRecords() {
		
		List<TimetableRecord> expected = getAllTimetableRecords();
		List<TimetableRecord> actual = service.findAll();
		
		assertEquals(expected, actual);
	}
	
	@Test
	void findByIdShouldReturnCertainTimetableRecord() {
		
		TimetableRecord expected = getAllTimetableRecords().get(1);
		TimetableRecord actual = service.findById(2L);
		
		assertEquals(expected, actual);
	}
	
	@Test
	void deleteShouldDeleteCertainTimetableRecord() {
		
		List<TimetableRecord> expected = getAllTimetableRecords();
		expected.remove(2);
		
		service.delete(3L);
		
		List<TimetableRecord> actual = service.findAll();
		
		assertEquals(expected, actual);
	}
	
	@Test
	void findByPeriodOfTimeShouldReturnCertainTimetableRecordList() {
		
		List<TimetableRecord> expected = getAllTimetableRecords();
		expected.remove(2);
		expected.remove(0);
		
		LocalDateTime firstDate = LocalDateTime.parse("2020-10-16T09:30:00");
		LocalDateTime secondDate = LocalDateTime.parse("2020-10-16T11:30:00");
		
		List<TimetableRecord> actual = service.findByPeriodOfTime(firstDate, secondDate);
		
		assertEquals(expected, actual);
	}
	
	@Test
	void saveShouldSaveCertainTimetableRecord() {
		
		List<TimetableRecord> expected = getAllTimetableRecords();
		TimetableRecord newTimetableRecord = getTimetableRecord(7L, "INSTRUCTOR", 6L, 1L, "Андрей", "Аксенов", 3L, "Музыка", "Основы музыкальной грамоты", 4L, "2020-10-16T15:00:00", getAllGroups());
		expected.add(newTimetableRecord);
		TimetableRecord timetableRecordToAdd = getTimetableRecord(7L, "INSTRUCTOR", 6L, 1L, "Андрей", "Аксенов", 3L, "Музыка", "Основы музыкальной грамоты", 0L, "2020-10-16T15:00:00", getAllGroups());
		
		service.save(timetableRecordToAdd);
		
		List<TimetableRecord> actual = service.findAll();
		
		assertEquals(expected, actual);
	}
	
	@Test
	void saveShouldReturnJustSavedTimetableRecord() {
		
		TimetableRecord timetableRecordToAdd = getTimetableRecord(7L, "INSTRUCTOR", 6L, 1L, "Андрей", "Аксенов", 3L, "Музыка", "Основы музыкальной грамоты", 0L, "2020-10-16T15:00:00", getAllGroups());
		
		TimetableRecord expected = getTimetableRecord(7L, "INSTRUCTOR", 6L, 1L, "Андрей", "Аксенов", 3L, "Музыка", "Основы музыкальной грамоты", 4L, "2020-10-16T15:00:00", getAllGroups());
		TimetableRecord actual = service.save(timetableRecordToAdd);		
		
		assertEquals(expected, actual);
	}
	
	@Test
	void updateShouldUpdateCertainFieldsOfTimetableRecord() {
		
		TimetableRecord expected = getAllTimetableRecords().get(0);
		expected.setDate(LocalDateTime.parse("2020-10-17T09:00:00"));
				
		service.update(expected);
		
		TimetableRecord actual = service.findById(expected.getId());
		
		assertEquals(expected, actual);
	}
	
	@Test
	void updateShouldReturnUpdatedTimetableRecord() {
		
		TimetableRecord expected = getAllTimetableRecords().get(0);
		expected.setDate(LocalDateTime.parse("2020-10-17T09:00:00"));
				
		service.update(expected);
		TimetableRecord actual = service.findById(expected.getId());
		
		assertEquals(expected, actual);
	}
	
	@Test
	void addGroupToTimeableShouldAddCertainRecord() {
		
		Group groupForAdd = getGroup(3L, "ab-03");
		TimetableRecord expected = service.findById(2L);
		expected.getGroupList().add(groupForAdd);
		
		service.addGroupToTimeable(2L, 3L);
		
		TimetableRecord actual = service.findById(2L);
		
		assertEquals(expected, actual);
	}
	
	@Test
	void addGroupToTimeableShouldReturnUpdatedTimetableRecord() {
		
		Group groupForAdd = getGroup(3L, "ab-03");
		TimetableRecord expected = service.findById(2L);
		expected.getGroupList().add(groupForAdd);
		
		TimetableRecord actual = service.addGroupToTimeable(2L, 3L);
		
		assertEquals(expected, actual);
	}
	
	@Test
	void removeGroupFromTimeableShouldRemoveCertainRecord() {
		
		TimetableRecord expected = service.findById(1L);
		expected.getGroupList().remove(0);
		
		service.removeGroupFromTimeable(1L, 1L);
		
		TimetableRecord actual = service.findById(1L);
		
		assertEquals(expected, actual);
	}
	
	@Test
	void removeGroupFromTimeableShouldReturnUpdatedTimetableRecord() {
		
		TimetableRecord expected = service.findById(1L);
		expected.getGroupList().remove(0);
		
		TimetableRecord actual = service.removeGroupFromTimeable(1L, 1L);
		
		assertEquals(expected, actual);
	}
	
	@Test
	void findByPeriodOfTimeAndLecturerIdShouldReturnCertainTimetableRecordList() {
		
		List<TimetableRecord> expected = getAllTimetableRecords();
		expected.remove(0);
		expected.remove(0);
		
		LocalDateTime firstDate = LocalDateTime.parse("2020-10-16T09:30:00");
		LocalDateTime secondDate = LocalDateTime.parse("2020-10-16T12:30:00");
		Long lecturerId = 3L;
		
		List<TimetableRecord> actual = service.findByPeriodOfTimeAndLecturerId(firstDate, secondDate, lecturerId);
		
		assertEquals(expected, actual);
	}
	
	@Test
	void findByPeriodOfTimeAndStudentIdShouldReturnCertainTimetableRecordList() {
		
		List<TimetableRecord> expected = getAllTimetableRecords();
		expected.remove(0);
		expected.remove(0);
		
		LocalDateTime firstDate = LocalDateTime.parse("2020-10-16T09:30:00");
		LocalDateTime secondDate = LocalDateTime.parse("2020-10-16T13:00:00");
		Long studentId = 3L;
		
		List<TimetableRecord> actual = service.findByPeriodOfTimeAndLecturerId(firstDate, secondDate, studentId);
		
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
