package ua.foxminded.yakovlev.university.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import ua.foxminded.yakovlev.university.entity.Group;

public interface GroupRepository extends JpaRepository<Group, Long>{

    Group findGroupByName(String name);
}