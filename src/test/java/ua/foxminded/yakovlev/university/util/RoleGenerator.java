package ua.foxminded.yakovlev.university.util;

import java.io.File;
import java.io.IOException;
import java.util.List;
import java.util.Set;

import com.fasterxml.jackson.core.type.TypeReference;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.dataformat.yaml.YAMLFactory;

import ua.foxminded.yakovlev.university.entity.Authority;
import ua.foxminded.yakovlev.university.entity.Role;

public class RoleGenerator {
	
	private static final String YAML_FILE_PATH = "src/test/resources/entity/roles.yaml";


	public Role getRole(Long id, String name, Set<Authority> authorities) {
		
		Role role = new Role();
		role.setId(id);
		role.setName(name);
		role.setAuthorities(authorities);
		
		return role;
	}
	
	public List<Role> getRoleList() {

		ObjectMapper mapper = new ObjectMapper(new YAMLFactory());
		List<Role> roleList;
		try {
			roleList = mapper.readValue(new File(YAML_FILE_PATH), new TypeReference<List<Role>>(){});
		} catch (IOException e) {
			throw new IllegalArgumentException("Bad yaml file " + YAML_FILE_PATH);
		}
				
		return roleList;
	}
}
