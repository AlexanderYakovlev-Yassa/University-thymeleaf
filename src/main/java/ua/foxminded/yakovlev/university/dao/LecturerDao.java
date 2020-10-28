package ua.foxminded.yakovlev.university.dao;

import java.util.List;

import ua.foxminded.yakovlev.university.entity.Lecturer;

public interface LecturerDao extends EntityDao<Lecturer, Long> {

	List<Lecturer> findByPositionId(Long positionId);
}
