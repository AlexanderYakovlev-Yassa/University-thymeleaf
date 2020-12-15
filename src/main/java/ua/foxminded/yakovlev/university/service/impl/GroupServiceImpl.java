package ua.foxminded.yakovlev.university.service.impl;

import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import ua.foxminded.yakovlev.university.entity.Group;
import ua.foxminded.yakovlev.university.repository.GroupRepository;
import ua.foxminded.yakovlev.university.service.GroupService;

@Service
@Transactional
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
