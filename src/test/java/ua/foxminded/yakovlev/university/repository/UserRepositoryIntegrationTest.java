package ua.foxminded.yakovlev.university.repository;

import static org.junit.jupiter.api.Assertions.*;

import java.util.HashSet;
import java.util.List;
import java.util.Optional;
import java.util.Set;

import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest;
import org.springframework.test.context.jdbc.Sql;

import ua.foxminded.yakovlev.university.entity.Authority;
import ua.foxminded.yakovlev.university.entity.Person;
import ua.foxminded.yakovlev.university.entity.Role;
import ua.foxminded.yakovlev.university.entity.Student;
import ua.foxminded.yakovlev.university.entity.User;

@DataJpaTest
@Sql({ "/sqlscripts/insertAuthorities.sql", "/sqlscripts/insertRoles.sql", "/sqlscripts/insertRoleAuthorities.sql",
		"/sqlscripts/insertPersons.sql", "/sqlscripts/insertUsers.sql", "/sqlscripts/insertUserRoles.sql" })
class UserRepositoryIntegrationTest {

	@Autowired
	protected UserRepository userRepository;

	@Autowired
	protected AuthorityRepository authorityRepository;
	
	@Autowired
	protected StudentRepository studentRepository;
	
	@Autowired
	protected RoleRepository roleRepository;

	@Autowired
	protected RoleRepository roleRep;

	@Test
	void findAllShouldReturnNineUserssWithNotNullFields() {

		List<User> userList = userRepository.findAll();

		assertTrue(userList.size() == 9);

		for (User u : userList) {

			assertNotNull(u.getName());
			assertNotNull(u.getPerson());
			assertNotNull(u.getRoleSet());
			
			for (Role r : u.getRoleSet()) {
				
				assertNotNull(r.getName());
				assertNotNull(r.getAuthorities());
				
				for (Authority a : r.getAuthorities()) {
					
					assertNotNull(a.getName());
				}
			}
		}
	}
	
	@Test
	void saveShouldReturnSavedUser() {
		
		User expected = new User();
		Student student = new Student();
		student.setFirstName("Test");
		student.setLastName("Tesrerovich");
		student = studentRepository.save(student);
		Person person = new Person();
		person.setPersonId(student.getPersonId());
		person.setFirstName(student.getFirstName());
		person.setLastName(student.getLastName());
		Set<Role> roleSet = new HashSet<>();
		roleSet.add(roleRepository.findAll().get(0));
		roleSet.add(roleRepository.findAll().get(1));
		
		expected.setName("testuser");
		expected.setPerson(person);
		expected.setPassword("$2y$12$Xt0KvlEYdpP16Bv/kvi.WOpRYtwagtMiEni1E3sQZaOlZWvmcpjoi");
		expected.setEnabled(true);
		expected.setRoleSet(roleSet);
		
		User actual = userRepository.save(expected);
		
		assertNotNull(actual.getId());
		assertEquals(expected, actual);
		
		List<User> userList = userRepository.findAll();
		assertTrue(userList.size() == 10);
	}
	
	@Test
	void findByIdShouldReturnUser() {
		
		User expected = userRepository.findAll().get(0);
		assertNotNull(expected);
		
		Long id = expected.getId();
		
		User actual = userRepository.findById(id).get();
		
		assertEquals(expected, actual);
	}

	@Test
	void findByIdShouldReturnOptionalEmpty() {
		
		Optional<User> expected = Optional.empty();
		assertNotNull(expected);
		
		Long id = 99L;
		
		Optional<User> actual = userRepository.findById(id);
		
		assertEquals(expected, actual);
	}
}