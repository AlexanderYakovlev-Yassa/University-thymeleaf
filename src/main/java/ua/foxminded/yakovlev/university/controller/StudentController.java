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
import ua.foxminded.yakovlev.university.entity.Student;
import ua.foxminded.yakovlev.university.service.GroupService;
import ua.foxminded.yakovlev.university.service.StudentService;
import ua.foxminded.yakovlev.university.validator.StudentValidator;

@Controller
@RequiredArgsConstructor
@RequestMapping("/students")
public class StudentController {

	private static final String TABLE_NAME = "Все студенты";
	private final StudentService studentService;
	private final GroupService groupService;	
	private final StudentValidator studentValidator;
	@Qualifier(value="messageSource")
	private final ResourceBundleMessageSource messageSource;

	@GetMapping()
	public String show(
			@RequestParam(name = "errorMessage", required = false) List<String> errorMessageList,
			@RequestParam(name = "activeWindow", required = false) String activeWindow,
			@RequestParam(name = "editingStudentFirstName", required = false) String editingStudentFirstName,
			@RequestParam(name = "editingStudentLastName", required = false) String editingStudentLastName,
			@RequestParam(name = "editingStudentGroupId", required = false) Long editingStudentGroupId,
			@RequestParam(name = "editingStudentGroupName", required = false) String editingStudentGroupName,
			Model model) {
		
		model.addAttribute("students", studentService.findAll());
		model.addAttribute("groups", groupService.findAll());
		model.addAttribute("selectedStudent", new Student());
		model.addAttribute("tableName", TABLE_NAME);
		model.addAttribute("errorMessageList", errorMessageList);
		model.addAttribute("activeWindow", activeWindow);
		model.addAttribute("editingStudentFirstName", editingStudentFirstName);
		model.addAttribute("editingStudentLastName", editingStudentLastName);
		model.addAttribute("editingStudentGroupId", editingStudentGroupId);
		model.addAttribute("editingStudentGroupName", editingStudentGroupName);

		return "students/show-students";
	}

	@PostMapping("/new")
	public String save(
			RedirectAttributes redirectAttributes,
			@RequestParam(name = "first-name", required = false) String firstName,
			@RequestParam(name = "last-name", required = false) String lastName, 
			@RequestParam(name = "group-id", required = false) Long groupId,
			@RequestParam(name = "group-name", required = false) String groupName
			) {

		Student student = new Student();
		student.setFirstName(firstName);
		student.setLastName(lastName);
		
		final DataBinder dataBinder = new DataBinder(student);
		dataBinder.addValidators(studentValidator);
		dataBinder.validate();
		
		if (dataBinder.getBindingResult().hasErrors()) {
			
			List<String> errorMessageList = dataBinder.getBindingResult().getAllErrors().stream().
					map(e -> (messageSource.getMessage(e, Locale.getDefault())))
					.collect(Collectors.toList());
			
			redirectAttributes.addAttribute("errorMessage", errorMessageList);
			redirectAttributes.addAttribute("activeWindow", "save");
			redirectAttributes.addAttribute("editingStudentFirstName", firstName);
			redirectAttributes.addAttribute("editingStudentLastName", lastName);
			redirectAttributes.addAttribute("editingStudentGroupId", groupId);
			redirectAttributes.addAttribute("editingStudentGroupName", groupName);
			
			return "redirect:/students";
		}
		
		if (groupId != null) {
			Group group = new Group();
			group.setId(groupId);
			group.setName(groupName);
			student.setGroup(group);
		}
		
		studentService.save(student);

		return "redirect:/students";
	}

	@PostMapping("/delete")
	public String delete(@RequestParam(name = "person-to-be-deleted-id") long id) {
		studentService.delete(id);
		return "redirect:/students";
	}

	@PostMapping("/edit")
	public String edit(@RequestParam(name = "person-id") Long personId, @RequestParam(name = "group-id") Long groupId,
			@RequestParam(name = "first-name") String firstName, @RequestParam(name = "last-name") String lastName,
			@RequestParam(name = "group-name") String groupName) {

		Student student = new Student();
		student.setPersonId(personId);
		student.setFirstName(firstName);
		student.setLastName(lastName);
		Group group = new Group();
		group.setId(groupId);
		group.setName(groupName);
		student.setGroup(group);

		studentService.update(student);

		return "redirect:/students";
	}
}