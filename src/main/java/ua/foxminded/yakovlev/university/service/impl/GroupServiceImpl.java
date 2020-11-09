package ua.foxminded.yakovlev.university.service.impl;

import org.springframework.stereotype.Component;

import ua.foxminded.yakovlev.university.dao.GroupDao;
import ua.foxminded.yakovlev.university.entity.Group;
import ua.foxminded.yakovlev.university.exception.DaoNotFoundException;
import ua.foxminded.yakovlev.university.exception.ServiceNotFoundException;
import ua.foxminded.yakovlev.university.service.GroupService;

@Component
public class GroupServiceImpl extends AbstractService<Group, Long> implements GroupService {

	private final GroupDao dao;
	
	public GroupServiceImpl(GroupDao groupDao)  {
		super(groupDao);
		this.dao = groupDao;
	}
	
	@Override
	public Group findGroupByName(String groupName) throws ServiceNotFoundException {		
		
		try {
			return dao.findGroupByName(groupName);
		} catch (DaoNotFoundException e) {
			throw new ServiceNotFoundException("Entity not found");
		}
	}
}
