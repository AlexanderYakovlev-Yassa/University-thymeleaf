package ua.foxminded.yakovlev.university.service;

import java.util.List;

public interface EntityService<E, ID> {

	E save(E entity);
	E findById(ID id);
	List<E> findAll();
	E update(E entity);
	void delete(ID id);
}
