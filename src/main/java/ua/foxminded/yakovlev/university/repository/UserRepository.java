package ua.foxminded.yakovlev.university.repository;

import java.util.Set;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

import ua.foxminded.yakovlev.university.entity.User;

public interface UserRepository extends JpaRepository<User, Long> {

	User findByName(String username);
	
	@Query("SELECT u FROM User u JOIN u.roleSet rs WHERE rs.id = ?1")
	Set<User> findByRoleId(Long roleId);
}