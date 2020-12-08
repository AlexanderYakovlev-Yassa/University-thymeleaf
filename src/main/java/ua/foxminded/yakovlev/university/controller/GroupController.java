package ua.foxminded.yakovlev.university.controller;

import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;

import lombok.RequiredArgsConstructor;
import ua.foxminded.yakovlev.university.entity.Group;
import ua.foxminded.yakovlev.university.service.GroupService;

@Controller
@RequiredArgsConstructor
@RequestMapping("/groups")
public class GroupController {
	
	private final GroupService groupService;
	
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
}