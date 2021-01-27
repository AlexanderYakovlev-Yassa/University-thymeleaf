package ua.foxminded.yakovlev.university.controller;

import java.security.Principal;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.context.support.ResourceBundleMessageSource;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import lombok.RequiredArgsConstructor;
import ua.foxminded.yakovlev.university.entity.User;
import ua.foxminded.yakovlev.university.service.impl.UserService;

@Controller
@RequiredArgsConstructor
@RequestMapping("/user")
public class UserController {
	
	private final UserService userService;
	
	@Qualifier(value="messageSource")
	private final ResourceBundleMessageSource messageSource;
	
	@GetMapping(produces="application/json")
    public ResponseEntity<String> showPositions(Principal principal) {
		
		String userFullName = "Unregistered user";
		
		if (principal != null) {
			User user = userService.findByUsername(principal.getName());	
			userFullName = user.getPerson().getFirstName() + " " + user.getPerson().getLastName();
		}
		
        return ResponseEntity.ok(userFullName);
    }
}