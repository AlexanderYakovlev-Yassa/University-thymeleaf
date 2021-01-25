package ua.foxminded.yakovlev.university.repository;

import java.time.LocalDateTime;
import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

import ua.foxminded.yakovlev.university.entity.TimetableRecord;

public interface TimetableRecordRepository extends JpaRepository<TimetableRecord, Long>{
	
	@Query ("select t from TimetableRecord t where t.date >= ?1 and t.date < ?2")
	List<TimetableRecord> findByPeriodOfTime(LocalDateTime periodStart, LocalDateTime periodFinish);
	
	@Query ("select t from TimetableRecord t where t.lecturer.personId = ?1 and t.date >= ?2 and t.date < ?3")
	List<TimetableRecord> findByLecturer(Long lecturerId, LocalDateTime periodStart, LocalDateTime periodFinish);
	
	@Query("select t from TimetableRecord t join t.groupList g where g.id = ?1 and t.date >= ?2 and t.date < ?3")
	List<TimetableRecord> findByGroup(Long groupId, LocalDateTime periodStart, LocalDateTime periodFinish);
}
