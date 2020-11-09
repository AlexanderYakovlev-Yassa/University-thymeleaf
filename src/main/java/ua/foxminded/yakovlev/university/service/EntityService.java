package ua.foxminded.yakovlev.university.service;

import java.util.List;

import ua.foxminded.yakovlev.university.exception.ServiceAlreadyExistsException;
import ua.foxminded.yakovlev.university.exception.ServiceConstrainException;
import ua.foxminded.yakovlev.university.exception.ServiceNotFoundException;

public interface EntityService<E, ID> {

	E save(E entity) throws ServiceNotFoundException, ServiceAlreadyExistsException, ServiceConstrainException;
	E findById(ID id) throws ServiceNotFoundException;
	List<E> findAll() throws ServiceNotFoundException;
	E update(E entity) throws ServiceNotFoundException, ServiceAlreadyExistsException;
	void delete(ID id) throws ServiceConstrainException;
}
