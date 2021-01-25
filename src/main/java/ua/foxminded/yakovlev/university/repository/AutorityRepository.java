package ua.foxminded.yakovlev.university.repository;

import org.springframework.data.jpa.repository.JpaRepository;

import ua.foxminded.yakovlev.university.entity.Autority;

public interface AutorityRepository extends JpaRepository<Autority, Long> {
	
}
