package ua.foxminded.yakovlev.university.dao;

import java.util.List;

import ua.foxminded.yakovlev.university.entity.Student;

public interface StudentDao extends EntityDao<Student, Long> {

	List<Student> findByGroupId(Long groupId);
}
