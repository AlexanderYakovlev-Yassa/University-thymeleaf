package ua.foxminded.yakovlev.university.controller;

import java.util.List;

import org.springframework.context.ApplicationContext;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;

import ua.foxminded.yakovlev.university.entity.Lecturer;
import ua.foxminded.yakovlev.university.service.LecturerService;

@Controller
@RequestMapping("/lecturers")
public class LecturerController {
	
	private final ApplicationContext context;
	
	public LecturerController(ApplicationContext context) {
		this.context = context;
	}
	
	@GetMapping()
    public String showLecturers(Model model) {
		
		List<Lecturer> lecturers = context.getBean("lecturerService", LecturerService.class).findAll();
		model.addAttribute("lecturers", lecturers);
		
        return "lecturers/show-lecturers";
    }
}