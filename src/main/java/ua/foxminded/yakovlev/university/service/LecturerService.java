package ua.foxminded.yakovlev.university.service;

import java.util.List;

import ua.foxminded.yakovlev.university.entity.Lecturer;
import ua.foxminded.yakovlev.university.exception.ServiceNotFoundException;

public interface LecturerService extends EntityService<Lecturer, Long>{

	List<Lecturer> findByPositionId(Long positionId) throws ServiceNotFoundException;
}
