package ua.foxminded.yakovlev.university.service.impl;

import java.util.List;

import org.springframework.stereotype.Component;

import ua.foxminded.yakovlev.university.entity.Student;
import ua.foxminded.yakovlev.university.jpaDao.StudentRepository;
import ua.foxminded.yakovlev.university.service.StudentService;

@Component
public class StudentServiceImpl extends AbstractServiceJpa<Student, Long> implements StudentService {

	private final StudentRepository dao;
	
	public StudentServiceImpl(StudentRepository studentDao)  {
		super(studentDao);
		this.dao = studentDao;
	}

	@Override
	public List<Student> findByGroupId(Long groupId) {
		return dao.findByGroupId(groupId);
	}
}
