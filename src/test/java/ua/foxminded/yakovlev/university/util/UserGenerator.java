package ua.foxminded.yakovlev.university.util;

import java.io.File;
import java.io.IOException;
import java.util.List;
import java.util.Set;

import com.fasterxml.jackson.core.type.TypeReference;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.dataformat.yaml.YAMLFactory;

import ua.foxminded.yakovlev.university.entity.Person;
import ua.foxminded.yakovlev.university.entity.Role;
import ua.foxminded.yakovlev.university.entity.User;

public class UserGenerator {
	
	private static final String YAML_FILE_PATH = "src/test/resources/entity/users.yaml";


	public User getUser(Long id, String name, String password, Person person, Boolean enabled, Set<Role> roleSet) {
		
		User user = new User();
		user.setId(id);
		user.setName(name);
		user.setPassword(password);
		user.setPerson(person);
		user.setEnabled(enabled);
		user.setRoleSet(roleSet);
		
		return user;
	}
	
	public List<User> getUserList() {

		ObjectMapper mapper = new ObjectMapper(new YAMLFactory());
		List<User> userList;
		try {
			userList = mapper.readValue(new File(YAML_FILE_PATH), new TypeReference<List<User>>(){});
		} catch (IOException e) {
			throw new IllegalArgumentException("Bad yaml file " + YAML_FILE_PATH);
		}
				
		return userList;
	}
}
