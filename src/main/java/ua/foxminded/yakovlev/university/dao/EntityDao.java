package ua.foxminded.yakovlev.university.dao;

import java.util.List;

public interface EntityDao<E, ID> {

	E save(E entity);
	E findById(ID id);
	List<E> findAll();
	E update(E entity);
	void delete(ID id);
}
