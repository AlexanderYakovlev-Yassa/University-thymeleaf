package ua.foxminded.yakovlev.university.dao.impl;

import java.sql.PreparedStatement;
import java.time.LocalDateTime;
import java.util.List;

import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.jdbc.core.PreparedStatementCreator;
import org.springframework.jdbc.core.PreparedStatementSetter;
import org.springframework.jdbc.core.RowMapper;
import org.springframework.stereotype.Component;

import ua.foxminded.yakovlev.university.dao.AbstractDao;
import ua.foxminded.yakovlev.university.dao.TimetableRecordDao;
import ua.foxminded.yakovlev.university.entity.Group;
import ua.foxminded.yakovlev.university.entity.TimetableRecord;

@Component
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
	private static final String FIND_BY_TIMETABLE_ID = "SELECT g.* "
			+ "FROM public.timetable_record_groups t "
			+ "JOIN public.groups g ON t.timetable_record_group_group_id = g.group_id "
			+ "WHERE t.timetable_record_group_timetable_record_id = ?;";
	private static final String ADD_TO_TIMETABLE = "INSERT INTO public.timetable_record_groups( "
			+ "timetable_record_group_timetable_record_id, timetable_record_group_group_id) "
			+ "VALUES (?, ?);";
	private static final String REMOVE_FROM_TIMETABLE = "DELETE FROM public.timetable_record_groups t "
			+ "WHERE t.timetable_record_group_timetable_record_id = ? AND t.timetable_record_group_group_id = ?;";
	private static final String ID_KEY = "timetable_record_id";
	
	private final RowMapper<Group> groupMapper;
	private final JdbcTemplate jdbcTemplate;

	public TimetableRecordDaoImpl(JdbcTemplate jdbcTemplate, RowMapper<TimetableRecord> recordMapper, RowMapper<Group> groupMapper) {
		super(jdbcTemplate, recordMapper, FIND_ALL, FIND_BY_ID, DELETE);
		this.jdbcTemplate = jdbcTemplate;
		this.groupMapper = groupMapper;
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
		
		List<TimetableRecord> recordList = findByQuery(FIND_BETWEEN_TWO_DATES, preparedStatementSetter);
		
		determineGroupsInRecordList(recordList);
		
		return recordList;
	}
	
	@Override
	public List<TimetableRecord> findAll() {
		
		List<TimetableRecord> allRecordList = super.findAll();
		
		determineGroupsInRecordList(allRecordList);
		
		return allRecordList;		
	}
	
	@Override
	public TimetableRecord findById(Long id) {
		
		TimetableRecord record = super.findById(id);
		
		determineGroupInRecord(record);
		
		return record;		
	}
	
	@Override
	public void delete(Long id) {
		
		List<Group> groupList = findGroupsByTimeableId(id);
		groupList.forEach(g -> removeGroupFromTimeableRecordGroups(id, g.getId()));
		
		super.delete(id);
	}
	
	@Override
	public TimetableRecord save(TimetableRecord record) {
		
		List<Group> groupList = record.getGroupList();
		TimetableRecord savedRecord = super.save(record);
		groupList.forEach(g -> addGroupToTimeableRecordGroups(savedRecord.getId(), g.getId()));
		
		return findById(savedRecord.getId());
	}
	
	@Override
	public TimetableRecord addGroupToTimeable(Long timetableId, Long groupId) {
		
		addGroupToTimeableRecordGroups(timetableId, groupId);
		
		return findById(timetableId);
	}

	@Override
	public TimetableRecord removeGroupFromTimeable(Long timetableId, Long groupId) {
		
		removeGroupFromTimeableRecordGroups(timetableId, groupId);
		
		return findById(timetableId);
	}
	
	private void determineGroupsInRecordList(List<TimetableRecord> recordList) {		
		recordList.forEach(r -> r.setGroupList(findGroupsByTimeableId(r.getId())));
	}	
	
	private void determineGroupInRecord(TimetableRecord record) {
		record.setGroupList(findGroupsByTimeableId(record.getId()));
	}
	
	private List<Group> findGroupsByTimeableId(Long id) {
				
		return jdbcTemplate.query(FIND_BY_TIMETABLE_ID, ps -> {
			ps.setLong(1, id);
		}, groupMapper);
	}
	
	private void addGroupToTimeableRecordGroups(Long timetableId, Long groupId) {
		
		jdbcTemplate.update(connection -> {
			PreparedStatement ps = connection.prepareStatement(ADD_TO_TIMETABLE);
			ps.setLong(1, timetableId);
			ps.setLong(2, groupId);
			return ps;
		});
	}
	
	private void removeGroupFromTimeableRecordGroups(Long timetableId, Long groupId) {
		
		executeUpdate(REMOVE_FROM_TIMETABLE, ps -> {
			ps.setLong(1, timetableId);
			ps.setLong(2, groupId);
		});
	}
}
