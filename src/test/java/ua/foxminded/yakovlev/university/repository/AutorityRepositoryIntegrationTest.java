package ua.foxminded.yakovlev.university.repository;

import static org.junit.jupiter.api.Assertions.*;

import java.util.List;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest;
import org.springframework.test.context.jdbc.Sql;

import ua.foxminded.yakovlev.university.entity.Autority;

@DataJpaTest
@Sql({"/sqlscripts/insertAutorities.sql"})
class AutorityRepositoryIntegrationTest {
        
    @Autowired
    protected AutorityRepository autorityRep;
    
    @Test    
    void findAllShouldReturnFourAutoritiesWithNotNullNames() {	
    	
    	List<Autority> autorityList = autorityRep.findAll();
    	
    	assertTrue(autorityList.size() == 4);
    	
    	autorityList.stream().forEach(a -> assertNotNull(a.getName()));
    }
}