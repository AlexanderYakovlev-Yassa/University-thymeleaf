package ua.foxminded.yakovlev.university.service.impl;

import org.springframework.stereotype.Service;

import ua.foxminded.yakovlev.university.entity.Position;
import ua.foxminded.yakovlev.university.repository.PositionRepository;
import ua.foxminded.yakovlev.university.service.PositionService;

@Service
public class PositionServiceImpl extends AbstractServiceJpa<Position, Long> implements PositionService {
	
	private final PositionRepository dao;

	public PositionServiceImpl(PositionRepository positionDao) {
		super(positionDao);
		this.dao = positionDao;
	}

	@Override
	public Position findPositionByName(String positionName) {		
		return dao.findPositionByName(positionName);
	}
}
