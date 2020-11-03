package ua.foxminded.yakovlev.university.service.impl;

import java.util.List;

import org.springframework.stereotype.Component;

import ua.foxminded.yakovlev.university.dao.LecturerDao;
import ua.foxminded.yakovlev.university.entity.Lecturer;
import ua.foxminded.yakovlev.university.service.LecturerService;

@Component
public class LecturerServiceImpl extends AbstractService<Lecturer, Long> implements LecturerService {

	private final LecturerDao dao;
	
	public LecturerServiceImpl(LecturerDao lecturerDao)  {
		super(lecturerDao);
		this.dao = lecturerDao;
	}

	@Override
	public List<Lecturer> findByPositionId(Long positionId) {
		return dao.findByPositionId(positionId);
	}
}
