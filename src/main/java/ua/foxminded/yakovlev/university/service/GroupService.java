package ua.foxminded.yakovlev.university.service;

import ua.foxminded.yakovlev.university.entity.Group;
import ua.foxminded.yakovlev.university.exception.ServiceNotFoundException;

public interface GroupService extends EntityService<Group, Long> {
	
	Group findGroupByName(String groupName) throws ServiceNotFoundException;
}
