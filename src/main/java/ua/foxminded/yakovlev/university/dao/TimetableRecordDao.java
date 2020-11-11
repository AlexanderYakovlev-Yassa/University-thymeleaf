package ua.foxminded.yakovlev.university.dao;

import java.time.LocalDateTime;
import java.util.List;

import ua.foxminded.yakovlev.university.entity.TimetableRecord;

public interface TimetableRecordDao extends EntityDao<TimetableRecord, Long> {

	List<TimetableRecord> findByPeriodOfTime(LocalDateTime periodStart, LocalDateTime periodFinish);
	
	List<TimetableRecord> findByLecturer(Long lecturerId, LocalDateTime periodStart, LocalDateTime periodFinish);
	List<TimetableRecord> findByStudent(Long studentId, LocalDateTime periodStart, LocalDateTime periodFinish);
	
	TimetableRecord addGroup(Long groupId, Long timetableId);
	TimetableRecord removeGroup(Long groupId, Long timetableId);
}
