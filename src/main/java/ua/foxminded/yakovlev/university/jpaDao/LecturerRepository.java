package ua.foxminded.yakovlev.university.jpaDao;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import ua.foxminded.yakovlev.university.entity.Lecturer;

public interface LecturerRepository extends JpaRepository<Lecturer, Long>{
	
	@Query("select l from Lecturer l where l.position.id = ?1")
	List<Lecturer> findByPositionId(Long groupId);
}
