package ua.foxminded.yakovlev.university.mapper;

import java.sql.ResultSet;
import java.sql.SQLException;

import org.springframework.jdbc.core.RowMapper;
import org.springframework.stereotype.Component;

import ua.foxminded.yakovlev.university.entity.Position;

@Component
public class PositionMapper implements RowMapper<Position> {
	
	private static final String POSITION_ID = "position_id";
	private static final String POSITION_NAME = "position_name";

	@Override
	public Position mapRow(ResultSet rs, int rowNum) throws SQLException {
		
		Position position = new Position();
		
		position.setId(rs.getLong(POSITION_ID));
		position.setName(rs.getString(POSITION_NAME));
				
		return position;
	}
}
