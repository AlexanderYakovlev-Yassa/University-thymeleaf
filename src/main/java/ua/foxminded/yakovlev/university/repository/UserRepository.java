package ua.foxminded.yakovlev.university.repository;

import org.springframework.data.jpa.repository.JpaRepository;

import ua.foxminded.yakovlev.university.entity.User;

public interface UserRepository extends JpaRepository<User, Long> {

	User findByName(String username);
}
