package ua.foxminded.yakovlev.university.service;

import java.time.LocalDateTime;
import java.util.List;

import ua.foxminded.yakovlev.university.entity.TimetableRecord;

public interface TimetableRecordService extends EntityService<TimetableRecord, Long>{

	List<TimetableRecord> findByPeriodOfTime(LocalDateTime periodStart, LocalDateTime periodFinish);
	
	List<TimetableRecord> findByLecturer(Long lecturerId, LocalDateTime periodStart, LocalDateTime periodFinish);
	List<TimetableRecord> findByStudent(Long studentId, LocalDateTime periodStart, LocalDateTime periodFinish);
	
	TimetableRecord addGroup(Long groupId, Long timetableId);
	TimetableRecord removeGroup(Long studentId, Long timetableId);
}
