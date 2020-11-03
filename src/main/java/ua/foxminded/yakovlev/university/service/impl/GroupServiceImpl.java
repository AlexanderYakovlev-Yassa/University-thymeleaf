package ua.foxminded.yakovlev.university.service.impl;

import org.springframework.stereotype.Component;

import ua.foxminded.yakovlev.university.dao.GroupDao;
import ua.foxminded.yakovlev.university.entity.Group;
import ua.foxminded.yakovlev.university.service.GroupService;

@Component
public class GroupServiceImpl extends AbstractService<Group, Long> implements GroupService {

	private final GroupDao dao;
	
	public GroupServiceImpl(GroupDao groupDao)  {
		super(groupDao);
		this.dao = groupDao;
	}
	
	@Override
	public Group findGroupByName(String groupName) {		
		return dao.findGroupByName(groupName);
	}
}
