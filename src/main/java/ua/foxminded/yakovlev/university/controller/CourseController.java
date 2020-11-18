package ua.foxminded.yakovlev.university.controller;

import java.util.List;

import org.springframework.context.ApplicationContext;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;

import ua.foxminded.yakovlev.university.entity.Course;
import ua.foxminded.yakovlev.university.service.CourseService;

@Controller
@RequestMapping("/courses")
public class CourseController {
	
	private final ApplicationContext context;
	
	public CourseController(ApplicationContext context) {
		this.context = context;
	}
	
	@GetMapping()
    public String showCourses(Model model) {
		
		List<Course> courses = context.getBean("courseService", CourseService.class).findAll();
		model.addAttribute("courses", courses);
		
        return "courses/show-courses";
    }
}