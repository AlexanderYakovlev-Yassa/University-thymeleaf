package ua.foxminded.yakovlev.university.repository;

import static org.junit.jupiter.api.Assertions.*;

import java.util.List;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest;
import org.springframework.test.context.jdbc.Sql;

import ua.foxminded.yakovlev.university.entity.Role;

@DataJpaTest
@Sql({	"/sqlscripts/insertRoles.sql"})
class RoleRepositoryIntegrationTest {
	
    @Autowired
    protected RoleRepository roleRep;
    
    @Test    
    void findAllShouldReturnThreeRoulesWithNotNullNames() {

    	List<Role> roleList = roleRep.findAll();
    	
    	assertTrue(roleList.size()==3);
    	
    	roleList.stream().forEach(r -> assertNotNull(r.getName()));
    }
}