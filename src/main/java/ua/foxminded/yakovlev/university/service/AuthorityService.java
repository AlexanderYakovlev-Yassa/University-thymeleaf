package ua.foxminded.yakovlev.university.service;

import ua.foxminded.yakovlev.university.entity.Authority;

public interface AuthorityService extends EntityService<Authority, Long>{

	Authority findAuthorityByName(String name);
}
