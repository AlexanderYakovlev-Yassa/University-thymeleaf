package ua.foxminded.yakovlev.university.service;

import ua.foxminded.yakovlev.university.entity.Position;
import ua.foxminded.yakovlev.university.exception.ServiceNotFoundException;

public interface PositionService extends EntityService<Position, Long>{

	Position findPositionByName(String positionName) throws ServiceNotFoundException;
}
