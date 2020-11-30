package ua.foxminded.yakovlev.university.init;

import java.util.Properties;

import javax.naming.NamingException;
import javax.sql.DataSource;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.ComponentScan;
import org.springframework.context.annotation.Configuration;
import org.springframework.dao.annotation.PersistenceExceptionTranslationPostProcessor;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.jndi.JndiTemplate;
import org.springframework.orm.jpa.JpaTransactionManager;
import org.springframework.orm.jpa.JpaVendorAdapter;
import org.springframework.orm.jpa.LocalContainerEntityManagerFactoryBean;
import org.springframework.orm.jpa.vendor.HibernateJpaVendorAdapter;
import org.springframework.transaction.annotation.EnableTransactionManagement;

import ua.foxminded.yakovlev.university.dao.TimetableRecordDao;
import ua.foxminded.yakovlev.university.dao.impl.ScriptExecutor;
import ua.foxminded.yakovlev.university.dao.impl.TimetableRecordDaoImpl;
import ua.foxminded.yakovlev.university.jpaDao.CourseRepository;
import ua.foxminded.yakovlev.university.jpaDao.GroupRepository;
import ua.foxminded.yakovlev.university.jpaDao.LecturerRepository;
import ua.foxminded.yakovlev.university.jpaDao.PositionRepository;
import ua.foxminded.yakovlev.university.jpaDao.StudentRepository;
import ua.foxminded.yakovlev.university.mapper.GroupMapper;
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
@EnableTransactionManagement
public class AppConfiguration {

	@Bean
	public DataSource getDataSource() throws NamingException {

		JndiTemplate jndiTemplate = new JndiTemplate();
		DataSource dataSource = (DataSource) jndiTemplate.lookup("java:comp/env/jdbc/UniversityDB");

		return dataSource;
	}

	@Bean
	public LocalContainerEntityManagerFactoryBean entityManagerFactory() throws NamingException {
		LocalContainerEntityManagerFactoryBean entityManager = new LocalContainerEntityManagerFactoryBean();
		entityManager.setDataSource(getDataSource());
		entityManager.setPackagesToScan(new String[] { "ua.foxminded.yakovlev.university.entity" });

		JpaVendorAdapter vendorAdapter = new HibernateJpaVendorAdapter();
		entityManager.setJpaVendorAdapter(vendorAdapter);
		entityManager.setJpaProperties(additionalProperties());

		return entityManager;
	}

	@Bean
	public JpaTransactionManager transactionManager() throws NamingException {
		JpaTransactionManager transactionManager = new JpaTransactionManager();
		transactionManager.setEntityManagerFactory(entityManagerFactory().getObject());

		return transactionManager;
	}

	@Bean
	public PersistenceExceptionTranslationPostProcessor exceptionTranslation() {
		return new PersistenceExceptionTranslationPostProcessor();
	}

	private Properties additionalProperties() {
		Properties properties = new Properties();
		properties.setProperty("hibernate.hbm2ddl.auto", "create-drop");
		properties.setProperty("hibernate.dialect", "org.hibernate.dialect.PostgreSQLDialect");

		return properties;
	}

	@Bean(name = "scriptExecutor")
	public ScriptExecutor getScriptExecutor(JdbcTemplate jdbcTemplate) {
		return new ScriptExecutor(jdbcTemplate);
	}

	@Bean(name = "timetableRecordDao")
	public TimetableRecordDao getTimetableRecordDao(JdbcTemplate jdbcTemplate,
			TimetableRecordMapper timetableRecordMapper, GroupMapper groupMapper) {
		return new TimetableRecordDaoImpl(jdbcTemplate, timetableRecordMapper, groupMapper);
	}

	@Bean(name = "courseService")
	public CourseService getCourseService(CourseRepository courseRepository) {
		return new CourseServiceImpl(courseRepository);
	}

	@Bean(name = "groupService")
	public GroupService getGroupService(GroupRepository groupRepository) {
		return new GroupServiceImpl(groupRepository);
	}

	@Bean(name = "positionService")
	public PositionService getPositionService(PositionRepository positionRepository) {
		return new PositionServiceImpl(positionRepository);
	}

	@Bean(name = "studentService")
	public StudentService getStudentService(StudentRepository studentRepository) {
		return new StudentServiceImpl(studentRepository);
	}

	@Bean(name = "lecturerService")
	public LecturerService getLecturerService(LecturerRepository lecturerRepository) {
		return new LecturerServiceImpl(lecturerRepository);
	}

	@Bean(name = "timetableRecordService")
	public TimetableRecordService getTimetableRecordService(TimetableRecordDao timetableRecordDao) {
		return new TimetableRecordServiceImpl(timetableRecordDao);
	}
}
