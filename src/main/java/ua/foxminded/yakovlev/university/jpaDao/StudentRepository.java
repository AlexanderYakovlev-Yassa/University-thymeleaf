package ua.foxminded.yakovlev.university.jpaDao;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import ua.foxminded.yakovlev.university.entity.Student;

public interface StudentRepository extends JpaRepository<Student, Long>{
	
	@Query("select s from students s where s.student_group_id = :group_id")
    List<Student> findByGroupId(@Param("group_id") Long groupId);
}