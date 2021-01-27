package ua.foxminded.yakovlev.university.service;

import ua.foxminded.yakovlev.university.entity.Role;

public interface RoleService extends EntityService<Role, Long>{

	void delete(Long roleId);
}
