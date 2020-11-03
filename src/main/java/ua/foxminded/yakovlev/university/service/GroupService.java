package ua.foxminded.yakovlev.university.service;

import ua.foxminded.yakovlev.university.entity.Group;

public interface GroupService extends EntityService<Group, Long> {
	
	Group findGroupByName(String groupName);
}
