package ua.foxminded.yakovlev.university.service.impl;

import java.time.LocalDateTime;
import java.util.List;
import org.springframework.stereotype.Component;

import ua.foxminded.yakovlev.university.dao.TimetableRecordDao;
import ua.foxminded.yakovlev.university.entity.TimetableRecord;
import ua.foxminded.yakovlev.university.exception.DaoConstrainException;
import ua.foxminded.yakovlev.university.exception.DaoNotFoundException;
import ua.foxminded.yakovlev.university.exception.ServiceConstrainException;
import ua.foxminded.yakovlev.university.exception.ServiceNotFoundException;
import ua.foxminded.yakovlev.university.service.TimetableRecordService;

@Component
public class TimetableRecordServiceImpl extends AbstractService<TimetableRecord, Long> implements TimetableRecordService {
	
	private static final String NOT_FOUD = "Entities not found";

	private final TimetableRecordDao dao;
	
	public TimetableRecordServiceImpl(TimetableRecordDao timetableRecordDao)  {
		super(timetableRecordDao);
		this.dao = timetableRecordDao;
	}

	@Override
	public List<TimetableRecord> findByPeriodOfTime(LocalDateTime periodStart, LocalDateTime periodFinish) throws ServiceNotFoundException {		
		
		try {
			return dao.findByPeriodOfTime(periodStart, periodFinish);
		} catch (DaoNotFoundException e) {
			throw new ServiceNotFoundException(NOT_FOUD);
		}
	}

	@Override
	public TimetableRecord addGroup(Long groupId, Long timetableId) throws ServiceNotFoundException, ServiceConstrainException {		
		try {
			return dao.addGroup(groupId, timetableId);
		} catch (DaoNotFoundException e) {
			throw new ServiceNotFoundException(NOT_FOUD);
		} catch (DaoConstrainException e) {
			throw new ServiceConstrainException("There is a constrain preventing group adding");
		}
	}

	@Override
	public TimetableRecord removeGroup(Long groupId, Long timetableId) throws ServiceNotFoundException {		
		try {
			return dao.removeGroup(groupId, timetableId);
		} catch (DaoNotFoundException e) {
			throw new ServiceNotFoundException(NOT_FOUD);
		}
	}

	@Override
	public List<TimetableRecord> findByLecturer(Long lecturerId, LocalDateTime periodStart, LocalDateTime periodFinish) throws ServiceNotFoundException {		
		try {
			return dao.findByLecturer(lecturerId, periodStart, periodFinish);
		} catch (DaoNotFoundException e) {
			throw new ServiceNotFoundException(NOT_FOUD);
		}
	}

	@Override
	public List<TimetableRecord> findByStudent(Long studentId, LocalDateTime periodStart, LocalDateTime periodFinish) throws ServiceNotFoundException {
		try {
			return dao.findByStudent(studentId, periodStart, periodFinish);
		} catch (DaoNotFoundException e) {
			throw new ServiceNotFoundException(NOT_FOUD);
		}
	}
}
