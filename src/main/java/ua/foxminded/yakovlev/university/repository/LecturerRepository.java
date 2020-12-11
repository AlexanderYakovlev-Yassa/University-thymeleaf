package ua.foxminded.yakovlev.university.repository;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import ua.foxminded.yakovlev.university.entity.Lecturer;

public interface LecturerRepository extends JpaRepository<Lecturer, Long>{

	List<Lecturer> findByPositionId(Long groupId);
}
