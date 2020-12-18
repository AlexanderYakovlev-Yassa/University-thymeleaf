package ua.foxminded.yakovlev.university.controller;

import java.util.List;
import java.util.Locale;
import java.util.Set;
import java.util.stream.Collectors;

import javax.validation.ConstraintViolation;
import javax.validation.Valid;

import org.hibernate.internal.build.AllowSysOut;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.context.support.ResourceBundleMessageSource;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.validation.DataBinder;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

import lombok.RequiredArgsConstructor;
import ua.foxminded.yakovlev.university.entity.Course;
import ua.foxminded.yakovlev.university.service.CourseService;
import ua.foxminded.yakovlev.university.validator.CourseValidator;

@Controller
@RequiredArgsConstructor
@RequestMapping("/courses")
public class CourseController {
	
	private final CourseService courseService;
	private final CourseValidator courseValidator;
	@Qualifier(value="messageSource")
	private final ResourceBundleMessageSource messageSource;
	
	@GetMapping()
    public String show(
			@RequestParam(name = "errorMessage", required = false) List<String> errorMessageList,
			@RequestParam(name = "editingCourseId", required = false) Course editingCourse,
			Model model) {

		model.addAttribute("courses", courseService.findAll());
		model.addAttribute("errorMessageList", errorMessageList);
		model.addAttribute("editingCourse", editingCourse);
		
        return "courses/show-courses";
    }
	
	@GetMapping("/new")
	public String create(
			@RequestParam(name = "errorMessage", required = false) List<String> errorMessageList,
			Model model) {
		
		model.addAttribute("course", new Course());
		model.addAttribute("errorMessageList", errorMessageList);
		
		return "courses/new-course";
	}
	
	@PostMapping("/new")
    public String save(
			RedirectAttributes redirectAttributes,
    		@Valid@ModelAttribute("course") Course  course,
    		BindingResult bindingResult,
			Model model) {		
		
		if (bindingResult.hasErrors()) {
			
			List<String> errorMessageList = bindingResult.getAllErrors().stream().
					map(e -> (messageSource.getMessage(e.getDefaultMessage(), null, Locale.getDefault())))
					.collect(Collectors.toList());
			
			redirectAttributes.addAttribute("errorMessage", errorMessageList);
			
			return "redirect:/courses/new";
		}
		
		courseService.save(course);
		
		return "redirect:/courses";
	}	
	
	@PostMapping("/delete")
    public String delete(@RequestParam(name = "id") long id) {
        courseService.delete(id);
        return "redirect:/courses";
    }
	
	@PostMapping("/edit")
	public String showEdit(
			RedirectAttributes redirectAttributes,
			@Valid@ModelAttribute("course") Course course,
    		BindingResult bindingResult) {
		
		if (bindingResult.hasErrors()) {
			
			List<String> errorMessageList = bindingResult.getAllErrors().stream().
					map(e -> (messageSource.getMessage(e.getDefaultMessage(), null, Locale.getDefault())))
					.collect(Collectors.toList());
			
			redirectAttributes.addAttribute("errorMessage", errorMessageList);
			redirectAttributes.addAttribute("id", course.getId());
			
			return "redirect:/courses/edit";
		}
		
		courseService.update(course);
		
		return "redirect:/courses";
	}
	
	@GetMapping("/edit")
    public String edit(
			@RequestParam(name = "errorMessage", required = false) List<String> errorMessageList,
    		@RequestParam(name = "id") Long id,
    		Model model
    		) {		
		
		model.addAttribute("course", courseService.findById(id));
		model.addAttribute("errorMessageList", errorMessageList);
		
		return "courses/new-course";
	}
}