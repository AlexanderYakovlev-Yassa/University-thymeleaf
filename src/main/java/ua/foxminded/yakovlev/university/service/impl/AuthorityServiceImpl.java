package ua.foxminded.yakovlev.university.service.impl;

import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import ua.foxminded.yakovlev.university.entity.Authority;
import ua.foxminded.yakovlev.university.repository.AuthorityRepository;
import ua.foxminded.yakovlev.university.service.AuthorityService;

@Service
@Transactional
public class AuthorityServiceImpl extends AbstractServiceJpa<Authority, Long> implements AuthorityService {

	private static final String ENTITY_NAME = "Authority";
	
	private final AuthorityRepository repository;
	
	public AuthorityServiceImpl(AuthorityRepository authorityRepository) {
		super(authorityRepository);
		this.repository = authorityRepository;
	}

	@Override
	public Authority findAuthorityByName(String name) {		
		return repository.findAuthorityByName(name);
	}

	@Override
	protected String getEntityName() {
		return ENTITY_NAME;
	}

	@Override
	protected Long getIdentifire(Authority authority) {
		return authority.getId();
	}
}
