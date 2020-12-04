package ua.foxminded.yakovlev.university.service.impl;

import java.util.List;

import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import ua.foxminded.yakovlev.university.entity.Student;
import ua.foxminded.yakovlev.university.jpaDao.StudentRepository;
import ua.foxminded.yakovlev.university.service.StudentService;

@Service
@Transactional
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
