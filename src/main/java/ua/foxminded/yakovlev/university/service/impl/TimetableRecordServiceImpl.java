package ua.foxminded.yakovlev.university.service.impl;

import java.time.LocalDateTime;
import java.util.List;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import ua.foxminded.yakovlev.university.entity.TimetableRecord;
import ua.foxminded.yakovlev.university.repository.TimetableRecordRepository;
import ua.foxminded.yakovlev.university.service.TimetableRecordService;

@Service
@Transactional
public class TimetableRecordServiceImpl extends AbstractServiceJpa<TimetableRecord, Long> implements TimetableRecordService {

	private static final String ENTITY_NAME = "Timetable record";
	
	private final TimetableRecordRepository dao;
	
	public TimetableRecordServiceImpl(TimetableRecordRepository timetableRecordRepository)  {
		super(timetableRecordRepository);
		this.dao = timetableRecordRepository;
	}

	@Override
	public List<TimetableRecord> findByPeriodOfTime(LocalDateTime periodStart, LocalDateTime periodFinish) {		
		return dao.findByPeriodOfTime(periodStart, periodFinish);
	}

	@Override
	public List<TimetableRecord> findByLecturer(Long lecturerId, LocalDateTime periodStart, LocalDateTime periodFinish) {		
		return dao.findByLecturer(lecturerId, periodStart, periodFinish);
	}

	@Override
	public List<TimetableRecord> findByGroup(Long studentId, LocalDateTime periodStart, LocalDateTime periodFinish) {
		return dao.findByGroup(studentId, periodStart, periodFinish);
	}

	@Override
	protected String getEntityName() {
		
		return ENTITY_NAME;
	}

	@Override
	protected Long getIdentifire(TimetableRecord record) {
		return record.getId();
	}
}
