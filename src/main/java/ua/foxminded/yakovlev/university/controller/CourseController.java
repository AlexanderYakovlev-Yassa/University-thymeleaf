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
import ua.foxminded.yakovlev.university.entity.Course;
import ua.foxminded.yakovlev.university.service.CourseService;
import ua.foxminded.yakovlev.university.validator.CourseValidator;
import ua.foxminded.yakovlev.university.validator.GroupValidator;

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
			@RequestParam(name = "activeWindow", required = false) String activeWindow,
			@RequestParam(name = "editingCourseId", required = false) Long editingCourseId,
			@RequestParam(name = "editingCourseName", required = false) String editingCourseName,
			@RequestParam(name = "editingCourseDescription", required = false) String editingCourseDescription,
			Model model) {

		model.addAttribute("courses", courseService.findAll());
		model.addAttribute("errorMessageList", errorMessageList);
		model.addAttribute("activeWindow", activeWindow);
		model.addAttribute("editingCourseId", editingCourseId);
		model.addAttribute("editingCourseName", editingCourseName);
		model.addAttribute("editingCourseDescription", editingCourseDescription);
		
        return "courses/show-courses";
    }
	
	@PostMapping("/new")
    public String save(
			RedirectAttributes redirectAttributes,
    		@RequestParam(name = "name") String name,
    		@RequestParam(name = "description") String description) {
		
		Course course = new Course();
		course.setName(name);
		course.setDescription(description);
		
		final DataBinder dataBinder = new DataBinder(course);
		dataBinder.addValidators(courseValidator);
		dataBinder.validate();
		
		if (dataBinder.getBindingResult().hasErrors()) {
			
			List<String> errorMessageList = dataBinder.getBindingResult().getAllErrors().stream().
					map(e -> (messageSource.getMessage(e, Locale.getDefault())))
					.collect(Collectors.toList());
			
			redirectAttributes.addAttribute("errorMessage", errorMessageList);
			redirectAttributes.addAttribute("activeWindow", "save");
			redirectAttributes.addAttribute("editingCourseName", name);
			redirectAttributes.addAttribute("editingCourseDescription", description);
			
			return "redirect:/courses";
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
    public String edit(
			RedirectAttributes redirectAttributes,
    		@RequestParam(name = "id") Long id,
    		@RequestParam(name = "name") String name,
    		@RequestParam(name = "description") String description) {
		
		Course course = new Course();
		course.setId(id);
		course.setName(name);
		course.setDescription(description);
		
		final DataBinder dataBinder = new DataBinder(course);
		dataBinder.addValidators(courseValidator);
		dataBinder.validate();
		
		if (dataBinder.getBindingResult().hasErrors()) {
			
			List<String> errorMessageList = dataBinder.getBindingResult().getAllErrors().stream().
					map(e -> (messageSource.getMessage(e, Locale.getDefault())))
					.collect(Collectors.toList());
			
			redirectAttributes.addAttribute("errorMessage", errorMessageList);
			redirectAttributes.addAttribute("activeWindow", "edit");
			redirectAttributes.addAttribute("editingCourseId", id);
			redirectAttributes.addAttribute("editingCourseName", name);
			redirectAttributes.addAttribute("editingCourseDescription", description);
			
			return "redirect:/courses";
		}
		
		courseService.update(course);
		
		return "redirect:/courses";
	}
}