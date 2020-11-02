package ua.foxminded.yakovlev.university.service.impl;

import ua.foxminded.yakovlev.university.dao.PositionDao;
import ua.foxminded.yakovlev.university.entity.Position;
import ua.foxminded.yakovlev.university.service.PositionService;
import ua.foxminded.yakovlev.university.validator.PositionValidator;

public class PositionServiceImpl extends AbstractService<Position, Long> implements PositionService {
	
	private final PositionDao dao;

	public PositionServiceImpl(PositionDao positionDao, PositionValidator positionValidator) {
		super(positionDao, positionValidator);
		this.dao = positionDao;
	}

	@Override
	public Position findPositionByName(String positionName) {		
		return dao.findPositionByName(positionName);
	}
}
