package ua.foxminded.yakovlev.university.controller.api;

import java.security.Principal;
import java.util.List;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import lombok.RequiredArgsConstructor;
import ua.foxminded.yakovlev.university.dto.UserDto;
import ua.foxminded.yakovlev.university.mapper.UserMapper;
import ua.foxminded.yakovlev.university.service.impl.UserService;

@RestController
@RequiredArgsConstructor
@RequestMapping(value="/api/users", produces = "application/json")
public class ApiUserController {
	
	private final UserService userService;
	private final UserMapper userMapper;
	
	@GetMapping
	@PreAuthorize("hasAuthority('READ_USER')")
    public ResponseEntity<List<UserDto>> findAllUsers() {
        return ResponseEntity.ok(userMapper.toUserDtos(userService.findAll()));
    }
	
	@GetMapping("active-user")
    public ResponseEntity<UserDto> findActiveUser(Principal principal) {
		
		UserDto userDto = new UserDto();
		userDto.setFirstName("Unregistered");
		userDto.setLastName("user");
		
		if (principal != null) {
			userDto = userMapper.toUserDto(userService.findByUsername(principal.getName()));
		}
		
        return ResponseEntity.ok(userDto);
    }
}