package ua.foxminded.yakovlev.university.dao.impl;

import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.jdbc.core.PreparedStatementSetter;
import org.springframework.jdbc.core.RowMapper;

import ua.foxminded.yakovlev.university.dao.AbstractDao;
import ua.foxminded.yakovlev.university.dao.GroupDao;
import ua.foxminded.yakovlev.university.entity.Group;

public class GroupDaoImpl extends AbstractDao<Group, Long> implements GroupDao {

	private static final String FIND_ALL = "SELECT * FROM public.groups;";
	private static final String SAVE = "INSERT INTO public.groups(group_name) VALUES (?) RETURNING group_id AS id;";
	private static final String FIND_BY_ID = "SELECT * FROM public.groups WHERE group_id = ?;";
	private static final String UPDATE = "UPDATE public.groups "
			+ "SET group_name=? "
			+ "WHERE group_id=?;";
	private static final String DELETE = "DELETE FROM public.groups WHERE group_id = ?;";
	private static final String FIND_BY_GROUP_NAME = "SELECT * FROM public.groups WHERE group_name = ?;";
	
	private final JdbcTemplate jdbcTemplate;
	private final RowMapper<Group> groupMapper;
	
	public GroupDaoImpl(JdbcTemplate jdbcTemplate, RowMapper<Group> groupMapper) {
		super(jdbcTemplate, groupMapper, FIND_ALL, FIND_BY_ID, UPDATE, DELETE);
		this.jdbcTemplate = jdbcTemplate;
		this.groupMapper = groupMapper;
	}

	@Override
	public Group findGroupByName(String groupName) {
		return jdbcTemplate.queryForObject(FIND_BY_GROUP_NAME, groupMapper, groupName);
	}

	@Override
	public PreparedStatementSetter setValuesForUpdate(Group group) {
		
		return ps -> {
			ps.setString(1, group.getName());
			ps.setLong(2, group.getId());
		};
	}

	@Override
	public Long getId(Group entity) {		
		return entity.getId();
	}
	
	@Override
	public Group save(Group group) {
		
		Long groupId = jdbcTemplate.query(SAVE, 
				ps -> ps.setString(1, group.getName()), 
				rs -> {
					rs.next();
					return rs.getLong("id");
				});
		
		return findById(groupId);		
	}
}
