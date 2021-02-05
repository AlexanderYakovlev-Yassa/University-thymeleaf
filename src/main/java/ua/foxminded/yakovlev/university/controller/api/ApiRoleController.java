package ua.foxminded.yakovlev.university.controller.api;

import java.util.List;
import javax.validation.Valid;

import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.context.support.ResourceBundleMessageSource;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;
import lombok.RequiredArgsConstructor;
import ua.foxminded.yakovlev.university.dto.RoleDto;
import ua.foxminded.yakovlev.university.entity.Role;
import ua.foxminded.yakovlev.university.mapper.RoleMapper;
import ua.foxminded.yakovlev.university.service.RoleService;

@RestController
@RequiredArgsConstructor
@RequestMapping(value="/api/roles", produces = "application/json")
public class ApiRoleController {
	
	private final RoleService roleService;
	private final RoleMapper roleMapper;
	@Qualifier(value="messageSource")
	private final ResourceBundleMessageSource messageSource;
	
	@GetMapping
	@PreAuthorize("hasAuthority('READ_ROLE')")
    public ResponseEntity<List<RoleDto>> findAll() {
		
        return ResponseEntity.ok(roleMapper.toRoleDtos(roleService.findAll()));
    }
	
	@PostMapping
	@PreAuthorize("hasAuthority('MANAGE_ROLE')")
    public ResponseEntity<RoleDto> create(@Valid@RequestBody RoleDto roleDto) {
		
        Role role = roleService.save(roleMapper.toRole(roleDto));
        
        return ResponseEntity.status(HttpStatus.CREATED).body(roleMapper.toRoleDto(role));
    }
	
	@GetMapping("/{id}")
	@PreAuthorize("hasAuthority('READ_ROLE')")
    public ResponseEntity<RoleDto> findById(@PathVariable Long id) {

        Role role = roleService.findById(id);
               
        return ResponseEntity.ok(roleMapper.toRoleDto(role));
    }
	
	@CrossOrigin(methods = RequestMethod.PUT)
	@PutMapping("/{id}")
	@PreAuthorize("hasAuthority('MODIFY_ROLE')")
    public ResponseEntity<RoleDto> update(@PathVariable Long id, @Valid@RequestBody RoleDto roleDto) {
		
		Role role = roleMapper.toRole(roleDto);
		role.setId(id);
		
        return ResponseEntity.status(HttpStatus.ACCEPTED).body(roleMapper.toRoleDto(roleService.update(role)));
    }

	@CrossOrigin(methods = RequestMethod.DELETE)
	@DeleteMapping("/{id}")
	@PreAuthorize("hasAuthority('MANAGE_ROLE')")
    public ResponseEntity<String> delete(@PathVariable Long id) {
        roleService.delete(id);
        
        return ResponseEntity.status(HttpStatus.ACCEPTED).build();
    }
}