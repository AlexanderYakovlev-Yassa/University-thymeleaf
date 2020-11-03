package ua.foxminded.yakovlev.university.service.impl;

import java.util.List;

import ua.foxminded.yakovlev.university.dao.EntityDao;
import ua.foxminded.yakovlev.university.service.EntityService;

public class AbstractService<E, ID> implements EntityService<E, ID>{
	private EntityDao<E, ID> dao;
	
	public AbstractService(EntityDao<E, ID> daoEntity) {
		this.dao = daoEntity;
	}
	
	@Override
	public E save(E entity) {
		
		return dao.save(entity);
	}

	@Override
	public E findById(ID id) {
		
		return dao.findById(id);
	}

	@Override
	public List<E> findAll() {

		return dao.findAll();
	}

	@Override
	public E update(E entity) {
		
		return dao.update(entity);
	}

	@Override
	public void delete(ID id) {
		dao.delete(id);		
	}
}
