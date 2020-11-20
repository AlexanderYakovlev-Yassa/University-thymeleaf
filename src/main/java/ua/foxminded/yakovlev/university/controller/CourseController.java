package ua.foxminded.yakovlev.university.controller;

import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;

import lombok.RequiredArgsConstructor;
import ua.foxminded.yakovlev.university.service.CourseService;

@Controller
@RequiredArgsConstructor
@RequestMapping("/courses")
public class CourseController {
	
	private final CourseService courseService;
	
	@GetMapping()
    public String showCourses(Model model) {

		model.addAttribute("courses", courseService.findAll());
		
        return "courses/show-courses";
    }
}