package ua.foxminded.yakovlev.university.jpaDao;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import ua.foxminded.yakovlev.university.entity.Position;

public interface PositionRepository extends JpaRepository<Position, Long>{
	
	@Query("select p from positions p where p.position_name = :name")
    Position findPositionByName(@Param("name") String name);
}