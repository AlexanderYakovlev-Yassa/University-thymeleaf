package ua.foxminded.yakovlev.university.init;

import javax.naming.NamingException;
import javax.sql.DataSource;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.ComponentScan;
import org.springframework.context.annotation.Configuration;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.jndi.JndiTemplate;

import ua.foxminded.yakovlev.university.dao.CourseDao;
import ua.foxminded.yakovlev.university.dao.GroupDao;
import ua.foxminded.yakovlev.university.dao.LecturerDao;
import ua.foxminded.yakovlev.university.dao.PositionDao;
import ua.foxminded.yakovlev.university.dao.StudentDao;
import ua.foxminded.yakovlev.university.dao.TimetableRecordDao;
import ua.foxminded.yakovlev.university.dao.impl.CourseDaoImpl;
import ua.foxminded.yakovlev.university.dao.impl.GroupDaoImpl;
import ua.foxminded.yakovlev.university.dao.impl.LecturerDaoImpl;
import ua.foxminded.yakovlev.university.dao.impl.PositionDaoImpl;
import ua.foxminded.yakovlev.university.dao.impl.ScriptExecutor;
import ua.foxminded.yakovlev.university.dao.impl.StudentDaoImpl;
import ua.foxminded.yakovlev.university.dao.impl.TimetableRecordDaoImpl;
import ua.foxminded.yakovlev.university.mapper.CourseMapper;
import ua.foxminded.yakovlev.university.mapper.GroupMapper;
import ua.foxminded.yakovlev.university.mapper.LecturerMapper;
import ua.foxminded.yakovlev.university.mapper.PositionMapper;
import ua.foxminded.yakovlev.university.mapper.StudentMapper;
import ua.foxminded.yakovlev.university.mapper.TimetableRecordMapper;
import ua.foxminded.yakovlev.university.service.CourseService;
import ua.foxminded.yakovlev.university.service.GroupService;
import ua.foxminded.yakovlev.university.service.LecturerService;
import ua.foxminded.yakovlev.university.service.PositionService;
import ua.foxminded.yakovlev.university.service.StudentService;
import ua.foxminded.yakovlev.university.service.TimetableRecordService;
import ua.foxminded.yakovlev.university.service.impl.CourseServiceImpl;
import ua.foxminded.yakovlev.university.service.impl.GroupServiceImpl;
import ua.foxminded.yakovlev.university.service.impl.LecturerServiceImpl;
import ua.foxminded.yakovlev.university.service.impl.PositionServiceImpl;
import ua.foxminded.yakovlev.university.service.impl.StudentServiceImpl;
import ua.foxminded.yakovlev.university.service.impl.TimetableRecordServiceImpl;

@Configuration
@ComponentScan("ua.foxminded.yakovlev.university")
public class AppConfiguration {

	@Bean (name="jdbcTemplate")
	public JdbcTemplate getJdbcTemplate() throws NamingException {
		
		JndiTemplate jndiTemplate = new JndiTemplate();
		DataSource dataSource = (DataSource) jndiTemplate.lookup("java:comp/env/jdbc/UniversityDB");
		
		return new JdbcTemplate(dataSource);
	}
	
	@Bean (name="scriptExecutor")
	public ScriptExecutor getScriptExecutor(JdbcTemplate jdbcTemplate) {
		return new ScriptExecutor(jdbcTemplate);
	}
	
	@Bean (name="courseDao")
	public CourseDao getCourseDao(JdbcTemplate jdbcTemplate, CourseMapper courseMapper) {		
		return new CourseDaoImpl(jdbcTemplate, courseMapper);
	}
	
	@Bean (name="groupDao")
	public GroupDao getGroupDao(JdbcTemplate jdbcTemplate, GroupMapper groupMapper) {		
		return new GroupDaoImpl(jdbcTemplate, groupMapper);
	}
	
	@Bean (name="positionDao")
	public PositionDao getPositionDao(JdbcTemplate jdbcTemplate, PositionMapper positionMapper) {		
		return new PositionDaoImpl(jdbcTemplate, positionMapper);
	}
	
	@Bean (name="lecturerDao")
	public LecturerDao getLecturerDao(JdbcTemplate jdbcTemplate, LecturerMapper lecturerMapper) {		
		return new LecturerDaoImpl(jdbcTemplate, lecturerMapper);
	}
	
	@Bean (name="studentDao")
	public StudentDao getStudentDao(JdbcTemplate jdbcTemplate, StudentMapper studentMapper) {		
		return new StudentDaoImpl(jdbcTemplate, studentMapper);
	}
	
	@Bean (name="timetableRecordDao")
	public TimetableRecordDao getTimetableRecordDao(JdbcTemplate jdbcTemplate, 
			TimetableRecordMapper timetableRecordMapper,
			GroupMapper groupMapper) {		
		return new TimetableRecordDaoImpl(jdbcTemplate, timetableRecordMapper, groupMapper);
	}
	
	@Bean (name="courseService")
	public CourseService getCourseService(CourseDao courseDao) {		
		return new CourseServiceImpl(courseDao);
	}
	
	@Bean (name="groupService")
	public GroupService getGroupService(GroupDao groupDao) {		
		return new GroupServiceImpl(groupDao);
	}
	
	@Bean (name="positionService")
	public PositionService getPositionService(PositionDao positionDao) {		
		return new PositionServiceImpl(positionDao);
	}
	
	@Bean (name="studentService")
	public StudentService getStudentService(StudentDao studentDao) {		
		return new StudentServiceImpl(studentDao);
	}
	
	@Bean (name="lecturerService")
	public LecturerService getLecturerService(LecturerDao lecturerDao) {		
		return new LecturerServiceImpl(lecturerDao);
	}
	
	@Bean (name="timetableRecordService")
	public TimetableRecordService getTimetableRecordService(TimetableRecordDao timetableRecordDao) {		
		return new TimetableRecordServiceImpl(timetableRecordDao);
	}
}
