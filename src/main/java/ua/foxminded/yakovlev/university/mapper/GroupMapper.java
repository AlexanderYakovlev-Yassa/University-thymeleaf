package ua.foxminded.yakovlev.university.mapper;

import java.sql.ResultSet;
import java.sql.SQLException;

import org.springframework.jdbc.core.RowMapper;
import org.springframework.stereotype.Component;

import ua.foxminded.yakovlev.university.entity.Group;

@Component
public class GroupMapper implements RowMapper<Group> {
	
	private static final String GROUP_ID = "group_id";
	private static final String GROUP_NAME = "group_name";

	@Override
	public Group mapRow(ResultSet rs, int rowNum) throws SQLException {
		
		Group group = new Group();
		
		group.setId(rs.getLong(GROUP_ID));
		group.setName(rs.getString(GROUP_NAME));
				
		return group;
	}
}
