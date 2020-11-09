package ua.foxminded.yakovlev.university.service.impl;

import java.util.List;

import org.springframework.transaction.annotation.Transactional;

import ua.foxminded.yakovlev.university.dao.EntityDao;
import ua.foxminded.yakovlev.university.exception.DaoAlreadyExistsException;
import ua.foxminded.yakovlev.university.exception.DaoConstrainException;
import ua.foxminded.yakovlev.university.exception.DaoNotFoundException;
import ua.foxminded.yakovlev.university.exception.ServiceAlreadyExistsException;
import ua.foxminded.yakovlev.university.exception.ServiceConstrainException;
import ua.foxminded.yakovlev.university.exception.ServiceNotFoundException;
import ua.foxminded.yakovlev.university.service.EntityService;

public class AbstractService<E, ID> implements EntityService<E, ID>{
	private EntityDao<E, ID> dao;
	
	public AbstractService(EntityDao<E, ID> daoEntity) {
		this.dao = daoEntity;
	}
	
	@Override
	@Transactional
	public E save(E entity) throws ServiceNotFoundException, ServiceAlreadyExistsException, ServiceConstrainException {
		
		try {
			return dao.save(entity);
		} catch (DaoNotFoundException e) {
			throw new ServiceNotFoundException("Entity not found");
		} catch (DaoAlreadyExistsException e) {
			throw new ServiceAlreadyExistsException("Entity already exists");
		} catch (DaoConstrainException e) {
			throw new ServiceConstrainException("There is a constrain preventing saving");
		}
	}

	@Override
	public E findById(ID id) throws ServiceNotFoundException {
		
		try {
			return dao.findById(id);
		} catch (DaoNotFoundException e) {
			throw new ServiceNotFoundException("Entity not found");
		}
	}

	@Override
	public List<E> findAll() throws ServiceNotFoundException {

		try {
			return dao.findAll();
		} catch (DaoNotFoundException e) {
			throw new ServiceNotFoundException("Entites not found");
		}
	}

	@Override
	public E update(E entity) throws ServiceNotFoundException, ServiceAlreadyExistsException {
		
		try {
			return dao.update(entity);
		} catch (DaoNotFoundException e) {
			throw new ServiceNotFoundException("Entity not found");
		} catch (DaoAlreadyExistsException e) {
			throw new ServiceAlreadyExistsException("Entity already exists");
		}
	}

	@Override
	public void delete(ID id) throws ServiceConstrainException {
		try {
			dao.delete(id);
		} catch (DaoConstrainException e) {
			throw new ServiceConstrainException("There is a constrain preventing deletion");
		}
	}
}
