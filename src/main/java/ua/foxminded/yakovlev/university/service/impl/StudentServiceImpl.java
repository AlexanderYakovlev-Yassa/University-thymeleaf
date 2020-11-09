package ua.foxminded.yakovlev.university.service.impl;

import java.util.List;

import org.springframework.stereotype.Component;

import ua.foxminded.yakovlev.university.dao.StudentDao;
import ua.foxminded.yakovlev.university.entity.Student;
import ua.foxminded.yakovlev.university.exception.DaoNotFoundException;
import ua.foxminded.yakovlev.university.exception.ServiceNotFoundException;
import ua.foxminded.yakovlev.university.service.StudentService;

@Component
public class StudentServiceImpl extends AbstractService<Student, Long> implements StudentService {

	private final StudentDao dao;
	
	public StudentServiceImpl(StudentDao studentDao)  {
		super(studentDao);
		this.dao = studentDao;
	}

	@Override
	public List<Student> findByGroupId(Long groupId) throws ServiceNotFoundException {
		
		try {
			return dao.findByGroupId(groupId);
		} catch (DaoNotFoundException e) {
			throw new ServiceNotFoundException("Entities not found");
		}
	}
}
