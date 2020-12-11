package ua.foxminded.yakovlev.university.controller;

import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

import lombok.RequiredArgsConstructor;
import ua.foxminded.yakovlev.university.entity.Group;
import ua.foxminded.yakovlev.university.service.GroupService;
import ua.foxminded.yakovlev.university.service.StudentService;

@Controller
@RequiredArgsConstructor
@RequestMapping("/groups")
public class GroupController {
	
	private final GroupService groupService;
	private final StudentService studentService;
	
	@GetMapping()
    public String showPositions(Model model) {
		
		model.addAttribute("groups", groupService.findAll());
		
        return "groups/show-groups";
    }
	
	@PostMapping("/new")
    public String save(
    		@RequestParam(name = "name") String name) {
		
		Group group = new Group();
		group.setName(name);
		groupService.save(group);
		
		return "redirect:/groups";
	}
	
	@PostMapping("/delete")
    public String delete(@RequestParam(name = "id") long id) {
		groupService.delete(id);
        return "redirect:/groups";
    }
	
	@PostMapping("/edit")
    public String edit(
    		@RequestParam(name = "id") Long id,
    		@RequestParam(name = "name") String name) {
		
		Group group = new Group();
		group.setId(id);
		group.setName(name);
		groupService.update(group);
		
		return "redirect:/groups";
	}
	
	@GetMapping("/manage")
    public String manage(Model model,
    		@RequestParam(name = "group-id") Long groupId) {
		
		model.addAttribute("group", groupService.findById(groupId));
		model.addAttribute("students", studentService.findByGroupId(groupId));
		model.addAttribute("groups", groupService.findAll());
		model.addAttribute("studentsWithoutGroup", studentService.findStudentsWithoutGroup());
		
		return "groups/manage-group";
	}
	
	@PostMapping("/add-student")
    public String addStudent (RedirectAttributes redirectAttributes,
    		@RequestParam(name = "groupId") Long groupID,
    		@RequestParam(name = "studentId") Long studentId) {
		
		studentService.addGroup(studentId, groupID);
		redirectAttributes.addAttribute("group-id", groupID);
		
		return "redirect:/groups/manage";
	}
	
	@PostMapping("/remove-student")
    public String removeStudent (RedirectAttributes redirectAttributes,
    		@RequestParam(name = "groupId") Long groupID,
    		@RequestParam(name = "studentId") Long studentId) {
		
		studentService.removeGroup(studentId);
		redirectAttributes.addAttribute("group-id", groupID);
		
		return "redirect:/groups/manage";
	}
}