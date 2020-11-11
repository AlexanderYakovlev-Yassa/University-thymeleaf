package ua.foxminded.yakovlev.university.service;

import java.util.List;

import ua.foxminded.yakovlev.university.entity.Student;

public interface StudentService extends EntityService<Student, Long>{

	List<Student> findByGroupId(Long groupId);
}
