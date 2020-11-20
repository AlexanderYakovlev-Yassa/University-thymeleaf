package ua.foxminded.yakovlev.university.controller;

import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;

import lombok.RequiredArgsConstructor;
import ua.foxminded.yakovlev.university.service.LecturerService;

@Controller
@RequiredArgsConstructor
@RequestMapping("/lecturers")
public class LecturerController {
	
	private final LecturerService lecturerService;
	
	@GetMapping()
    public String showLecturers(Model model) {
		
		model.addAttribute("lecturers", lecturerService.findAll());
		
        return "lecturers/show-lecturers";
    }
}