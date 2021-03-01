package ua.foxminded.yakovlev.university.repository;

import static org.junit.jupiter.api.Assertions.*;

import java.util.List;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest;
import org.springframework.test.context.jdbc.Sql;

import ua.foxminded.yakovlev.university.entity.Role;

@DataJpaTest
@Sql({"/sqlscripts/insertPersons.sql", "/sqlscripts/insertRoles.sql", "/sqlscripts/insertUsers.sql", "/sqlscripts/insertUserRoles.sql"})
class RoleRepositoryIntegrationTest {
	
    @Autowired
    protected RoleRepository roleRep;
    
    @Autowired
    protected UserRepository userRep;
    
    @Test    
    void findAllShouldReturnThreeRoulesWithNotNullNames() {

    	List<Role> roleList = roleRep.findAll();
    	
    	assertTrue(roleList.size()==3);
    	
    	roleList.stream().forEach(r -> assertNotNull(r.getName()));
    }
    
    @Test    
    void findByNameShouldReturnNotNullRole() {
    	
    	Role role = roleRep.findByName("STUDENT");
    	System.out.println(role);
    	
    	assertNotNull(role);
    }
}