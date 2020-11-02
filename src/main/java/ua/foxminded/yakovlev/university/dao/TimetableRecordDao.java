package ua.foxminded.yakovlev.university.dao;

import java.time.LocalDateTime;
import java.util.List;

import ua.foxminded.yakovlev.university.entity.TimetableRecord;

public interface TimetableRecordDao extends EntityDao<TimetableRecord, Long> {

	List<TimetableRecord> findByPeriodOfTime(LocalDateTime periodStart, LocalDateTime periodFinish);
	
	List<TimetableRecord> findByPeriodOfTimeAndLecturerId(LocalDateTime periodStart, LocalDateTime periodFinish, Long lecturerId);
	List<TimetableRecord> findByPeriodOfTimeAndStudentId(LocalDateTime periodStart, LocalDateTime periodFinish, Long studentId);
	
	TimetableRecord addGroupToTimeable(Long timetableId, Long groupId);
	TimetableRecord removeGroupFromTimeable(Long timetableId, Long groupId);
}
