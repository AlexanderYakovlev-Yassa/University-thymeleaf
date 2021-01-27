package ua.foxminded.yakovlev.university.repository;

import static org.junit.jupiter.api.Assertions.*;

import java.util.List;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest;
import org.springframework.test.context.jdbc.Sql;

import ua.foxminded.yakovlev.university.entity.Authority;

@DataJpaTest
@Sql({"/sqlscripts/insertAuthorities.sql"})
class AuthorityRepositoryIntegrationTest {
        
    @Autowired
    protected AuthorityRepository authorityRep;
    
    @Test    
    void findAllShouldReturnFourAuthoritiesWithNotNullNames() {	
    	
    	List<Authority> authorityList = authorityRep.findAll();
    	
    	assertTrue(authorityList.size() == 4);
    	
    	authorityList.stream().forEach(a -> assertNotNull(a.getName()));
    }
}