package ua.foxminded.yakovlev.university.dao;

import java.util.List;

import ua.foxminded.yakovlev.university.entity.Group;

public interface GroupDao extends EntityDao<Group, Long> {

	Group findGroupByName(String groupName);
}
