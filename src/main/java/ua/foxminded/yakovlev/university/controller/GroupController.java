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
import ua.foxminded.yakovlev.university.entity.Group;
import ua.foxminded.yakovlev.university.service.GroupService;
import ua.foxminded.yakovlev.university.service.StudentService;

@Controller
@RequiredArgsConstructor
@RequestMapping("/groups")
public class GroupController {
	
	private final GroupService groupService;
	private final StudentService studentService;
	@Qualifier(value="messageSource")
	private final ResourceBundleMessageSource messageSource;
	
	@GetMapping()
	@PreAuthorize("hasAuthority('READ_GROUP')")
    public String showPositions(
			Model model) {
		
		model.addAttribute("groups", groupService.findAll());
		
        return "groups/show-groups";
    }
	
	@GetMapping("/new")
	@PreAuthorize("hasAuthority('MANAGE_GROUP')")
    public String create(
			@RequestParam(name = "errorMessage", required = false) List<String> errorMessageList,
			Model model
			) {
		
		model.addAttribute("newGroup", new Group());
		model.addAttribute("errorMessageList", errorMessageList);
		
		return "groups/new-group";
	}
	
	@PostMapping("/new")
	@PreAuthorize("hasAuthority('MANAGE_GROUP')")
    public String save(
			RedirectAttributes redirectAttributes,			
			@Valid@ModelAttribute("newGroup") Group  newGroup,
    		BindingResult bindingResult) {
		
		if (bindingResult.hasErrors()) {
			
			List<String> errorMessageList = bindingResult.getAllErrors().stream().
					map(e -> (messageSource.getMessage(e.getDefaultMessage(), null, Locale.getDefault())))
					.collect(Collectors.toList());
			
			redirectAttributes.addAttribute("errorMessage", errorMessageList);
			
			return "redirect:/groups/new";
		}
		
		groupService.save(newGroup);
		
		return "redirect:/groups";
	}
	
	@GetMapping("/edit")
	@PreAuthorize("hasAuthority('MODIFY_GROUP')")
    public String showEditPage(
			@RequestParam(name = "errorMessage", required = false) List<String> errorMessageList,
    		@RequestParam(name = "id") Long id,
    		Model model
    		) {	

		model.addAttribute("group", groupService.findById(id));
		model.addAttribute("errorMessageList", errorMessageList);
		
		return "groups/edit-group";
	}
	
	@PostMapping("/edit")
	@PreAuthorize("hasAuthority('MODIFY_GROUP')")
    public String edit(
			RedirectAttributes redirectAttributes,
			@Valid@ModelAttribute("group") Group group,
    		BindingResult bindingResult) {
		
		if (bindingResult.hasErrors()) {
			
			List<String> errorMessageList = bindingResult.getAllErrors().stream().
					map(e -> (messageSource.getMessage(e.getDefaultMessage(), null, Locale.getDefault())))
					.collect(Collectors.toList());
			
			redirectAttributes.addAttribute("errorMessage", errorMessageList);
			redirectAttributes.addAttribute("id", group.getId());
			
			return "redirect:/groups/edit";
		}
		
		groupService.update(group);
		
		return "redirect:/groups";
	}
	
	@GetMapping("/manage")
	@PreAuthorize("hasAuthority('MODIFY_GROUP')")
    public String manage(Model model,
    		@RequestParam(name = "group-id") Long groupId) {
		
		model.addAttribute("group", groupService.findById(groupId));
		model.addAttribute("students", studentService.findByGroupId(groupId));
		model.addAttribute("groups", groupService.findAll());
		model.addAttribute("studentsWithoutGroup", studentService.findStudentsWithoutGroup());
		
		return "groups/manage-group";
	}
	
	@PostMapping("/add-student")
	@PreAuthorize("hasAuthority('MANAGE_GROUP')")
    public String addStudent (RedirectAttributes redirectAttributes,
    		@RequestParam(name = "groupId") Long groupID,
    		@RequestParam(name = "studentId") Long studentId) {
		
		studentService.addGroup(studentId, groupID);
		redirectAttributes.addAttribute("group-id", groupID);
		
		return "redirect:/groups/manage";
	}
	
	@PostMapping("/remove-student")
	@PreAuthorize("hasAuthority('MANAGE_GROUP')")
    public String removeStudent (RedirectAttributes redirectAttributes,
    		@RequestParam(name = "groupId") Long groupId,
    		@RequestParam(name = "studentId") Long studentId) {
		
		studentService.removeGroup(studentId, groupId);
		redirectAttributes.addAttribute("group-id", groupId);
		
		return "redirect:/groups/manage";
	}
	
	@PostMapping("/delete")
	@PreAuthorize("hasAuthority('MANAGE_GROUP')")
    public String delete(@RequestParam(name = "id") long id) {
		groupService.delete(id);
        return "redirect:/groups";
    }
}