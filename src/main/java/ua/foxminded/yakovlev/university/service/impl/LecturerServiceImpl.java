package ua.foxminded.yakovlev.university.service.impl;

import java.util.List;

import javax.transaction.Transactional;

import org.springframework.stereotype.Service;

import ua.foxminded.yakovlev.university.entity.Lecturer;
import ua.foxminded.yakovlev.university.repository.LecturerRepository;
import ua.foxminded.yakovlev.university.service.LecturerService;

@Service
@Transactional
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
