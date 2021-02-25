package ua.foxminded.yakovlev.university.controller.api;

import java.util.List;
import javax.validation.Valid;

import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.context.support.ResourceBundleMessageSource;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
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

@RestController
@RequiredArgsConstructor
@RequestMapping(value="/api/courses", produces = "application/json")
public class ApiCourseController {
	
	private final CourseService courseService;
	private final CourseMapper courseMapper;
	@Qualifier(value="messageSource")
	private final ResourceBundleMessageSource messageSource;
	
	@GetMapping
	@PreAuthorize("hasAuthority('READ_COURSE')")
    public ResponseEntity<List<CourseDto>> findAll() {		
		
        return ResponseEntity.ok(courseMapper.toCourseDtos(courseService.findAll()));
    }
	
	@PostMapping
	@PreAuthorize("hasAuthority('MANAGE_COURSE')")
    public ResponseEntity<CourseDto> create(@Valid@RequestBody CourseDto courseDto) {
		
        Course course = courseService.save(courseMapper.toCourse(courseDto));
        
        return ResponseEntity.status(HttpStatus.CREATED).body(courseMapper.toCourseDto(course));
    }
	
	@GetMapping("/{id}")
	@PreAuthorize("hasAuthority('READ_COURSE')")
    public ResponseEntity<CourseDto> findById(@PathVariable Long id) {

        Course course = courseService.findById(id);
               
        return ResponseEntity.ok(courseMapper.toCourseDto(course));
    }
	
	@CrossOrigin(methods = RequestMethod.PUT)
	@PutMapping("/{id}")
	@PreAuthorize("hasAuthority('MODIFY_COURSE')")
    public ResponseEntity<CourseDto> update(@PathVariable Long id, @Valid@RequestBody CourseDto courseDto) {
		
		Course course = courseMapper.toCourse(courseDto);
		course.setId(id);
		
        return ResponseEntity.status(HttpStatus.ACCEPTED).body(courseMapper.toCourseDto(courseService.update(course)));
    }

	@CrossOrigin(methods = RequestMethod.DELETE)
	@DeleteMapping("/{id}")
	@PreAuthorize("hasAuthority('MANAGE_COURSE')")
    public ResponseEntity<String> delete(@PathVariable Long id) {
        courseService.delete(id);
        
        return ResponseEntity.status(HttpStatus.ACCEPTED).build();
    }
}