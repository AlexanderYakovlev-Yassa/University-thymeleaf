package ua.foxminded.yakovlev.university.repository;

import static org.junit.jupiter.api.Assertions.*;

import java.time.LocalDateTime;
import java.util.List;
import java.util.stream.Collectors;

import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest;
import org.springframework.test.context.jdbc.Sql;

import ua.foxminded.yakovlev.university.entity.Group;
import ua.foxminded.yakovlev.university.entity.Lecturer;
import ua.foxminded.yakovlev.university.entity.TimetableRecord;

@DataJpaTest
@Sql({
	"/sqlscripts/insertPersons.sql",
	"/sqlscripts/insertPositions.sql",
	"/sqlscripts/insertLecturers.sql",
	"/sqlscripts/insertCourses.sql",
	"/sqlscripts/insertGroups.sql",
	"/sqlscripts/insertTimetableRecords.sql",
	"/sqlscripts/insertTimetableGroups.sql"})
class TimetableRepositoryIntegrationTest {
	
    @Autowired
    protected TimetableRecordRepository repository;
    
    @Autowired
    protected GroupRepository groupRepository;
    
    @Test
    void findAllShoudReturnTimetableRecordList() {     	
    	
    	List<TimetableRecord> recordList = repository.findAll();
    	assertEquals(9, recordList.size());
    	assertNotNull(recordList.get(0).getDate());
    	assertNotNull(recordList.get(0).getLecturer());
    	assertNotNull(recordList.get(0).getCourse());
    }

    @Test
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
    void findByLecturerShoudReturnTimetableRecordList() {     
    	
    	String start = "2020-10-16T00:00:00";
		String end = "2020-10-18T00:00:00";
		LocalDateTime startDate = LocalDateTime.parse(start);
		LocalDateTime endDate = LocalDateTime.parse(end);
		List<TimetableRecord> all = repository.findAll();
		
		if (all.isEmpty()) {		
			fail("There are no records in DB");
		}
		
		Lecturer lecturer = all.get(0).getLecturer();
    	 
    	List<TimetableRecord> expected = repository.findAll().stream()
    			.filter(t -> startDate.isBefore(t.getDate()))
    			.filter(t -> endDate.isAfter(t.getDate()))
    			.filter(t -> t.getLecturer().equals(lecturer))
    			.collect(Collectors.toList());
    	
    	Long lecturerId = expected.get(0).getLecturer().getPersonId();
    	
    	List<TimetableRecord> actual = repository.findByLecturer(lecturerId, startDate, endDate);
    	
    	assertEquals(expected, actual);
    }

    @Test
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