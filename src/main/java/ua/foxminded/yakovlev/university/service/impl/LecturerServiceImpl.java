package ua.foxminded.yakovlev.university.service.impl;

import java.util.List;

import org.springframework.stereotype.Component;

import ua.foxminded.yakovlev.university.dao.LecturerDao;
import ua.foxminded.yakovlev.university.entity.Lecturer;
import ua.foxminded.yakovlev.university.service.LecturerService;
import ua.foxminded.yakovlev.university.validator.LecturerValidator;

@Component
public class LecturerServiceImpl extends AbstractService<Lecturer, Long> implements LecturerService {

	private final LecturerDao dao;
	
	public LecturerServiceImpl(LecturerDao lecturerDao, LecturerValidator lecturerValidator)  {
		super(lecturerDao, lecturerValidator);
		this.dao = lecturerDao;
	}

	@Override
	public List<Lecturer> findByPositionId(Long positionId) {
		return dao.findByPositionId(positionId);
	}
}
