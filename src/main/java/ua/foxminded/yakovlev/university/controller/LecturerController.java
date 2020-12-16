package ua.foxminded.yakovlev.university.controller;

import java.util.List;
import java.util.Locale;
import java.util.stream.Collectors;

import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.context.support.ResourceBundleMessageSource;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.DataBinder;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

import lombok.RequiredArgsConstructor;
import ua.foxminded.yakovlev.university.entity.Group;
import ua.foxminded.yakovlev.university.entity.Lecturer;
import ua.foxminded.yakovlev.university.entity.Position;
import ua.foxminded.yakovlev.university.service.LecturerService;
import ua.foxminded.yakovlev.university.service.PositionService;
import ua.foxminded.yakovlev.university.validator.LecturerValidator;
import ua.foxminded.yakovlev.university.validator.StudentValidator;

@Controller
@RequiredArgsConstructor
@RequestMapping("/lecturers")
public class LecturerController {
	
	private final LecturerService lecturerService;
	private final PositionService positionService;
	private final LecturerValidator lecturerValidator;
	@Qualifier(value="messageSource")
	private final ResourceBundleMessageSource messageSource;
	
	@GetMapping()
    public String showLecturers(
			@RequestParam(name = "errorMessage", required = false) List<String> errorMessageList,
			@RequestParam(name = "activeWindow", required = false) String activeWindow,
			@RequestParam(name = "editingPersonId", required = false) Long editingPersonId,
			@RequestParam(name = "editingLecturerFirstName", required = false) String editingLecturerFirstName,
			@RequestParam(name = "editingLecturerLastName", required = false) String editingLecturerLastName,
			@RequestParam(name = "editingLecturerPositionId", required = false) Long editingLecturerPositionId,
			@RequestParam(name = "editingLecturerPositionName", required = false) String editingLecturerPositionName,
			Model model) {
		
		model.addAttribute("lecturers", lecturerService.findAll());
		model.addAttribute("positions", positionService.findAll());
		model.addAttribute("errorMessageList", errorMessageList);
		model.addAttribute("activeWindow", activeWindow);
		model.addAttribute("editingPersonId", editingPersonId);
		model.addAttribute("editingLecturerFirstName", editingLecturerFirstName);
		model.addAttribute("editingLecturerLastName", editingLecturerLastName);
		model.addAttribute("editingLecturerPositionId", editingLecturerPositionId);
		model.addAttribute("editingLecturerPositionName", editingLecturerPositionName);
		
        return "lecturers/show-lecturers";
    }
	
	@PostMapping("/new")
    public String save(
			RedirectAttributes redirectAttributes,
    		@RequestParam(name = "first-name", required = false) String firstName,
    		@RequestParam(name = "last-name", required = false) String lastName,
    		@RequestParam(name = "position-id", required = false) Long positionId,
    		@RequestParam(name = "position-name", required = false) String positionName
    		) {
		
		Lecturer lecturer = new Lecturer();
		lecturer.setFirstName(firstName);
		lecturer.setLastName(lastName);
		
		if (positionId != null) {
			lecturer.setPosition(createPosition(positionId, positionName));
		}
		
		final DataBinder dataBinder = new DataBinder(lecturer);
		dataBinder.addValidators(lecturerValidator);
		dataBinder.validate();
		
		if (dataBinder.getBindingResult().hasErrors()) {
			
			List<String> errorMessageList = dataBinder.getBindingResult().getAllErrors().stream().
					map(e -> (messageSource.getMessage(e, Locale.getDefault())))
					.collect(Collectors.toList());
			
			redirectAttributes.addAttribute("errorMessage", errorMessageList);
			redirectAttributes.addAttribute("activeWindow", "save");
			redirectAttributes.addAttribute("editingLecturerFirstName", firstName);
			redirectAttributes.addAttribute("editingLecturerLastName", lastName);
			redirectAttributes.addAttribute("editingLecturerPositionId", positionId);
			redirectAttributes.addAttribute("editingLecturerPositionName", positionName);
			
			return "redirect:/lecturers";
		}
		
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
			RedirectAttributes redirectAttributes,
    		@RequestParam(name = "person-id", required = false) Long personId,
    		@RequestParam(name = "position-id", required = false) Long positionId,
    		@RequestParam(name = "first-name", required = false) String firstName,
    		@RequestParam(name = "last-name", required = false) String lastName,
    		@RequestParam(name = "position-name", required = false) String positionName) {
		
		Lecturer lecturer = new Lecturer();
		lecturer.setPersonId(personId);
		lecturer.setFirstName(firstName);
		lecturer.setLastName(lastName);
		
		if (positionId != null) {
			lecturer.setPosition(createPosition(positionId, positionName));
		}

		final DataBinder dataBinder = new DataBinder(lecturer);
		dataBinder.addValidators(lecturerValidator);
		dataBinder.validate();
		
		if (dataBinder.getBindingResult().hasErrors()) {
			
			List<String> errorMessageList = dataBinder.getBindingResult().getAllErrors().stream().
					map(e -> (messageSource.getMessage(e, Locale.getDefault())))
					.collect(Collectors.toList());
			
			redirectAttributes.addAttribute("errorMessage", errorMessageList);
			redirectAttributes.addAttribute("activeWindow", "edit");			
			redirectAttributes.addAttribute("editingPersonId", personId);
			redirectAttributes.addAttribute("editingLecturerFirstName", firstName);
			redirectAttributes.addAttribute("editingLecturerLastName", lastName);
			redirectAttributes.addAttribute("editingLecturerPositionId", positionId);
			redirectAttributes.addAttribute("editingLecturerPositionName", positionName);
			
			return "redirect:/lecturers";
		}
		
		
		lecturerService.update(lecturer);
		
		return "redirect:/lecturers";
	}
	
	private Position createPosition(Long lecturerId, String lecturerName) {
		Position position = new Position();
		position.setId(lecturerId);
		position.setName(lecturerName);
		return position;
	}
}