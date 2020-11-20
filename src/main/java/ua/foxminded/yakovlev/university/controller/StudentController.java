package ua.foxminded.yakovlev.university.controller;

import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;

import lombok.RequiredArgsConstructor;
import ua.foxminded.yakovlev.university.service.StudentService;

@Controller
@RequiredArgsConstructor
@RequestMapping("/students")
public class StudentController {
	
	private final StudentService studentService;
	
	@GetMapping()
    public String showStudents(Model model) {
		
		model.addAttribute("students", studentService.findAll());
		
        return "students/show-students";
    }
}