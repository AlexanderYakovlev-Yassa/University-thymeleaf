package ua.foxminded.yakovlev.university.dao.impl;

import java.sql.PreparedStatement;
import java.time.LocalDateTime;
import java.util.List;

import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.jdbc.core.PreparedStatementCreator;
import org.springframework.jdbc.core.PreparedStatementSetter;
import org.springframework.jdbc.core.RowMapper;
import ua.foxminded.yakovlev.university.dao.AbstractDao;
import ua.foxminded.yakovlev.university.dao.TimetableRecordDao;
import ua.foxminded.yakovlev.university.entity.TimetableRecord;

public class TimetableRecordDaoImpl extends AbstractDao<TimetableRecord, Long> implements TimetableRecordDao {

	private static final String FIND_ALL = "SELECT t.*, l.*, c.*, p.*, ps.* "
			+ "FROM public.timetable_records t "
			+ "JOIN courses c ON t.timetable_record_course_id = c.course_id "
			+ "JOIN lecturers l ON t.timetable_record_lecturer_id = l.lecturer_id "
			+ "JOIN persons p ON l.lecturer_person_id = p.person_id "
			+ "JOIN positions ps ON l.lecturer_position_id = ps.position_id;";
	private static final String FIND_BY_ID = "SELECT t.*, l.*, c.*, p.*, ps.* " + "FROM public.timetable_records t "
			+ "JOIN courses c ON t.timetable_record_course_id = c.course_id "
			+ "JOIN lecturers l ON t.timetable_record_lecturer_id = l.lecturer_id "
			+ "JOIN persons p ON l.lecturer_person_id = p.person_id "
			+ "JOIN positions ps ON l.lecturer_position_id = ps.position_id "
			+ "WHERE t.timetable_record_id = ?;";
	private static final String SAVE = "INSERT INTO public.timetable_records( "
			+ "timetable_record_time, timetable_record_lecturer_id, timetable_record_course_id) " + "VALUES (?, ?, ?);";
	private static final String UPDATE = "UPDATE public.timetable_records "
			+ "SET timetable_record_time=?, timetable_record_lecturer_id=?, timetable_record_course_id=? "
			+ "WHERE timetable_record_id=?;";
	private static final String DELETE = "DELETE FROM public.timetable_records WHERE timetable_record_id = ?;";
	private static final String FIND_BETWEEN_TWO_DATES = "SELECT t.*, l.*, c.*, p.*, ps.* " + "FROM public.timetable_records t "
			+ "JOIN courses c ON t.timetable_record_course_id = c.course_id "
			+ "JOIN lecturers l ON t.timetable_record_lecturer_id = l.lecturer_id "
			+ "JOIN persons p ON l.lecturer_person_id = p.person_id "
			+ "JOIN positions ps ON l.lecturer_position_id = ps.position_id "
			+ "WHERE timetable_record_time >= ? "
			+ "AND timetable_record_time < ?;";
	private static final String ID_KEY = "timetable_record_id";

	public TimetableRecordDaoImpl(JdbcTemplate jdbcTemplate, RowMapper<TimetableRecord> rowMapper) {
		super(jdbcTemplate, rowMapper, FIND_ALL, FIND_BY_ID, DELETE);
	}

	@Override
	public PreparedStatementCreator getPreparedStatementCreatorForSave(TimetableRecord timetableRecord) {

		return connection -> {
			PreparedStatement ps = connection.prepareStatement(SAVE, new String[] { ID_KEY });
			ps.setObject(1, timetableRecord.getDate());
			ps.setLong(2, timetableRecord.getLecturer().getLecturerId());
			ps.setLong(3, timetableRecord.getCourse().getId());
			return ps;
		};
	}

	@Override
	public PreparedStatementCreator getPreparedStatementCreatorForUpdate(TimetableRecord timetableRecord) {

		return connection -> {
			PreparedStatement ps = connection.prepareStatement(UPDATE,
					new String[] { ID_KEY });
			ps.setObject(1, timetableRecord.getDate());
			ps.setLong(2, timetableRecord.getLecturer().getLecturerId());
			ps.setLong(3, timetableRecord.getCourse().getId());
			ps.setLong(4, timetableRecord.getId());
			return ps;
		};
	}

	@Override
	public List<TimetableRecord> findByPeriodOfTime(LocalDateTime periodStart, LocalDateTime periodFinish) {
		
		PreparedStatementSetter preparedStatementSetter = ps -> {
			ps.setObject(1, periodStart);
			ps.setObject(2, periodFinish);
		};
		
		return findByQuery(FIND_BETWEEN_TWO_DATES, preparedStatementSetter);
	}
}
