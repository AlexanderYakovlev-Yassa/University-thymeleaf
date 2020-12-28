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
import ua.foxminded.yakovlev.university.dto.LecturerDto;
import ua.foxminded.yakovlev.university.entity.Lecturer;
import ua.foxminded.yakovlev.university.mapper.LecturerMapper;
import ua.foxminded.yakovlev.university.service.LecturerService;
import ua.foxminded.yakovlev.university.util.ErrorMessageHandler;

@RestController
@RequiredArgsConstructor
@RequestMapping("/api/lecturers")
public class ApiLecturerController {
	
	private static final String NOT_FOUND = "api.message.lecturer_not_found";
	private final LecturerService lecturerService;
	private final LecturerMapper lecturerMapper;
	private final ErrorMessageHandler errorMessageHandler;
	@Qualifier(value="messageSource")
	private final ResourceBundleMessageSource messageSource;
	
	@GetMapping
    public ResponseEntity<List<LecturerDto>> findAll() {		
		
        return ResponseEntity.ok(lecturerMapper.toLecturerDtos(lecturerService.findAll()));
    }
	
	@PostMapping
    public ResponseEntity<?> create(@Valid@RequestBody LecturerDto lecturerDto,
    		BindingResult bindingResult) {
		
		if (bindingResult.hasErrors()) {
			return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(errorMessageHandler.handle(bindingResult));
		}
		
        Lecturer lecturer = lecturerService.save(lecturerMapper.toLecturer(lecturerDto));
        
        return ResponseEntity.status(HttpStatus.CREATED).body(lecturerMapper.toLecturerDto(lecturer));
    }
	
	@GetMapping("/{id}")
    public ResponseEntity<?> findById(@PathVariable Long id) {

        Lecturer lecturer = lecturerService.findById(id);
        
        if (lecturer == null) {
        	return ResponseEntity.status(HttpStatus.NOT_FOUND).body(messageSource.getMessage(NOT_FOUND, null, Locale.getDefault()));
        }
               
        return ResponseEntity.ok(lecturerMapper.toLecturerDto(lecturer));
    }
	
	@CrossOrigin(methods = RequestMethod.PUT)
	@PutMapping("/{id}")
    public ResponseEntity<?> update(@PathVariable Long id, @Valid@RequestBody LecturerDto lecturerDto, 
    		BindingResult bindingResult) {

		if (bindingResult.hasErrors()) {
			return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(errorMessageHandler.handle(bindingResult));
		}
		
        Lecturer lecturer = lecturerMapper.toLecturer(lecturerDto);
        lecturer.setPersonId(id);

        return ResponseEntity.status(HttpStatus.ACCEPTED).body(lecturerMapper.toLecturerDto(lecturerService.save(lecturer)));
    }

	@CrossOrigin(methods = RequestMethod.DELETE)
	@DeleteMapping("/{id}")
    public ResponseEntity<String> delete(@PathVariable Long id) {
        lecturerService.delete(id);
        
        return ResponseEntity.status(HttpStatus.ACCEPTED).build();
    }	
}