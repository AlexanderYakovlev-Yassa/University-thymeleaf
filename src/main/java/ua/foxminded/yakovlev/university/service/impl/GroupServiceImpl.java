package ua.foxminded.yakovlev.university.service.impl;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import ua.foxminded.yakovlev.university.dao.GroupDao;
import ua.foxminded.yakovlev.university.entity.Group;
import ua.foxminded.yakovlev.university.service.GroupService;
import ua.foxminded.yakovlev.university.validator.EntityValidator;

@Component
public class GroupServiceImpl implements GroupService {

	private final GroupDao dao;
	private final EntityValidator<Group> validator;
	
	@Autowired
	public GroupServiceImpl(GroupDao groupDao, EntityValidator<Group> validator)  {
		this.dao = groupDao;
		this.validator = validator;
	}
	
	@Override
	public Group save(Group entity) {
		
		validator.check(entity);
		return dao.save(entity);
	}

	@Override
	public Group findById(Long id) {
		
		validator.checkId(id);
		return dao.findById(id);
	}

	@Override
	public List<Group> findAll() {

		return dao.findAll();
	}

	@Override
	public Group update(Group entity) {
		
		validator.check(entity);
		return dao.update(entity);
	}

	@Override
	public void delete(Long id) {
		validator.checkId(id);
		dao.delete(id);		
	}

	@Override
	public Group findGroupByName(String groupName) {		
		return dao.findGroupByName(groupName);
	}

	
}
