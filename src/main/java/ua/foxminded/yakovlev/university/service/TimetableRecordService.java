package ua.foxminded.yakovlev.university.service;

import java.time.LocalDateTime;
import java.util.List;

import ua.foxminded.yakovlev.university.entity.TimetableRecord;
import ua.foxminded.yakovlev.university.exception.ServiceConstrainException;
import ua.foxminded.yakovlev.university.exception.ServiceNotFoundException;

public interface TimetableRecordService extends EntityService<TimetableRecord, Long>{

	List<TimetableRecord> findByPeriodOfTime(LocalDateTime periodStart, LocalDateTime periodFinish) throws ServiceNotFoundException;
	
	List<TimetableRecord> findByLecturer(Long lecturerId, LocalDateTime periodStart, LocalDateTime periodFinish) throws ServiceNotFoundException;
	List<TimetableRecord> findByStudent(Long studentId, LocalDateTime periodStart, LocalDateTime periodFinish) throws ServiceNotFoundException;
	
	TimetableRecord addGroup(Long groupId, Long timetableId) throws ServiceNotFoundException, ServiceConstrainException;
	TimetableRecord removeGroup(Long studentId, Long timetableId) throws ServiceNotFoundException;
}
