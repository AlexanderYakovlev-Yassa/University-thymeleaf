package ua.foxminded.yakovlev.university.controller;

import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;

import lombok.RequiredArgsConstructor;
import ua.foxminded.yakovlev.university.entity.Course;
import ua.foxminded.yakovlev.university.service.CourseService;

@Controller
@RequiredArgsConstructor
@RequestMapping("/courses")
public class CourseController {
	
	private final CourseService courseService;
	
	@GetMapping()
    public String show(Model model) {

		model.addAttribute("courses", courseService.findAll());
		
        return "courses/show-courses";
    }
	
	@PostMapping("/new")
    public String save(
    		@RequestParam(name = "name") String name,
    		@RequestParam(name = "description") String description) {
		
		Course course = new Course();
		course.setName(name);
		course.setDescription(description);
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
    		@RequestParam(name = "id") Long id,
    		@RequestParam(name = "name") String name,
    		@RequestParam(name = "description") String description) {
		
		Course course = new Course();
		course.setId(id);
		course.setName(name);
		course.setDescription(description);
		courseService.update(course);
		
		return "redirect:/courses";
	}
}