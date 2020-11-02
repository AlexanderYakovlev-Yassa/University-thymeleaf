package ua.foxminded.yakovlev.university.service.impl;

import java.time.LocalDateTime;
import java.util.List;
import org.springframework.stereotype.Component;

import ua.foxminded.yakovlev.university.dao.TimetableRecordDao;
import ua.foxminded.yakovlev.university.entity.TimetableRecord;
import ua.foxminded.yakovlev.university.service.TimetableRecordService;
import ua.foxminded.yakovlev.university.validator.TimetableRecordValidator;

@Component
public class TimetableRecordServiceImpl extends AbstractService<TimetableRecord, Long> implements TimetableRecordService {

	private final TimetableRecordDao dao;
	
	public TimetableRecordServiceImpl(TimetableRecordDao timetableRecordDao, 
			TimetableRecordValidator timetableRecordValidator)  {
		super(timetableRecordDao, timetableRecordValidator);
		this.dao = timetableRecordDao;
	}

	@Override
	public List<TimetableRecord> findByPeriodOfTime(LocalDateTime periodStart, LocalDateTime periodFinish) {		
		return dao.findByPeriodOfTime(periodStart, periodFinish);
	}

	@Override
	public TimetableRecord addGroupToTimeable(Long timetableId, Long groupId) {		
		return dao.addGroupToTimeable(timetableId, groupId);
	}

	@Override
	public TimetableRecord removeGroupFromTimeable(Long timetableId, Long groupId) {		
		return dao.removeGroupFromTimeable(timetableId, groupId);
	}
}
