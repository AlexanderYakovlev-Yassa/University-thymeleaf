package ua.foxminded.yakovlev.university.service;

import java.util.List;

import ua.foxminded.yakovlev.university.entity.Group;

public interface GroupService {
		
	Group save(Group entity);
	Group findById(Long id);
	List<Group> findAll();
	Group update(Group entity);
	void delete(Long id);
	
	Group findGroupByName(String groupName);
}
