package ua.foxminded.yakovlev.university.service;

import ua.foxminded.yakovlev.university.entity.Position;

public interface PositionService extends EntityService<Position, Long>{

	Position findPositionByName(String positionName);
}
