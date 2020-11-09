package ua.foxminded.yakovlev.university.dao;

import java.util.List;

import ua.foxminded.yakovlev.university.exception.DaoAlreadyExistsException;
import ua.foxminded.yakovlev.university.exception.DaoConstrainException;
import ua.foxminded.yakovlev.university.exception.DaoNotFoundException;

public interface EntityDao<E, ID> {

	E save(E entity) throws DaoNotFoundException, DaoAlreadyExistsException, DaoConstrainException;
	E findById(ID id) throws DaoNotFoundException;
	List<E> findAll() throws DaoNotFoundException;
	E update(E entity) throws DaoNotFoundException, DaoAlreadyExistsException;
	void delete(ID id) throws DaoConstrainException;
}
