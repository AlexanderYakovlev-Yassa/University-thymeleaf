package ua.foxminded.yakovlev.university.repository;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNotNull;

import java.time.LocalDateTime;
import java.util.List;
import java.util.stream.Collectors;

import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest;
import org.springframework.test.context.jdbc.Sql;

import ua.foxminded.yakovlev.university.entity.Group;
import ua.foxminded.yakovlev.university.entity.TimetableRecord;

@DataJpaTest
class TimetableRepositoryIntegrationTest {
	
    @Autowired
    protected TimetableRecordRepository repository;
    
    @Autowired
    protected GroupRepository groupRepository;
    
    @Test
    @Sql({
    	"insertPersons.sql",
    	"insertPositions.sql",
    	"insertLecturers.sql",
    	"insertCourses.sql",
    	"insertGroups.sql",
    	"insertTimetableRecords.sql",
    	"insertTimetableGroups.sql"})
    void findAllShoudReturnTimetableRecordList() {     	
    	
    	List<TimetableRecord> recordList = repository.findAll();//.stream().forEach(System.out::println);   
    	assertEquals(9, recordList.size());
    	assertNotNull(recordList.get(0).getDate());
    	assertNotNull(recordList.get(0).getLecturer());
    	assertNotNull(recordList.get(0).getCourse());
    }

    @Test
    @Sql({
    	"insertPersons.sql",
    	"insertPositions.sql",
    	"insertLecturers.sql",
    	"insertCourses.sql",
    	"insertGroups.sql",
    	"insertTimetableRecords.sql",
    	"insertTimetableGroups.sql"})
    void findByPeriodOfTimeShoudReturnTimetableRecordList() {     
    	
    	String start = "2020-10-16T00:00:00";
		String end = "2020-10-17T00:00:00";
		LocalDateTime startDate = LocalDateTime.parse(start);
		LocalDateTime endDate = LocalDateTime.parse(end);
    	 
    	List<TimetableRecord> expected = repository.findAll().stream()
    			.filter(t -> startDate.isBefore(t.getDate()))
    			.filter(t -> endDate.isAfter(t.getDate()))
    			.collect(Collectors.toList());
    	
    	List<TimetableRecord> actual = repository.findByPeriodOfTime(startDate, endDate);
    	
    	assertEquals(expected, actual);
    }

    @Test
    @Sql({
    	"insertPersons.sql",
    	"insertPositions.sql",
    	"insertLecturers.sql",
    	"insertCourses.sql",
    	"insertGroups.sql",
    	"insertTimetableRecords.sql",
    	"insertTimetableGroups.sql"})
    void findByLecturerShoudReturnTimetableRecordList() {     
    	
    	String start = "2020-10-16T00:00:00";
		String end = "2020-10-18T00:00:00";
		LocalDateTime startDate = LocalDateTime.parse(start);
		LocalDateTime endDate = LocalDateTime.parse(end);
    	 
    	List<TimetableRecord> expected = repository.findAll().stream()
    			.filter(t -> startDate.isBefore(t.getDate()))
    			.filter(t -> endDate.isAfter(t.getDate()))
    			.filter(t -> t.getLecturer().getFirstName().equals("Андрей"))
    			.filter(t -> t.getLecturer().getLastName().equals("Аксенов"))
    			.collect(Collectors.toList());
    	
    	Long lecturerId = expected.get(0).getLecturer().getPersonId();
    	
    	List<TimetableRecord> actual = repository.findByLecturer(lecturerId, startDate, endDate);
    	
    	assertEquals(expected, actual);
    }

    @Test
    @Sql({
    	"insertPersons.sql",
    	"insertPositions.sql",
    	"insertLecturers.sql",
    	"insertCourses.sql",
    	"insertGroups.sql",
    	"insertTimetableRecords.sql",
    	"insertTimetableGroups.sql"})
    void findByCourseShoudReturnTimetableRecordList() {     
    	
    	String start = "2020-10-16T00:00:00";
		String end = "2020-10-18T00:00:00";
		LocalDateTime startDate = LocalDateTime.parse(start);
		LocalDateTime endDate = LocalDateTime.parse(end);
		
		Group group = groupRepository.findGroupByName("aa-01");
    	 
    	List<TimetableRecord> expected = repository.findAll().stream()
    			.filter(t -> startDate.isBefore(t.getDate()))
    			.filter(t -> endDate.isAfter(t.getDate()))
    			.filter(t -> t.getGroupList()!=null)
    			.filter(t -> t.getGroupList().contains(group))
    			.collect(Collectors.toList());
    	
    	List<TimetableRecord> actual = repository.findByGroup(group.getId(), startDate, endDate);
    	
    	assertEquals(expected, actual);
    }
}