package ua.foxminded.yakovlev.university.jpaDao;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import ua.foxminded.yakovlev.university.entity.Group;

public interface GroupRepository extends JpaRepository<Group, Long>{
	
	@Query("select g from groups g where g.group_name = :name")
    Group findGroupByName(@Param("name") String name);
}