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
import ua.foxminded.yakovlev.university.dto.LecturerDto;
import ua.foxminded.yakovlev.university.entity.Lecturer;
import ua.foxminded.yakovlev.university.mapper.LecturerMapper;
import ua.foxminded.yakovlev.university.service.LecturerService;

@RestController
@RequiredArgsConstructor
@RequestMapping("/api/lecturers")
public class ApiLecturerController {
	
	private final LecturerService lecturerService;
	private final LecturerMapper lecturerMapper;
	@Qualifier(value="messageSource")
	private final ResourceBundleMessageSource messageSource;
	
	@GetMapping
	@PreAuthorize("hasAuthority('READ_LECTURER')")
    public ResponseEntity<List<LecturerDto>> findAll() {		
		
        return ResponseEntity.ok(lecturerMapper.toLecturerDtos(lecturerService.findAll()));
    }
	
	@PostMapping
	@PreAuthorize("hasAuthority('MANAGE_LECTURER')")
    public ResponseEntity<LecturerDto> create(@Valid@RequestBody LecturerDto lecturerDto) {        
        return ResponseEntity.status(HttpStatus.CREATED)
        		.body(lecturerMapper.toLecturerDto(lecturerService.save(lecturerMapper.toLecturer(lecturerDto))));
    }
	
	@GetMapping("/{id}")
	@PreAuthorize("hasAuthority('READ_LECTURER')")
    public ResponseEntity<LecturerDto> findById(@PathVariable Long id) {               
        return ResponseEntity.ok(lecturerMapper.toLecturerDto(lecturerService.findById(id)));
    }
	
	@CrossOrigin(methods = RequestMethod.PUT)
	@PutMapping("/{id}")
	@PreAuthorize("hasAuthority('MODIFY_LECTURER')")
    public ResponseEntity<LecturerDto> update(@PathVariable Long id, @Valid@RequestBody LecturerDto lecturerDto) {
		
        Lecturer lecturer = lecturerMapper.toLecturer(lecturerDto);
        lecturer.setPersonId(id);

        return ResponseEntity.status(HttpStatus.ACCEPTED).body(lecturerMapper.toLecturerDto(lecturerService.update(lecturer)));
    }

	@CrossOrigin(methods = RequestMethod.DELETE)
	@DeleteMapping("/{id}")
	@PreAuthorize("hasAuthority('MANAGE_LECTURER')")
    public ResponseEntity<String> delete(@PathVariable Long id) {
		
        lecturerService.delete(id);
        
        return ResponseEntity.status(HttpStatus.ACCEPTED).build();
    }
	
	@GetMapping("/position/{id}")
	@PreAuthorize("hasAuthority('READ_LECTURER')")
    public ResponseEntity<List<LecturerDto>> findByPosition(@PathVariable Long id) {		
        return ResponseEntity.ok(lecturerMapper.toLecturerDtos(lecturerService.findByPositionId(id)));
    }
}