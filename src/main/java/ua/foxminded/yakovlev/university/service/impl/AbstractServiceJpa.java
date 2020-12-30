package ua.foxminded.yakovlev.university.service.impl;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;

import lombok.AllArgsConstructor;
import ua.foxminded.yakovlev.university.exception.NotFoundException;
import ua.foxminded.yakovlev.university.service.EntityService;

@AllArgsConstructor
public abstract class AbstractServiceJpa<E, ID> implements EntityService<E, ID>{
	
	private JpaRepository<E, ID> dao;
		
	@Override
	public E save(E entity) {				
		return dao.save(entity);
	}

	@Override
	public E findById(ID id) {
		return dao.findById(id).orElseThrow(() -> new NotFoundException(getEntityName() + " with ID " + id + " is not found!"));
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
		dao.delete(findById(id));
	}
	
	protected abstract String getEntityName();
}