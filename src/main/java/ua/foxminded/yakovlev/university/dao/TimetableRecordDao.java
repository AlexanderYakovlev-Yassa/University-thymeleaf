package ua.foxminded.yakovlev.university.dao;

import java.time.LocalDateTime;
import java.util.List;

import ua.foxminded.yakovlev.university.entity.TimetableRecord;
import ua.foxminded.yakovlev.university.exception.DaoConstrainException;
import ua.foxminded.yakovlev.university.exception.DaoNotFoundException;

public interface TimetableRecordDao extends EntityDao<TimetableRecord, Long> {

	List<TimetableRecord> findByPeriodOfTime(LocalDateTime periodStart, LocalDateTime periodFinish) throws DaoNotFoundException;
	
	List<TimetableRecord> findByLecturer(Long lecturerId, LocalDateTime periodStart, LocalDateTime periodFinish) throws DaoNotFoundException;
	List<TimetableRecord> findByStudent(Long studentId, LocalDateTime periodStart, LocalDateTime periodFinish) throws DaoNotFoundException;
	
	TimetableRecord addGroup(Long groupId, Long timetableId) throws DaoNotFoundException, DaoConstrainException;
	TimetableRecord removeGroup(Long groupId, Long timetableId) throws DaoNotFoundException;
}
