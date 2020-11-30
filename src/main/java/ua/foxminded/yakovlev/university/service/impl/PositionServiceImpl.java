package ua.foxminded.yakovlev.university.service.impl;

import ua.foxminded.yakovlev.university.entity.Position;
import ua.foxminded.yakovlev.university.jpaDao.PositionRepository;
import ua.foxminded.yakovlev.university.service.PositionService;

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
