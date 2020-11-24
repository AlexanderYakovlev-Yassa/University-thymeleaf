package ua.foxminded.yakovlev.university.controller;

import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;

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
	
	@GetMapping()
    public String show(Model model) {
		
		model.addAttribute("students", studentService.findAll());
		model.addAttribute("groups", groupService.findAll());
		model.addAttribute("selectedStudent", new Student());
		
        return "students/show-students";
    }
	
	@PostMapping("/new")
    public String save(
    		@RequestParam(name = "first-name") String firstName,
    		@RequestParam(name = "last-name") String lastName,
    		@RequestParam(name = "group-id") Long groupId,
    		@RequestParam(name = "group-name") String groupName) {
		
		Student student = new Student();
		student.setFirstName(firstName);
		student.setLastName(lastName);
		Group group = new Group();
		group.setId(groupId);
		group.setName(groupName);
		student.setGroup(group);
		studentService.save(student);
		
		return "redirect:/students";
	}
	
	@PostMapping("/delete")
    public String delete(@RequestParam(name = "student-to-be-deleted-id") long id) {
        studentService.delete(id);
        return "redirect:/students";
    }
	
	@PostMapping("/edit")
    public String edit(
    		@RequestParam(name = "student-id") Long studentId,
    		@RequestParam(name = "person-id") Long personId,
    		@RequestParam(name = "group-id") Long groupId,
    		@RequestParam(name = "first-name") String firstName,
    		@RequestParam(name = "last-name") String lastName,
    		@RequestParam(name = "group-name") String groupName) {
		
		Student student = new Student();
		student.setPersonId(personId);
		student.setStudentId(studentId);
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