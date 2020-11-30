package ua.foxminded.yakovlev.university.service.impl;

import java.util.List;

import org.springframework.stereotype.Component;

import ua.foxminded.yakovlev.university.entity.Lecturer;
import ua.foxminded.yakovlev.university.jpaDao.LecturerRepository;
import ua.foxminded.yakovlev.university.service.LecturerService;

@Component
public class LecturerServiceImpl extends AbstractServiceJpa<Lecturer, Long> implements LecturerService {

	private final LecturerRepository dao;
	
	public LecturerServiceImpl(LecturerRepository lecturerDao)  {
		super(lecturerDao);
		this.dao = lecturerDao;
	}

	@Override
	public List<Lecturer> findByPositionId(Long positionId) {
		return dao.findByPositionId(positionId);
	}
}
