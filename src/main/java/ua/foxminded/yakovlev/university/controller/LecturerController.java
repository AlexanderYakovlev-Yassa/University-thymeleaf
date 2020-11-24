package ua.foxminded.yakovlev.university.controller;

import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;

import lombok.RequiredArgsConstructor;
import ua.foxminded.yakovlev.university.entity.Lecturer;
import ua.foxminded.yakovlev.university.entity.Position;
import ua.foxminded.yakovlev.university.service.LecturerService;
import ua.foxminded.yakovlev.university.service.PositionService;

@Controller
@RequiredArgsConstructor
@RequestMapping("/lecturers")
public class LecturerController {
	
	private final LecturerService lecturerService;
	private final PositionService positionService;
	
	@GetMapping()
    public String showLecturers(Model model) {
		
		model.addAttribute("lecturers", lecturerService.findAll());
		model.addAttribute("positions", positionService.findAll());
		
        return "lecturers/show-lecturers";
    }
	
	@PostMapping("/new")
    public String save(
    		@RequestParam(name = "first-name") String firstName,
    		@RequestParam(name = "last-name") String lastName,
    		@RequestParam(name = "position-id") Long positionId) {
		
		Lecturer lecturer = new Lecturer();
		lecturer.setFirstName(firstName);
		lecturer.setLastName(lastName);
		Position position = new Position();
		position.setId(positionId);
		lecturer.setPosition(position);
		lecturerService.save(lecturer);
		
		return "redirect:/lecturers";
	}
	
	@PostMapping("/delete")
    public String delete(@RequestParam(name = "id") long id) {
		lecturerService.delete(id);
        return "redirect:/lecturers";
    }
	
	@PostMapping("/edit")
    public String edit(
    		@RequestParam(name = "lecturer-id") Long lecturerId,
    		@RequestParam(name = "person-id") Long personId,
    		@RequestParam(name = "position-id") Long positionId,
    		@RequestParam(name = "first-name") String firstName,
    		@RequestParam(name = "last-name") String lastName,
    		@RequestParam(name = "position-name") String positionName) {
		
		Lecturer lecturer = new Lecturer();
		lecturer.setPersonId(personId);
		lecturer.setLecturerId(lecturerId);
		lecturer.setFirstName(firstName);
		lecturer.setLastName(lastName);
		Position position = new Position();
		position.setId(positionId);
		position.setName(positionName);
		lecturer.setPosition(position);
		
		lecturerService.update(lecturer);
		
		return "redirect:/lecturers";
	}
}