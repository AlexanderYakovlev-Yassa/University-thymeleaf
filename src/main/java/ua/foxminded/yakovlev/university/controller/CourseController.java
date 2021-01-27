package ua.foxminded.yakovlev.university.controller;

import java.util.List;
import java.util.Locale;
import java.util.stream.Collectors;

import javax.validation.Valid;

import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.context.support.ResourceBundleMessageSource;
import org.springframework.security.access.prepost.PreAuthorize;
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
import ua.foxminded.yakovlev.university.entity.Course;
import ua.foxminded.yakovlev.university.service.CourseService;

@Controller
@RequiredArgsConstructor
@RequestMapping("/courses")
public class CourseController {
	
	private final CourseService courseService;
	@Qualifier(value="messageSource")
	private final ResourceBundleMessageSource messageSource;
	
	@GetMapping()
	@PreAuthorize("hasAuthority('READ_COURSE')")
    public String show(Model model) {

		model.addAttribute("courses", courseService.findAll());
		
        return "courses/show-courses";
    }
	
	@GetMapping("/new")
	@PreAuthorize("hasAuthority('MANAGE_COURSE')")
	public String create(
			@RequestParam(name = "errorMessage", required = false) List<String> errorMessageList,
			Model model) {		
		
		model.addAttribute("course", new Course());
		model.addAttribute("errorMessageList", errorMessageList);
		
		return "courses/new-course";
	}
	
	@PostMapping("/new")
	@PreAuthorize("hasAuthority('MANAGE_COURSE')")
    public String save(
			RedirectAttributes redirectAttributes,
    		@Valid@ModelAttribute("course") Course  course,
    		BindingResult bindingResult) {		
		
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
	
	@GetMapping("/edit")
	@PreAuthorize("hasAuthority('MODIFY_COURSE')")
    public String edit(
			@RequestParam(name = "errorMessage", required = false) List<String> errorMessageList,
    		@RequestParam(name = "id") Long id,
    		Model model
    		) {
		
		model.addAttribute("course", courseService.findById(id));
		model.addAttribute("errorMessageList", errorMessageList);
		
		return "courses/edit-course";
	}
	
	@PostMapping("/edit")
	@PreAuthorize("hasAuthority('MODIFY_COURSE')")
	public String update(
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
	
	@PostMapping("/delete")
	@PreAuthorize("hasAuthority('MANAGE_COURSE')")
    public String delete(@RequestParam(name = "id") Long id) {
		
        courseService.delete(id);
        return "redirect:/courses";
    }
}