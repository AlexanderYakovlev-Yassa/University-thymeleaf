package ua.foxminded.yakovlev.university.dao;

import ua.foxminded.yakovlev.university.entity.Group;
import ua.foxminded.yakovlev.university.exception.DaoNotFoundException;

public interface GroupDao extends EntityDao<Group, Long> {

	Group findGroupByName(String groupName) throws DaoNotFoundException;
}
