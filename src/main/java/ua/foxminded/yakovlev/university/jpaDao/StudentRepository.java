package ua.foxminded.yakovlev.university.jpaDao;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import ua.foxminded.yakovlev.university.entity.Student;

public interface StudentRepository extends JpaRepository<Student, Long>{
	
	@Query("select s from Student s  where s.group.id = ?1")
    List<Student> findByGroupId(Long groupId);
	
	@Query("select s from Student s  where s.group.id = null")
	List<Student> findStudentsWithoutGroup();
}
