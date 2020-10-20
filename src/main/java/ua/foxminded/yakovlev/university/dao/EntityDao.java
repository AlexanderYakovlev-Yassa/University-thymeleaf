package ua.foxminded.yakovlev.university.dao;

import java.util.List;

import ua.foxminded.yakovlev.university.exception.DaoNotFoundException;

public interface EntityDao<E, ID> {

	void save(E entity);
	E findById(ID id);
	List<E> findAll();
	void update(E entity);
	void delete(ID id);
}
