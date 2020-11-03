package ua.foxminded.yakovlev.university.service.impl;

import java.time.LocalDateTime;
import java.util.List;
import org.springframework.stereotype.Component;

import ua.foxminded.yakovlev.university.dao.TimetableRecordDao;
import ua.foxminded.yakovlev.university.entity.TimetableRecord;
import ua.foxminded.yakovlev.university.service.TimetableRecordService;

@Component
public class TimetableRecordServiceImpl extends AbstractService<TimetableRecord, Long> implements TimetableRecordService {

	private final TimetableRecordDao dao;
	
	public TimetableRecordServiceImpl(TimetableRecordDao timetableRecordDao)  {
		super(timetableRecordDao);
		this.dao = timetableRecordDao;
	}

	@Override
	public List<TimetableRecord> findByPeriodOfTime(LocalDateTime periodStart, LocalDateTime periodFinish) {		
		return dao.findByPeriodOfTime(periodStart, periodFinish);
	}

	@Override
	public TimetableRecord addGroup(Long groupId, Long timetableId) {		
		return dao.addGroup(groupId, timetableId);
	}

	@Override
	public TimetableRecord removeGroup(Long groupId, Long timetableId) {		
		return dao.removeGroup(groupId, timetableId);
	}

	@Override
	public List<TimetableRecord> findByLecturer(Long lecturerId, LocalDateTime periodStart, LocalDateTime periodFinish) {		
		return dao.findByLecturer(lecturerId, periodStart, periodFinish);
	}

	@Override
	public List<TimetableRecord> findByStudent(Long studentId, LocalDateTime periodStart, LocalDateTime periodFinish) {
		return dao.findByStudent(studentId, periodStart, periodFinish);
	}
}
