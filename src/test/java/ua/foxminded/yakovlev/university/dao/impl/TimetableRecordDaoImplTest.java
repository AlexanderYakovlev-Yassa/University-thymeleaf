package ua.foxminded.yakovlev.university.dao.impl;

import static org.junit.jupiter.api.Assertions.*;

import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;
import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.jdbc.core.RowMapper;

import ua.foxminded.yakovlev.university.dbconnector.JdbcTemplateFactory;
import ua.foxminded.yakovlev.university.entity.Course;
import ua.foxminded.yakovlev.university.entity.Lecturer;
import ua.foxminded.yakovlev.university.entity.Position;
import ua.foxminded.yakovlev.university.entity.TimetableRecord;
import ua.foxminded.yakovlev.university.mapper.TimetableRecordMapper;
import ua.foxminded.yakovlev.university.testutil.TestDatabaseGenerator;
import ua.foxminded.yakovlev.university.util.FileReader;

class TimetableRecordDaoImplTest {

	private static TestDatabaseGenerator generator;
	private static TimetableRecordDaoImpl dao;

	@BeforeAll
	static void initTestCase() {
		FileReader fileReader = new FileReader();
		JdbcTemplateFactory jdbcTemplateFactory = new JdbcTemplateFactory("university_db");
		JdbcTemplate jdbcTemplate = jdbcTemplateFactory.getJdbcTemplate();
		RowMapper<TimetableRecord> rowMapper = new TimetableRecordMapper();
		ScriptExecutor scriptExecutor = new ScriptExecutor(jdbcTemplate);
		generator = new TestDatabaseGenerator(fileReader, scriptExecutor);
		dao = new TimetableRecordDaoImpl(jdbcTemplate, rowMapper);
	}

	@BeforeEach
	void initDB() {
		generator.generate();
	}
	
	@Test
	void findAllShouldReturnCertainListOfTimetableRecords() {
		
		List<TimetableRecord> expected = getAllTimetableRecords();
		List<TimetableRecord> actual = dao.findAll();
		
		assertEquals(expected, actual);
	}
	
	@Test
	void findByIdShouldReturnCertainTimetableRecord() {
		
		TimetableRecord expected = getAllTimetableRecords().get(1);
		TimetableRecord actual = dao.findById(2L);
		
		assertEquals(expected, actual);
	}
	
	@Test
	void deleteShouldDeleteCertainTimetableRecord() {
		
		List<TimetableRecord> expected = getAllTimetableRecords();
		expected.remove(2);
		
		dao.delete(3L);
		
		List<TimetableRecord> actual = dao.findAll();
		
		assertEquals(expected, actual);
	}
	
	@Test
	void findByPeriodOfTimeShouldReturnCertainTimetableRecordList() {
		
		List<TimetableRecord> expected = getAllTimetableRecords();
		expected.remove(2);
		expected.remove(0);
		
		LocalDateTime firstDate = LocalDateTime.parse("2020-10-16T09:30:00");
		LocalDateTime secondDate = LocalDateTime.parse("2020-10-16T11:30:00");
		
		List<TimetableRecord> actual = dao.findByPeriodOfTime(firstDate, secondDate);
		
		assertEquals(expected, actual);
	}
	
	@Test
	void saveShouldSaveCertainTimetableRecord() {
		
		List<TimetableRecord> expected = getAllTimetableRecords();
		TimetableRecord newTimetableRecord = getTimetableRecord(7L, "INSTRUCTOR", 6L, 1L, "Андрей", "Аксенов", 3L, "Музыка", "Основы музыкальной грамоты", 4L, "2020-10-16T15:00:00");
		expected.add(newTimetableRecord);
		TimetableRecord timetableRecordToAdd = getTimetableRecord(7L, "INSTRUCTOR", 6L, 1L, "Андрей", "Аксенов", 3L, "Музыка", "Основы музыкальной грамоты", 0L, "2020-10-16T15:00:00");
		
		dao.save(timetableRecordToAdd);
		
		List<TimetableRecord> actual = dao.findAll();
		
		assertEquals(expected, actual);
	}
	
	@Test
	void saveShouldReturnJustSavedTimetableRecord() {
		
		TimetableRecord timetableRecordToAdd = getTimetableRecord(7L, "INSTRUCTOR", 6L, 1L, "Андрей", "Аксенов", 3L, "Музыка", "Основы музыкальной грамоты", 0L, "2020-10-16T15:00:00");
		
		TimetableRecord expected = getTimetableRecord(7L, "INSTRUCTOR", 6L, 1L, "Андрей", "Аксенов", 3L, "Музыка", "Основы музыкальной грамоты", 4L, "2020-10-16T15:00:00");
		TimetableRecord actual = dao.save(timetableRecordToAdd);		
		
		assertEquals(expected, actual);
	}
	
	@Test
	void updateShouldUpdateCertainFieldsOfTimetableRecord() {
		
		TimetableRecord expected = getAllTimetableRecords().get(0);
		expected.setDate(LocalDateTime.parse("2020-10-17T09:00:00"));
				
		dao.update(expected);
		
		TimetableRecord actual = dao.findById(expected.getId());
		
		assertEquals(expected, actual);
	}
	
	@Test
	void updateShouldReturnUpdatedTimetableRecord() {
		
		TimetableRecord expected = getAllTimetableRecords().get(0);
		expected.setDate(LocalDateTime.parse("2020-10-17T09:00:00"));
				
		dao.update(expected);
		TimetableRecord actual = dao.findById(expected.getId());
		
		assertEquals(expected, actual);
	}
	
	List<TimetableRecord> getAllTimetableRecords() {
		
		List<TimetableRecord> allTimetableRecords = new ArrayList<>();
		
		allTimetableRecords.add(getTimetableRecord(7L, "INSTRUCTOR", 6L, 1L, "Андрей", "Аксенов", 3L, "Музыка", "Основы музыкальной грамоты", 1L, "2020-10-16T09:00:00"));
		allTimetableRecords.add(getTimetableRecord(7L, "INSTRUCTOR", 7L, 2L, "Мирон", "Давыдов", 2L, "Физика", "Общий курс физики", 2L, "2020-10-16T10:00:00"));
		allTimetableRecords.add(getTimetableRecord(8L, "RESEARCH_ASSOCIATE", 8L, 3L, "Владимир", "Ойстрах", 1L, "Математика", "Общий курс математики", 3L, "2020-10-16T12:00:00"));
		
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
		String timetableRecordTimestamp) {
		
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
		timetableRecord.setGroupList(new ArrayList<>());
		
		return timetableRecord;
	}
}
