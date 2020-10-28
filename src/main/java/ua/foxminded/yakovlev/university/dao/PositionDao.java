package ua.foxminded.yakovlev.university.dao;

import ua.foxminded.yakovlev.university.entity.Position;

public interface PositionDao extends EntityDao<Position, Long> {

	Position findPositionByName(String positionName);
}
