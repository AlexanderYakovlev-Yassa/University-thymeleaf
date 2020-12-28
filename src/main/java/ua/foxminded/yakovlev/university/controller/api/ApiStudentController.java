package ua.foxminded.yakovlev.university.controller.api;

import java.util.List;
import java.util.Locale;
import javax.validation.Valid;

import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.context.support.ResourceBundleMessageSource;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.BindingResult;
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
import ua.foxminded.yakovlev.university.util.ErrorMessageHandler;

@RestController
@RequiredArgsConstructor
@RequestMapping("/api/students")
public class ApiStudentController {
	
	private static final String NOT_FOUND = "api.message.student_not_found";
	private final StudentService studentService;
	private final StudentMapper studentMapper;
	private final ErrorMessageHandler errorMessageHandler;
	@Qualifier(value="messageSource")
	private final ResourceBundleMessageSource messageSource;
	
	@GetMapping
    public ResponseEntity<List<StudentDto>> findAll() {		
		
        return ResponseEntity.ok(studentMapper.toStudentDtos(studentService.findAll()));
    }
	
	@PostMapping
    public ResponseEntity<?> create(@Valid@RequestBody StudentDto studentDto,
    		BindingResult bindingResult) {
		
		if (bindingResult.hasErrors()) {
			return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(errorMessageHandler.handle(bindingResult));
		}
		
        Student student = studentService.save(studentMapper.toStudent(studentDto));
        
        return ResponseEntity.status(HttpStatus.CREATED).body(studentMapper.toStudentDto(student));
    }
	
	@GetMapping("/{id}")
    public ResponseEntity<?> findById(@PathVariable Long id) {

        Student student = studentService.findById(id);
        
        if (student == null) {
        	return ResponseEntity.status(HttpStatus.NOT_FOUND).body(messageSource.getMessage(NOT_FOUND, null, Locale.getDefault()));
        }
               
        return ResponseEntity.ok(studentMapper.toStudentDto(student));
    }
	
	@CrossOrigin(methods = RequestMethod.PUT)
	@PutMapping("/{id}")
    public ResponseEntity<?> update(@PathVariable Long id, @Valid@RequestBody StudentDto studentDto, 
    		BindingResult bindingResult) {

		if (bindingResult.hasErrors()) {
			return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(errorMessageHandler.handle(bindingResult));
		}
		
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
}