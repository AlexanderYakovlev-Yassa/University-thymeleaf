package ua.foxminded.yakovlev.university.service.impl;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;

import ua.foxminded.yakovlev.university.service.EntityService;

public class AbstractServiceJpa<E, ID> implements EntityService<E, ID>{
	private JpaRepository<E, ID> dao;
	
	public AbstractServiceJpa(JpaRepository<E, ID> daoEntity) {
		this.dao = daoEntity;
	}
	
	@Override
	public E save(E entity) {
		
		return dao.save(entity);
	}

	@Override
	public E findById(ID id) {
		
		return dao.getOne(id);
	}

	@Override
	public List<E> findAll() {

		return dao.findAll();
	}

	@Override
	public E update(E entity) {
		
		return dao.saveAndFlush(entity);
	}

	@Override
	public void delete(ID id) {
		dao.delete(findById(id));;		
	}
}