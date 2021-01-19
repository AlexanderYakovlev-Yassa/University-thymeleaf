package ua.foxminded.yakovlev.university.util;

import java.io.File;
import java.io.IOException;
import java.util.List;

import com.fasterxml.jackson.core.type.TypeReference;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.dataformat.yaml.YAMLFactory;

import ua.foxminded.yakovlev.university.entity.Group;

public class GroupGenerator {
	
	private static final String YAML_FILE_PATH = "src/test/resources/entity/groups.yaml";

	public Group getGroup(Long id, String name) {
		
		Group group = new Group();
		group.setId(id);
		group.setName(name);
		
		return group;
	}
	
	public List<Group> getGroupList() {

		ObjectMapper mapper = new ObjectMapper(new YAMLFactory());
		List<Group> groupList;
		try {
			groupList = mapper.readValue(new File(YAML_FILE_PATH), new TypeReference<List<Group>>(){});
		} catch (IOException e) {
			throw new IllegalArgumentException("Bad yaml file " + YAML_FILE_PATH);
		}
		
		return groupList;
	}
}