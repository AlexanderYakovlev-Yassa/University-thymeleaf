package ua.foxminded.yakovlev.university.service.impl;

import java.util.List;

import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import ua.foxminded.yakovlev.university.entity.Student;
import ua.foxminded.yakovlev.university.jpaDao.GroupRepository;
import ua.foxminded.yakovlev.university.jpaDao.StudentRepository;
import ua.foxminded.yakovlev.university.service.StudentService;

@Service
@Transactional
public class StudentServiceImpl extends AbstractServiceJpa<Student, Long> implements StudentService {

	private final StudentRepository studentDao;
	private final GroupRepository groupDao;
	
	public StudentServiceImpl(StudentRepository studentDao, GroupRepository groupDao)  {
		super(studentDao);
		this.studentDao = studentDao;
		this.groupDao = groupDao;
	}

	@Override
	public List<Student> findByGroupId(Long groupId) {
		return studentDao.findByGroupId(groupId);
	}

	@Override
	public Student addGroup(Long studentId, Long groupId) {
		
		Student student = findById(studentId);
		
		if (student.getGroup() != null) {
			throw new IllegalArgumentException("A student already has a group!");
		}
		
		student.setGroup(groupDao.findById(groupId).orElse(null));
		
		return update(student);
	}

	@Override
	public Student removeGroup(Long studentId) {
		
		Student student = findById(studentId);
		student.setGroup(null);
		
		return update(student);
	}

	@Override
	public List<Student> findStudentsWithoutGroup() {
		return studentDao.findStudentsWithoutGroup();
	}
}
