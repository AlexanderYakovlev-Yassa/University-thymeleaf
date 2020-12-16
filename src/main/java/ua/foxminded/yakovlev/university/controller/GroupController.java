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
import ua.foxminded.yakovlev.university.service.GroupService;
import ua.foxminded.yakovlev.university.service.StudentService;
import ua.foxminded.yakovlev.university.validator.GroupValidator;

@Controller
@RequiredArgsConstructor
@RequestMapping("/groups")
public class GroupController {
	
	private final GroupService groupService;
	private final StudentService studentService;
	private final GroupValidator groupValidator;
	@Qualifier(value="messageSource")
	private final ResourceBundleMessageSource messageSource;
	
	@GetMapping()
    public String showPositions(
			@RequestParam(name = "errorMessage", required = false) List<String> errorMessageList,
			@RequestParam(name = "activeWindow", required = false) String activeWindow,
			@RequestParam(name = "editingGroupId", required = false) Long editingGroupId,
			@RequestParam(name = "editingGroupName", required = false) String editingGroupName,
			Model model) {
		
		model.addAttribute("groups", groupService.findAll());
		model.addAttribute("errorMessageList", errorMessageList);
		model.addAttribute("activeWindow", activeWindow);
		model.addAttribute("editingGroupId", editingGroupId);
		model.addAttribute("editingGroupName", editingGroupName);
		
        return "groups/show-groups";
    }
	
	@PostMapping("/new")
    public String save(
			RedirectAttributes redirectAttributes,			
    		@RequestParam(name = "name") String name) {
		
		Group group = new Group();
		group.setName(name);
		
		final DataBinder dataBinder = new DataBinder(group);
		dataBinder.addValidators(groupValidator);
		dataBinder.validate();
		
		if (dataBinder.getBindingResult().hasErrors()) {
			
			List<String> errorMessageList = dataBinder.getBindingResult().getAllErrors().stream().
					map(e -> (messageSource.getMessage(e, Locale.getDefault())))
					.collect(Collectors.toList());
			
			redirectAttributes.addAttribute("errorMessage", errorMessageList);
			redirectAttributes.addAttribute("activeWindow", "save");
			redirectAttributes.addAttribute("editingGroupName", name);
			
			return "redirect:/groups";
		}
		
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
			RedirectAttributes redirectAttributes,
			@RequestParam(name = "id") Long id,
    		@RequestParam(name = "name") String name) {
		
		Group group = new Group();
		group.setId(id);
		group.setName(name);
		
		final DataBinder dataBinder = new DataBinder(group);
		dataBinder.addValidators(groupValidator);
		dataBinder.validate();
		
		if (dataBinder.getBindingResult().hasErrors()) {
			
			List<String> errorMessageList = dataBinder.getBindingResult().getAllErrors().stream().
					map(e -> (messageSource.getMessage(e, Locale.getDefault())))
					.collect(Collectors.toList());
			
			redirectAttributes.addAttribute("errorMessage", errorMessageList);
			redirectAttributes.addAttribute("activeWindow", "edit");
			redirectAttributes.addAttribute("editingGroupId", id);
			redirectAttributes.addAttribute("editingGroupName", name);
			
			return "redirect:/groups";
		}
		
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