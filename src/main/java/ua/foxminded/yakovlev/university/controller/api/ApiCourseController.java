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
import ua.foxminded.yakovlev.university.dto.CourseDto;
import ua.foxminded.yakovlev.university.entity.Course;
import ua.foxminded.yakovlev.university.mapper.CourseMapper;
import ua.foxminded.yakovlev.university.service.CourseService;
import ua.foxminded.yakovlev.university.util.ErrorMessageHandler;

@RestController
@RequiredArgsConstructor
@RequestMapping("/api/courses")
public class ApiCourseController {
	
	private static final String NOT_FOUND = "api.message.course_not_found";
	private final CourseService courseService;
	private final CourseMapper courseMapper;
	private final ErrorMessageHandler errorMessageHandler;
	@Qualifier(value="messageSource")
	private final ResourceBundleMessageSource messageSource;
	
	@GetMapping
    public ResponseEntity<List<CourseDto>> findAll() {		
		
        return ResponseEntity.ok(courseMapper.toCourseDtos(courseService.findAll()));
    }
	
	@PostMapping
    public ResponseEntity<?> create(@Valid@RequestBody CourseDto courseDto,
    		BindingResult bindingResult) {
		
		if (bindingResult.hasErrors()) {
			return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(errorMessageHandler.handle(bindingResult));
		}
		
        Course course = courseService.save(courseMapper.toCourse(courseDto));
        
        return ResponseEntity.status(HttpStatus.CREATED).body(courseMapper.toCourseDto(course));
    }
	
	@GetMapping("/{id}")
    public ResponseEntity<?> findById(@PathVariable Long id) {

        Course course = courseService.findById(id);
        
        if (course == null) {
        	return ResponseEntity.status(HttpStatus.NOT_FOUND).body(messageSource.getMessage(NOT_FOUND, null, Locale.getDefault()));
        }
               
        return ResponseEntity.ok(courseMapper.toCourseDto(course));
    }
	
	@CrossOrigin(methods = RequestMethod.PUT)
	@PutMapping("/{id}")
    public ResponseEntity<?> update(@PathVariable Long id, @Valid@RequestBody CourseDto courseDto, 
    		BindingResult bindingResult) {

		if (bindingResult.hasErrors()) {
			return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(errorMessageHandler.handle(bindingResult));
		}
		
		Course course = courseMapper.toCourse(courseDto);
		course.setId(id);
		
        return ResponseEntity.status(HttpStatus.ACCEPTED).body(courseMapper.toCourseDto(courseService.save(course)));
    }

	@CrossOrigin(methods = RequestMethod.DELETE)
	@DeleteMapping("/{id}")
    public ResponseEntity<String> delete(@PathVariable Long id) {
        courseService.delete(id);
        
        return ResponseEntity.status(HttpStatus.ACCEPTED).build();
    }
}