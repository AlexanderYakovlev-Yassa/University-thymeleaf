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
import ua.foxminded.yakovlev.university.dto.GroupDto;
import ua.foxminded.yakovlev.university.dto.StudentDto;
import ua.foxminded.yakovlev.university.entity.Group;
import ua.foxminded.yakovlev.university.mapper.GroupMapper;
import ua.foxminded.yakovlev.university.mapper.StudentMapper;
import ua.foxminded.yakovlev.university.service.GroupService;
import ua.foxminded.yakovlev.university.service.StudentService;

@RestController
@RequiredArgsConstructor
@RequestMapping("/api/groups")
public class ApiGroupController {
	
	private final GroupService groupService;
	private final StudentService studentService;
	private final GroupMapper groupMapper;
	private final StudentMapper studentMapper;
	@Qualifier(value="messageSource")
	private final ResourceBundleMessageSource messageSource;
	
	@GetMapping
	@PreAuthorize("hasAuthority('READ_GROUP')")
    public ResponseEntity<List<GroupDto>> findAll() {		
		
        return ResponseEntity.ok(groupMapper.toGroupDtos(groupService.findAll()));
    }
	
	@PostMapping
	@PreAuthorize("hasAuthority('MANAGE_GROUP')")
    public ResponseEntity<GroupDto> create(@Valid@RequestBody GroupDto groupDto) {
		
        Group group = groupService.save(groupMapper.toGroup(groupDto));
        
        return ResponseEntity.status(HttpStatus.CREATED).body(groupMapper.toGroupDto(group));
    }
	
	@GetMapping("/{id}")
	@PreAuthorize("hasAuthority('READ_GROUP')")
    public ResponseEntity<GroupDto> findById(@PathVariable Long id) {

        Group group = groupService.findById(id);
               
        return ResponseEntity.ok(groupMapper.toGroupDto(group));
    }
	
	@CrossOrigin(methods = RequestMethod.PUT)
	@PutMapping("/{id}")
	@PreAuthorize("hasAuthority('MODIFY_GROUP')")
    public ResponseEntity<?> update(@PathVariable Long id, @Valid@RequestBody GroupDto groupDto) {
		
		Group group = groupMapper.toGroup(groupDto);
		group.setId(id);

        return ResponseEntity.status(HttpStatus.ACCEPTED).body(groupMapper.toGroupDto(groupService.update(group)));
    }

	@CrossOrigin(methods = RequestMethod.DELETE)
	@DeleteMapping("/{id}")
	@PreAuthorize("hasAuthority('MANAGE_GROUP')")
    public ResponseEntity<String> delete(@PathVariable Long id) {
		
        groupService.delete(id);
        
        return ResponseEntity.status(HttpStatus.ACCEPTED).build();
    }
	
	@CrossOrigin(methods = RequestMethod.PUT)
	@PutMapping("/{id}/add-student")
	@PreAuthorize("hasAuthority('MODIFY_GROUP')")
    public ResponseEntity<List<StudentDto>> addStudent(@PathVariable Long id, @RequestBody StudentDto studentDto) {
		
		Long studentId = studentDto.getPersonId();
		studentService.addGroup(studentId, id);

        return ResponseEntity.status(HttpStatus.ACCEPTED).body(studentMapper.toStudentDtos(studentService.findByGroupId(id)));
    }
	
	@CrossOrigin(methods = RequestMethod.PUT)
	@PutMapping("/{id}/remove-student")
	@PreAuthorize("hasAuthority('MANAGE_GROUP')")
    public ResponseEntity<List<StudentDto>> removeStudent(@PathVariable Long id, @RequestBody StudentDto studentDto) {
		
		Long studentId = studentDto.getPersonId();
		studentService.removeGroup(studentId, id);

        return ResponseEntity.status(HttpStatus.ACCEPTED).body(studentMapper.toStudentDtos(studentService.findByGroupId(id)));
    }
}