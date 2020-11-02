package ua.foxminded.yakovlev.university.service;

import java.time.LocalDateTime;
import java.util.List;

import ua.foxminded.yakovlev.university.entity.TimetableRecord;

public interface TimetableRecordService extends EntityService<TimetableRecord, Long>{

	List<TimetableRecord> findByPeriodOfTime(LocalDateTime periodStart, LocalDateTime periodFinish);
	
	List<TimetableRecord> findByPeriodOfTimeAndLecturerId(LocalDateTime periodStart, LocalDateTime periodFinish, Long lecturerId);
	List<TimetableRecord> findByPeriodOfTimeAndGroupId(LocalDateTime periodStart, LocalDateTime periodFinish, Long groupId);
	
	TimetableRecord addGroupToTimeable(Long timetableId, Long groupId);
	TimetableRecord removeGroupFromTimeable(Long timetableId, Long studentId);
}
