package ua.foxminded.yakovlev.university.repository;

import org.springframework.data.jpa.repository.JpaRepository;

import ua.foxminded.yakovlev.university.entity.Authority;

public interface AuthorityRepository extends JpaRepository<Authority, Long> {
	
	Authority findAuthorityByName(String name);
}
