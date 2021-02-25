package ua.foxminded.yakovlev.university.service.impl;

import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import ua.foxminded.yakovlev.university.entity.Role;
import ua.foxminded.yakovlev.university.repository.RoleRepository;
import ua.foxminded.yakovlev.university.service.RoleService;

@Service
@Transactional
public class RoleServiceImpl extends AbstractServiceJpa<Role, Long> implements RoleService {
	
	private static final String ADMIN_ROLE_NAME = "ADMIN";
	
	private static final String ENTITY_NAME = "Role";

	public RoleServiceImpl(RoleRepository repository) {
		super(repository);
	}

	@Override
	protected String getEntityName() {		
		return ENTITY_NAME;
	}

	@Override
	protected Long getIdentifire(Role role) {		
		return role.getId();
	}

	@Override
	public void delete(Long id) {
		
		if (ADMIN_ROLE_NAME.equals(findById(id).getName())) {
			throw new IllegalArgumentException("Role " + ADMIN_ROLE_NAME + " can not be deleted");
		}
		
		super.delete(id);
	}
}
