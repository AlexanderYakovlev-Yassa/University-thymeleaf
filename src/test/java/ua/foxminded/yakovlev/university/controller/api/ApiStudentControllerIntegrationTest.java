package ua.foxminded.yakovlev.university.controller.api;

import static org.mockito.Mockito.*;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;

import java.util.List;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.http.MediaType;
import org.springframework.security.test.context.support.WithMockUser;
import org.springframework.test.web.servlet.MockMvc;

import com.fasterxml.jackson.databind.ObjectMapper;

import ua.foxminded.yakovlev.university.entity.Student;
import ua.foxminded.yakovlev.university.exception.NotFoundException;
import ua.foxminded.yakovlev.university.mapper.StudentMapper;
import ua.foxminded.yakovlev.university.service.StudentService;
import ua.foxminded.yakovlev.university.service.GroupService;
import ua.foxminded.yakovlev.university.service.LecturerService;
import ua.foxminded.yakovlev.university.service.PositionService;
import ua.foxminded.yakovlev.university.service.RoleService;
import ua.foxminded.yakovlev.university.service.AuthorityService;
import ua.foxminded.yakovlev.university.service.CourseService;
import ua.foxminded.yakovlev.university.service.TimetableRecordService;
import ua.foxminded.yakovlev.university.service.impl.UserService;
import ua.foxminded.yakovlev.university.util.StudentGenerator;

@WebMvcTest(ApiStudentController.class)
class ApiStudentControllerIntegrationTest {

	@MockBean
	private StudentService studentService;
	
	@MockBean
	private GroupService gs;
	@MockBean
	private LecturerService ls;
	@MockBean
	private PositionService ps;
	@MockBean
	private CourseService cs;
	@MockBean
	private TimetableRecordService ts;
	@MockBean
	private UserService us;
	@MockBean
	private RoleService rs;
	@MockBean
	private AuthorityService as;
	
	@Autowired
	private MockMvc mockMvc;
	private ObjectMapper objectMapper;
	
	@Autowired
	private StudentMapper studentMapper;
	
	private StudentGenerator studentGenerator;
	
	@BeforeEach
	private void init() {
		objectMapper = new ObjectMapper();
		studentGenerator = new StudentGenerator();
	}
	
	@Test
	@WithMockUser(username = "user", authorities={"READ_STUDENT"})
	void findAllShouldReturnAllStudents() throws Exception {
		
		List<Student> studentList = studentGenerator.getStudentList();
		
		when(studentService.findAll()).thenReturn(studentList);
		
		this.mockMvc.perform(get("/api/students"))
		   .andExpect(status().isOk())
		   .andExpect(content().contentType(MediaType.APPLICATION_JSON))
		   .andExpect(content().json(objectMapper.writeValueAsString(studentMapper.toStudentDtos(studentList))));
	}
	
	@Test
	@WithMockUser(username = "user", authorities={"MANAGE_STUDENT"})
	void createShouldSaveStudentIfitValidAndReturnSavedStudent() throws Exception {
		
		Student student = studentGenerator.getStudentList().get(0);
		String jsonStudentDto = objectMapper.writeValueAsString(studentMapper.toStudentDto(student));
		
		when(studentService.save(student)).thenReturn(student);
				
		this.mockMvc.perform(post("/api/students").contentType(MediaType.APPLICATION_JSON)
	            .content(jsonStudentDto))
		   .andExpect(status().isCreated())
		   .andExpect(content().contentType(MediaType.APPLICATION_JSON))
		   .andExpect(content().json(jsonStudentDto));
	}
	
	@Test
	@WithMockUser(username = "user", authorities={"MANAGE_STUDENT"})
	void createShouldReturnStatusIfStudentIsInvalid() throws Exception {
		
		Student student = studentGenerator.getStudentList().get(0);
		student.setFirstName("");
		String jsonStudentDto = objectMapper.writeValueAsString(studentMapper.toStudentDto(student));
		
		when(studentService.save(student)).thenReturn(student);
				
		this.mockMvc.perform(post("/api/students").contentType(MediaType.APPLICATION_JSON)
	            .content(jsonStudentDto))
		   .andExpect(status().isBadRequest())
		   .andExpect(content().contentType(MediaType.APPLICATION_JSON));
		
		verify(studentService, never()).save(student);
	}
	
	@Test
	@WithMockUser(username = "user", authorities={"READ_STUDENT"})
	void findByIdShouldReturnStudentIfSuchStudentExists() throws Exception {

		Student student = studentGenerator.getStudentList().get(0);
		Long id = 1L;
		String jsonStudentDto = objectMapper.writeValueAsString(studentMapper.toStudentDto(student));
		
		when(studentService.findById(id)).thenReturn(student);
				
		this.mockMvc.perform(get("/api/students/{id}", id))
		   .andExpect(status().isOk())
		   .andExpect(content().contentType(MediaType.APPLICATION_JSON))
		   .andExpect(content().json(jsonStudentDto));
	}
	
	@Test
	@WithMockUser(username = "user", authorities={"READ_STUDENT"})
	void findByIdShouldReturnStatusNotFoundIfSuchStudentDoesNotExist() throws Exception {

		Long id = 1L;
		
		when(studentService.findById(id)).thenThrow(NotFoundException.class);
				
		this.mockMvc.perform(get("/api/students/{id}", id))
		   .andExpect(status().isNotFound())
		   .andExpect(content().contentType(MediaType.APPLICATION_JSON));
	}
	
	@Test
	@WithMockUser(username = "user", authorities={"MODIFY_STUDENT"})
	void updateShouldReturnUpdatedStudentDtoIfSuchStudentExistsAndValid() throws Exception {

		Student student = studentGenerator.getStudentList().get(0);
		Long id = student.getPersonId();
		String jsonStudentDto = objectMapper.writeValueAsString(studentMapper.toStudentDto(student));
		
		when(studentService.update(student)).thenReturn(student);
				
		this.mockMvc.perform(put("/api/students/{id}", id)
				.contentType(MediaType.APPLICATION_JSON)
				.content(jsonStudentDto))
		   .andExpect(status().isAccepted())
		   .andExpect(content().contentType(MediaType.APPLICATION_JSON))
		   .andExpect(content().json(jsonStudentDto));
	}
	
	@Test
	@WithMockUser(username = "user", authorities={"MODIFY_STUDENT"})
	void updateShouldReturnStatusBadRequestIfSuchStudentExistsButInvalid() throws Exception {

		Student student = studentGenerator.getStudentList().get(0);
		Long id = student.getPersonId();
		student.setFirstName("");
		String jsonStudentDto = objectMapper.writeValueAsString(studentMapper.toStudentDto(student));
		
		when(studentService.update(student)).thenReturn(student);
				
		this.mockMvc.perform(put("/api/students/{id}", id)
				.contentType(MediaType.APPLICATION_JSON)
				.content(jsonStudentDto))
		   .andExpect(status().isBadRequest())
		   .andExpect(content().contentType(MediaType.APPLICATION_JSON));
		
		verify(studentService, never()).update(student);
	}
	
	@Test
	@WithMockUser(username = "user", authorities={"MODIFY_STUDENT"})
	void updateShouldReturnStatusNotFoundIfSuchStudentDoesNotExist() throws Exception {

		Student student = studentGenerator.getStudentList().get(0);
		Long id = student.getPersonId();
		String jsonStudentDto = objectMapper.writeValueAsString(studentMapper.toStudentDto(student));
		
		when(studentService.update(student)).thenThrow(NotFoundException.class);
				
		this.mockMvc.perform(put("/api/students/{id}", id)
				.contentType(MediaType.APPLICATION_JSON)
				.content(jsonStudentDto))
		   .andExpect(status().isNotFound())
		   .andExpect(content().contentType(MediaType.APPLICATION_JSON));
	}
	
	@Test
	@WithMockUser(username = "user", authorities={"MANAGE_STUDENT"})
	void deleteShouldReturnStatusAcceptedIfSuchStudentExists() throws Exception {
		
		Long id = 1L;
		
		this.mockMvc.perform(delete("/api/students/{id}", id))
		   .andExpect(status().isAccepted());
		
		verify(studentService).delete(id);
	}
	
	@Test
	@WithMockUser(username = "user", authorities={"MANAGE_STUDENT"})
	void deleteShouldReturnStatusNotFoundIfSuchStudentDoesNotExist() throws Exception {
		
		Long id = 1L;
		
		doThrow(NotFoundException.class).when(studentService).delete(id);
		
		this.mockMvc.perform(delete("/api/students/{id}", id))
		   .andExpect(status().isNotFound());
	}
}
