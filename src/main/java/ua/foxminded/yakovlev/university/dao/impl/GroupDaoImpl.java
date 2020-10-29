package ua.foxminded.yakovlev.university.dao.impl;

import java.sql.PreparedStatement;

import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.jdbc.core.PreparedStatementCreator;
import org.springframework.jdbc.core.RowMapper;
import org.springframework.stereotype.Component;

import ua.foxminded.yakovlev.university.dao.AbstractDao;
import ua.foxminded.yakovlev.university.dao.GroupDao;
import ua.foxminded.yakovlev.university.entity.Group;

@Component
public class GroupDaoImpl extends AbstractDao<Group, Long> implements GroupDao {

	private static final String FIND_ALL = "SELECT * FROM public.groups;";
	private static final String SAVE = "INSERT INTO public.groups(group_name) VALUES (?);";
	private static final String FIND_BY_ID = "SELECT * FROM public.groups WHERE group_id = ?;";
	private static final String UPDATE = "UPDATE public.groups "
			+ "SET group_name=? "
			+ "WHERE group_id=?;";
	private static final String DELETE = "DELETE FROM public.groups WHERE group_id = ?;";
	private static final String FIND_BY_GROUP_NAME = "SELECT * FROM public.groups WHERE group_name = ?;";
	private static final String ID_KEY = "group_id";
	
	private final JdbcTemplate jdbcTemplate;
	private final RowMapper<Group> groupMapper;
	
	public GroupDaoImpl(JdbcTemplate jdbcTemplate, RowMapper<Group> groupMapper) {
		super(jdbcTemplate, groupMapper, FIND_ALL, FIND_BY_ID, DELETE);
		this.jdbcTemplate = jdbcTemplate;
		this.groupMapper = groupMapper;
	}

	@Override
	public Group findGroupByName(String groupName) {
		return jdbcTemplate.queryForObject(FIND_BY_GROUP_NAME, groupMapper, groupName);
	}

	@Override
	public PreparedStatementCreator getPreparedStatementCreatorForSave(Group group) {
		
		return connection -> {
	        PreparedStatement ps = connection
	                .prepareStatement(SAVE, new String[] { ID_KEY });
	                ps.setString(1, group.getName());
	                return ps;
	              };
	}

	@Override
	public PreparedStatementCreator getPreparedStatementCreatorForUpdate(Group group) {
		
		return connection -> {
	        PreparedStatement ps = connection
	                .prepareStatement(UPDATE, new String[] { ID_KEY });
	                ps.setString(1, group.getName());
	                ps.setLong(2, group.getId());
	                return ps;
	              };
	}
}
