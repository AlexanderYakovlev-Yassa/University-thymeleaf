package ua.foxminded.yakovlev.university.controller;

import java.util.List;
import java.util.Locale;
import java.util.stream.Collectors;

import javax.validation.Valid;

import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.context.support.ResourceBundleMessageSource;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

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
	@Qualifier(value="messageSource")
	private final ResourceBundleMessageSource messageSource;
	
	@GetMapping()
    public String showLecturers(Model model) {		
		model.addAttribute("lecturers", lecturerService.findAll());		
        return "lecturers/show-lecturers";
    }
	
	@GetMapping("/new")
    public String create(
			@RequestParam(name = "errorMessage", required = false) List<String> errorMessageList,
			Model model
			) {
		
		model.addAttribute("newLecturer", new Lecturer());
		model.addAttribute("positions", positionService.findAll());
		model.addAttribute("errorMessageList", errorMessageList);
		
		return "lecturers/new-lecturer";
	}
	
	@PostMapping("/new")
    public String save(
			RedirectAttributes redirectAttributes,
			@RequestParam(name = "positionId", required = false) Long positionId,
			@Valid@ModelAttribute("newLecturer") Lecturer newLecturer,
    		BindingResult bindingResult) {
		
		if (bindingResult.hasErrors()) {
			
			List<String> errorMessageList = bindingResult.getAllErrors().stream().
					map(e -> (messageSource.getMessage(e.getDefaultMessage(), null, Locale.getDefault())))
					.collect(Collectors.toList());
			
			redirectAttributes.addAttribute("errorMessage", errorMessageList);
			
			return "redirect:/lecturers/new";
		}
		
		if (positionId != null) {
			Position position = positionService.findById(positionId);
			newLecturer.setPosition(position);
		}
		
		lecturerService.save(newLecturer);
		
		return "redirect:/lecturers";
	}
	
	@GetMapping("/edit")
    public String showEditPage(
			@RequestParam(name = "errorMessage", required = false) List<String> errorMessageList,
    		@RequestParam(name = "id") Long id,
    		Model model
    		) {	

		model.addAttribute("lecturer", lecturerService.findById(id));
		model.addAttribute("positions", positionService.findAll());
		model.addAttribute("errorMessageList", errorMessageList);
		
		return "lecturers/edit-lecturer";
	}
	
	@PostMapping("/edit")
    public String edit(
			RedirectAttributes redirectAttributes,
			@RequestParam(name = "positionId", required = false) Long positionId,
			@Valid@ModelAttribute("lecturer") Lecturer lecturer,
    		BindingResult bindingResult) {
		
		if (bindingResult.hasErrors()) {
			
			List<String> errorMessageList = bindingResult.getAllErrors().stream().
					map(e -> (messageSource.getMessage(e.getDefaultMessage(), null, Locale.getDefault())))
					.collect(Collectors.toList());
			
			redirectAttributes.addAttribute("errorMessage", errorMessageList);
			redirectAttributes.addAttribute("id", lecturer.getPersonId());
			
			return "redirect:/lecturers/edit";
		}
		
		if (positionId != null) {
			Position position = positionService.findById(positionId);
			lecturer.setPosition(position);
		}		
		
		lecturerService.update(lecturer);
		
		return "redirect:/lecturers";
	}
	
	@PostMapping("/delete")
    public String delete(@RequestParam(name = "id") long id) {
		lecturerService.delete(id);
        return "redirect:/lecturers";
    }
}