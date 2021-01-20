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
import ua.foxminded.yakovlev.university.entity.Group;
import ua.foxminded.yakovlev.university.entity.Student;
import ua.foxminded.yakovlev.university.service.GroupService;
import ua.foxminded.yakovlev.university.service.StudentService;

@Controller
@RequiredArgsConstructor
@RequestMapping("/students")
public class StudentController {

	private final StudentService studentService;
	private final GroupService groupService;
	@Qualifier(value="messageSource")
	private final ResourceBundleMessageSource messageSource;

	@GetMapping()
	public String show(
			Model model) {
		
		model.addAttribute("students", studentService.findAll());

		return "students/show-students";
	}
	
	@GetMapping("/new")
    public String create(
			@RequestParam(name = "errorMessage", required = false) List<String> errorMessageList,
			Model model
			) {
		
		model.addAttribute("newStudent", new Student());
		model.addAttribute("groups", groupService.findAll());
		model.addAttribute("errorMessageList", errorMessageList);
		
		return "students/new-student";
	}
	
	@PostMapping("/new")
    public String save(
			RedirectAttributes redirectAttributes,
			@RequestParam(name = "groupId", required = false) Long groupId,
			@Valid@ModelAttribute("newStudent") Student  newStudent,
    		BindingResult bindingResult) {
		
		if (bindingResult.hasErrors()) {
			
			List<String> errorMessageList = bindingResult.getAllErrors().stream().
					map(e -> (messageSource.getMessage(e.getDefaultMessage(), null, Locale.getDefault())))
					.collect(Collectors.toList());
			
			redirectAttributes.addAttribute("errorMessage", errorMessageList);
			
			return "redirect:/students/new";
		}
		
		if (groupId != null) {
			Group group = groupService.findById(groupId);
			newStudent.setGroup(group);
		}
		
		studentService.save(newStudent);
		
		return "redirect:/students";
	}
	
	@GetMapping("/edit")
    public String showEditPage(
			@RequestParam(name = "errorMessage", required = false) List<String> errorMessageList,
    		@RequestParam(name = "id") Long id,
    		Model model
    		) {	

		model.addAttribute("student", studentService.findById(id));
		model.addAttribute("groups", groupService.findAll());
		model.addAttribute("errorMessageList", errorMessageList);
		
		return "students/edit-student";
	}
	
	@PostMapping("/edit")
    public String edit(
			RedirectAttributes redirectAttributes,
			@RequestParam(name = "groupId", required = false) Long groupId,
			@Valid@ModelAttribute("student") Student student,
    		BindingResult bindingResult) {
		
		if (bindingResult.hasErrors()) {
			
			List<String> errorMessageList = bindingResult.getAllErrors().stream().
					map(e -> (messageSource.getMessage(e.getDefaultMessage(), null, Locale.getDefault())))
					.collect(Collectors.toList());
			
			redirectAttributes.addAttribute("errorMessage", errorMessageList);
			redirectAttributes.addAttribute("id", student.getPersonId());
			
			return "redirect:/students/edit";
		}
		
		if (groupId != null) {
			Group group = groupService.findById(groupId);
			student.setGroup(group);
		}		
		
		studentService.update(student);
		
		return "redirect:/students";
	}

	@PostMapping("/delete")
	public String delete(@RequestParam(name = "person-to-be-deleted-id") long id) {
		studentService.delete(id);
		return "redirect:/students";
	}
}