package ua.foxminded.yakovlev.university.jpaDao;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import ua.foxminded.yakovlev.university.entity.Lecturer;

public interface LecturerRepository extends JpaRepository<Lecturer, Long>{
	
	@Query("select s from lecturers l where l.lecturer_position_id = :position_id")
    List<Lecturer> findByPositionId(@Param("position_id") Long groupId);
}