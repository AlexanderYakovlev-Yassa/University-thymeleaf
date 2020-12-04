package ua.foxminded.yakovlev.university.jpaDao;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

import ua.foxminded.yakovlev.university.entity.Group;

public interface GroupRepository extends JpaRepository<Group, Long>{
	
	@Query("select g from Group g where g.name = ?1")	
    Group findGroupByName(String name);
}