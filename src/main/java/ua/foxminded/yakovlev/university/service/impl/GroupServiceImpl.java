package ua.foxminded.yakovlev.university.service.impl;

import org.springframework.stereotype.Component;

import ua.foxminded.yakovlev.university.entity.Group;
import ua.foxminded.yakovlev.university.jpaDao.GroupRepository;
import ua.foxminded.yakovlev.university.service.GroupService;

@Component
public class GroupServiceImpl extends AbstractServiceJpa<Group, Long> implements GroupService {

	private final GroupRepository dao;
	
	public GroupServiceImpl(GroupRepository groupDao) {
		super(groupDao);
		this.dao = groupDao;
	}
	
	@Override
	public Group findGroupByName(String groupName) {		
		return dao.findGroupByName(groupName);
	}
}
