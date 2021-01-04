package ua.foxminded.yakovlev.university.controller.api;

import java.util.List;
import javax.validation.Valid;

import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.context.support.ResourceBundleMessageSource;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;
import lombok.RequiredArgsConstructor;
import ua.foxminded.yakovlev.university.dto.StudentDto;
import ua.foxminded.yakovlev.university.entity.Student;
import ua.foxminded.yakovlev.university.mapper.StudentMapper;
import ua.foxminded.yakovlev.university.service.StudentService;

@RestController
@RequiredArgsConstructor
@RequestMapping("/api/students")
public class ApiStudentController {

	private final StudentService studentService;
	private final StudentMapper studentMapper;
	@Qualifier(value="messageSource")
	private final ResourceBundleMessageSource messageSource;
	
	@GetMapping
    public ResponseEntity<List<StudentDto>> findAll() {		
		
        return ResponseEntity.ok(studentMapper.toStudentDtos(studentService.findAll()));
    }
	
	@PostMapping
    public ResponseEntity<?> create(@Valid@RequestBody StudentDto studentDto) {
		
		System.out.println(studentDto);
		System.out.println(studentMapper.toStudent(studentDto));
		
        Student student = studentService.save(studentMapper.toStudent(studentDto));
        
        return ResponseEntity.status(HttpStatus.CREATED).body(studentMapper.toStudentDto(student));
    }
	
	@GetMapping("/{id}")
    public ResponseEntity<?> findById(@PathVariable Long id) {

        Student student = studentService.findById(id);
               
        return ResponseEntity.ok(studentMapper.toStudentDto(student));
    }
	
	@CrossOrigin(methods = RequestMethod.PUT)
	@PutMapping("/{id}")
    public ResponseEntity<?> update(@PathVariable Long id, @Valid@RequestBody StudentDto studentDto) {
		
        Student student = studentMapper.toStudent(studentDto);
        student.setPersonId(id);

        return ResponseEntity.status(HttpStatus.ACCEPTED).body(studentMapper.toStudentDto(studentService.save(student)));
    }

	@CrossOrigin(methods = RequestMethod.DELETE)
	@DeleteMapping("/{id}")
    public ResponseEntity<String> delete(@PathVariable Long id) {
        studentService.delete(id);
        
        return ResponseEntity.status(HttpStatus.ACCEPTED).build();
    }
	
	@GetMapping("/group/{id}")
    public ResponseEntity<List<StudentDto>> findByGroup(@PathVariable Long id) {		
        return ResponseEntity.ok(studentMapper.toStudentDtos(studentService.findByGroupId(id)));
    }
}