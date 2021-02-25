package ua.foxminded.yakovlev.university.repository;

import org.springframework.data.jpa.repository.JpaRepository;

import ua.foxminded.yakovlev.university.entity.Role;

public interface RoleRepository extends JpaRepository<Role, Long> {

}
