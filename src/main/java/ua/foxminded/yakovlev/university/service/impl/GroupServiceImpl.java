package ua.foxminded.yakovlev.university.service.impl;

import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import ua.foxminded.yakovlev.university.entity.Group;
import ua.foxminded.yakovlev.university.repository.GroupRepository;
import ua.foxminded.yakovlev.university.service.GroupService;

@Service
@Transactional
public class GroupServiceImpl extends AbstractServiceJpa<Group, Long> implements GroupService {

	private static final String ENTITY_NAME = "Group";
	
	private final GroupRepository dao;
	
	public GroupServiceImpl(GroupRepository groupDao) {
		super(groupDao);
		this.dao = groupDao;
	}
	
	@Override
	public Group findGroupByName(String groupName) {		
		return dao.findGroupByName(groupName);
	}

	@Override
	protected String getEntityName() {
		
		return ENTITY_NAME;
	}
}
