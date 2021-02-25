package ua.foxminded.yakovlev.university.controller;

import java.util.List;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.context.support.ResourceBundleMessageSource;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;

import lombok.RequiredArgsConstructor;
import ua.foxminded.yakovlev.university.dto.UserDto;
import ua.foxminded.yakovlev.university.entity.Role;
import ua.foxminded.yakovlev.university.mapper.LecturerMapper;
import ua.foxminded.yakovlev.university.mapper.StudentMapper;
import ua.foxminded.yakovlev.university.mapper.UserMapper;
import ua.foxminded.yakovlev.university.service.LecturerService;
import ua.foxminded.yakovlev.university.service.RoleService;
import ua.foxminded.yakovlev.university.service.StudentService;
import ua.foxminded.yakovlev.university.service.impl.UserService;

@Controller
@RequiredArgsConstructor
@RequestMapping("/users")
public class UserController {
	
	private final UserService userService;
	private final StudentService studentService;
	private final LecturerService lecturerService;
	private final RoleService roleService;
	private final UserMapper userMapper;
	private final StudentMapper studentMapper;
	private final LecturerMapper lecturerMapper;
	
	@Qualifier(value="messageSource")
	private final ResourceBundleMessageSource messageSource;
	
	@GetMapping()
	@PreAuthorize("hasAuthority('READ_USER')")
    public String showAdminPage(Model model) {
		
		model.addAttribute("userList", userMapper.toUserDtos(userService.findAll()));
				
        return "users/show-users";
    }
	
	@GetMapping("/new")
	@PreAuthorize("hasAuthority('MANAGE_USER')")
    public String newUser(Model model) {
		
		UserDto userDto = new UserDto();
		List<Role> roles = roleService.findAll();
		model.addAttribute("newUser", userDto);
		model.addAttribute("users", userMapper.toUserDtos(userService.findAll()));
		model.addAttribute("allStudents", studentMapper.toStudentDtos(studentService.findAll()));
		model.addAttribute("allLecturers", lecturerMapper.toLecturerDtos(lecturerService.findAll()));
		model.addAttribute("roles", roles);
				
        return "users/new-user";
    }
	
	@GetMapping("/{id}")
	@PreAuthorize("hasAuthority('READ_USER')")
    public String findUser(
    		@PathVariable Long id,
    		Model model) {
		
		model.addAttribute("user", userMapper.toUserDto(userService.findById(id)));
		model.addAttribute("roles", roleService.findAll());
				
        return "users/edit-user";
    }
	
	@PostMapping("/delete")
	@PreAuthorize("hasAuthority('MANAGE_USER')")
    public String delete(@RequestParam(name = "id") Long id) {
		
        userService.delete(id);
        return "redirect:/users";
    }
}