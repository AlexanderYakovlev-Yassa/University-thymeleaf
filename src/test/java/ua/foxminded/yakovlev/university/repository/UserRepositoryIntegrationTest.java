package ua.foxminded.yakovlev.university.repository;

import static org.junit.jupiter.api.Assertions.*;

import java.util.List;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest;
import org.springframework.test.context.jdbc.Sql;

import ua.foxminded.yakovlev.university.entity.Autority;
import ua.foxminded.yakovlev.university.entity.Role;
import ua.foxminded.yakovlev.university.entity.User;

@DataJpaTest
@Sql({ "/sqlscripts/insertAutorities.sql", "/sqlscripts/insertRoles.sql", "/sqlscripts/insertRoleAutorities.sql",
		"/sqlscripts/insertPersons.sql", "/sqlscripts/insertUsers.sql", "/sqlscripts/insertUserRoles.sql" })
class UserRepositoryIntegrationTest {

	@Autowired
	protected UserRepository userRep;

	@Autowired
	protected AutorityRepository autorityRep;

	@Autowired
	protected RoleRepository roleRep;

	@Test
	void findAllShouldReturnNineUserssWithNotNullFields() {

		List<User> userList = userRep.findAll();

		assertTrue(userList.size() == 9);

		for (User u : userList) {

			assertNotNull(u.getName());
			assertNotNull(u.getPerson());
			assertNotNull(u.getRoleSet());
			
			for (Role r : u.getRoleSet()) {
				
				assertNotNull(r.getName());
				assertNotNull(r.getAutorities());
				
				for (Autority a : r.getAutorities()) {
					
					assertNotNull(a.getName());
				}
			}
		}
	}
}