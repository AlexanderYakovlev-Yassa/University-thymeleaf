package ua.foxminded.yakovlev.university.repository;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import ua.foxminded.yakovlev.university.entity.Student;

public interface StudentRepository extends JpaRepository<Student, Long>{

    List<Student> findByGroupId(Long groupId);

	List<Student> getByGroup_Id(Long id);
}
