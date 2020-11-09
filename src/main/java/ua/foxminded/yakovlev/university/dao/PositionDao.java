package ua.foxminded.yakovlev.university.dao;

import ua.foxminded.yakovlev.university.entity.Position;
import ua.foxminded.yakovlev.university.exception.DaoNotFoundException;

public interface PositionDao extends EntityDao<Position, Long> {

	Position findPositionByName(String positionName) throws DaoNotFoundException;
}
