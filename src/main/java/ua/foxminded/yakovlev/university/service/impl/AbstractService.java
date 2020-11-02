package ua.foxminded.yakovlev.university.service.impl;

import java.util.List;

import ua.foxminded.yakovlev.university.dao.EntityDao;
import ua.foxminded.yakovlev.university.service.EntityService;
import ua.foxminded.yakovlev.university.validator.EntityValidator;

public class AbstractService<E, ID> implements EntityService<E, ID>{
	
	private EntityValidator<E, ID> validator;
	private EntityDao<E, ID> dao;
	
	public AbstractService(EntityDao<E, ID> daoEntity, EntityValidator<E, ID> validator) {
		this.dao = daoEntity;
		this.validator = validator;
	}
	
	@Override
	public E save(E entity) {
		
		validator.check(entity);
		return dao.save(entity);
	}

	@Override
	public E findById(ID id) {
		
		validator.checkId(id);
		return dao.findById(id);
	}

	@Override
	public List<E> findAll() {

		return dao.findAll();
	}

	@Override
	public E update(E entity) {
		
		validator.check(entity);
		return dao.update(entity);
	}

	@Override
	public void delete(ID id) {
		validator.checkId(id);
		dao.delete(id);		
	}
}
