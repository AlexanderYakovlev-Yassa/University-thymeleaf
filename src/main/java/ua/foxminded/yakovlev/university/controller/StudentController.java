package ua.foxminded.yakovlev.university.controller;

import java.util.List;

import org.springframework.context.ApplicationContext;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;

import ua.foxminded.yakovlev.university.entity.Student;
import ua.foxminded.yakovlev.university.service.StudentService;

@Controller
@RequestMapping("/students")
public class StudentController {
	
	private final ApplicationContext context;
	
	public StudentController(ApplicationContext context) {
		this.context = context;
	}
	
	@GetMapping()
    public String showStudents(Model model) {
		
		List<Student> students = context.getBean("studentService", StudentService.class).findAll();
		model.addAttribute("students", students);
		
        return "students/show-students";
    }
}