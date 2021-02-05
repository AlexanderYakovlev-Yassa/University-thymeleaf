package ua.foxminded.yakovlev.university.controller.api;

import java.security.Principal;
import java.util.List;
import javax.validation.Valid;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.security.crypto.password.PasswordEncoder;
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
import ua.foxminded.yakovlev.university.dto.UserDto;
import ua.foxminded.yakovlev.university.entity.User;
import ua.foxminded.yakovlev.university.mapper.UserMapper;
import ua.foxminded.yakovlev.university.service.impl.UserService;

@RestController
@RequiredArgsConstructor
@RequestMapping(value="/api/users", produces = "application/json")
public class ApiUserController {
	
	private final UserService userService;
	private final UserMapper userMapper;
	private final PasswordEncoder passwordEncoder;
	
	@GetMapping
	@PreAuthorize("hasAuthority('READ_USER')")
    public ResponseEntity<List<UserDto>> findAllUsers() {
        return ResponseEntity.ok(userMapper.toUserDtos(userService.findAll()));
    }
	
	@PostMapping
	@PreAuthorize("hasAuthority('MANAGE_USER')")
    public ResponseEntity<UserDto> create(
    		@Valid@RequestBody UserDto userDto
    		){
		
		userDto.setPassword(passwordEncoder.encode(userDto.getPassword()));
		User user = userService.save(userMapper.toUser(userDto));
        return ResponseEntity.status(HttpStatus.CREATED).body(userMapper.toUserDto(user));
    }
	
	@GetMapping("/{id}")
	@PreAuthorize("hasAuthority('READ_USER')")
    public ResponseEntity<UserDto> findById(@PathVariable Long id) {               
        return ResponseEntity.ok(userMapper.toUserDto(userService.findById(id)));
    }
	
	@GetMapping("active-user")
	@PreAuthorize("hasAuthority('READ_USER')")
    public ResponseEntity<UserDto> findActiveUser(Principal principal) {
		
		UserDto userDto = new UserDto();
		userDto.setFirstName("Unregistered");
		userDto.setLastName("user");
		
		if (principal != null) {
			userDto = userMapper.toUserDto(userService.findByUsername(principal.getName()));
		}
		
        return ResponseEntity.ok(userDto);
    }
	
	@CrossOrigin(methods = RequestMethod.PUT)
	@PutMapping("{id}")
	@PreAuthorize("hasAuthority('MODIFY_USER')")
    public ResponseEntity<UserDto> update (
    		@PathVariable Long id, 
    		@Valid@RequestBody UserDto userDto
    		){
		
		userDto.setId(id);
		userDto.setPassword(passwordEncoder.encode(userDto.getPassword()));	
		
		return ResponseEntity.status(HttpStatus.ACCEPTED).body(userMapper.toUserDto(userService.update(userMapper.toUser(userDto))));
    }

	@CrossOrigin(methods = RequestMethod.DELETE)
	@DeleteMapping("/{id}")
	@PreAuthorize("hasAuthority('MANAGE_USER')")
    public ResponseEntity<String> delete(@PathVariable Long id) {
        userService.delete(id);
        
        return ResponseEntity.status(HttpStatus.ACCEPTED).build();
    }
}