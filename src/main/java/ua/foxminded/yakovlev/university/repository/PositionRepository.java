package ua.foxminded.yakovlev.university.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import ua.foxminded.yakovlev.university.entity.Position;

public interface PositionRepository extends JpaRepository<Position, Long>{

    Position findPositionByName(String name);
}