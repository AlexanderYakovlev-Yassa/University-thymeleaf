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

import ua.foxminded.yakovlev.university.dto.StudentDto;
import ua.foxminded.yakovlev.university.entity.Group;
import ua.foxminded.yakovlev.university.entity.Student;
import ua.foxminded.yakovlev.university.exception.NotFoundException;
import ua.foxminded.yakovlev.university.mapper.GroupMapper;
import ua.foxminded.yakovlev.university.mapper.StudentMapper;
import ua.foxminded.yakovlev.university.service.AuthorityService;
import ua.foxminded.yakovlev.university.service.CourseService;
import ua.foxminded.yakovlev.university.service.GroupService;
import ua.foxminded.yakovlev.university.service.LecturerService;
import ua.foxminded.yakovlev.university.service.PositionService;
import ua.foxminded.yakovlev.university.service.RoleService;
import ua.foxminded.yakovlev.university.service.StudentService;
import ua.foxminded.yakovlev.university.service.TimetableRecordService;
import ua.foxminded.yakovlev.university.service.impl.UserService;
import ua.foxminded.yakovlev.university.util.GroupGenerator;
import ua.foxminded.yakovlev.university.util.StudentGenerator;

@WebMvcTest(ApiGroupController.class)
class ApiGroupControllerIntegrationTest {

	@MockBean
	private GroupService groupService;
	
	@MockBean
	private CourseService cs;
	@MockBean
	private LecturerService ls;
	@MockBean
	private PositionService ps;
	@MockBean
	private StudentService studentService;
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
	private GroupMapper groupMapper;
	
	@Autowired
	private StudentMapper studentMapper;
	
	private GroupGenerator groupGenerator;
	private StudentGenerator studentGenerator;
	
	@BeforeEach
	private void init() {
		objectMapper = new ObjectMapper();
		groupGenerator = new GroupGenerator();
		studentGenerator = new StudentGenerator();
	}
	
	@Test
	@WithMockUser(username = "user", authorities={"READ_GROUP"})
	void findAllShouldReturnAllGroups() throws Exception {
		
		List<Group> groupList = groupGenerator.getGroupList();
		
		when(groupService.findAll()).thenReturn(groupList);
		
		this.mockMvc.perform(get("/api/groups"))
		   .andExpect(status().isOk())
		   .andExpect(content().contentType(MediaType.APPLICATION_JSON))
		   .andExpect(content().json(objectMapper.writeValueAsString(groupMapper.toGroupDtos(groupList))));
	}
	
	@Test
	@WithMockUser(username = "user", authorities={"MANAGE_GROUP"})
	void createShouldSaveGroupIfitValidAndReturnSavedGroup() throws Exception {
		
		Group group = groupGenerator.getGroupList().get(0);
		String jsonGroupDto = objectMapper.writeValueAsString(groupMapper.toGroupDto(group));
		
		when(groupService.save(group)).thenReturn(group);
				
		this.mockMvc.perform(post("/api/groups").contentType(MediaType.APPLICATION_JSON)
	            .content(jsonGroupDto))
		   .andExpect(status().isCreated())
		   .andExpect(content().contentType(MediaType.APPLICATION_JSON))
		   .andExpect(content().json(jsonGroupDto));
	}
	
	@Test
	@WithMockUser(username = "user", authorities={"MANAGE_GROUP"})
	void createShouldReturnStatusIfGroupIsInvalid() throws Exception {
		
		Group group = groupGenerator.getGroupList().get(0);
		group.setName("");
		String jsonGroupDto = objectMapper.writeValueAsString(groupMapper.toGroupDto(group));
		
		when(groupService.save(group)).thenReturn(group);
				
		this.mockMvc.perform(post("/api/groups").contentType(MediaType.APPLICATION_JSON)
	            .content(jsonGroupDto))
		   .andExpect(status().isBadRequest())
		   .andExpect(content().contentType(MediaType.APPLICATION_JSON));
		
		verify(groupService, never()).save(group);
	}
	
	@Test
	@WithMockUser(username = "user", authorities={"READ_GROUP"})
	void findByIdShouldReturnGroupIfSuchGroupExists() throws Exception {

		Group group = groupGenerator.getGroupList().get(0);
		Long id = 1L;
		String jsonGroupDto = objectMapper.writeValueAsString(groupMapper.toGroupDto(group));
		
		when(groupService.findById(id)).thenReturn(group);
				
		this.mockMvc.perform(get("/api/groups/{id}", id))
		   .andExpect(status().isOk())
		   .andExpect(content().contentType(MediaType.APPLICATION_JSON))
		   .andExpect(content().json(jsonGroupDto));
	}
	
	@Test
	@WithMockUser(username = "user", authorities={"READ_GROUP"})
	void findByIdShouldReturnStatusNotFoundIfSuchGroupDoesNotExist() throws Exception {

		Long id = 1L;
		
		when(groupService.findById(id)).thenThrow(NotFoundException.class);
				
		this.mockMvc.perform(get("/api/groups/{id}", id))
		   .andExpect(status().isNotFound())
		   .andExpect(content().contentType(MediaType.APPLICATION_JSON));
	}
	
	@Test
	@WithMockUser(username = "user", authorities={"MODIFY_GROUP"})
	void updateShouldReturnUpdatedGroupDtoIfSuchGroupExistsAndValid() throws Exception {

		Group group = groupGenerator.getGroupList().get(0);
		Long id = group.getId();
		String jsonGroupDto = objectMapper.writeValueAsString(groupMapper.toGroupDto(group));
		
		when(groupService.update(group)).thenReturn(group);
				
		this.mockMvc.perform(put("/api/groups/{id}", id)
				.contentType(MediaType.APPLICATION_JSON)
				.content(jsonGroupDto))
		   .andExpect(status().isAccepted())
		   .andExpect(content().contentType(MediaType.APPLICATION_JSON))
		   .andExpect(content().json(jsonGroupDto));
	}
	
	@Test
	@WithMockUser(username = "user", authorities={"MODIFY_GROUP"})
	void updateShouldReturnStatusBadRequestIfSuchGroupExistsButInvalid() throws Exception {

		Group group = groupGenerator.getGroupList().get(0);
		Long id = group.getId();
		group.setName("");
		String jsonGroupDto = objectMapper.writeValueAsString(groupMapper.toGroupDto(group));
		
		when(groupService.update(group)).thenReturn(group);
				
		this.mockMvc.perform(put("/api/groups/{id}", id)
				.contentType(MediaType.APPLICATION_JSON)
				.content(jsonGroupDto))
		   .andExpect(status().isBadRequest())
		   .andExpect(content().contentType(MediaType.APPLICATION_JSON));
		
		verify(groupService, never()).update(group);
	}
	
	@Test
	@WithMockUser(username = "user", authorities={"MODIFY_GROUP"})
	void updateShouldReturnStatusNotFoundIfSuchGroupDoesNotExist() throws Exception {

		Group group = groupGenerator.getGroupList().get(0);
		Long id = group.getId();
		String jsonGroupDto = objectMapper.writeValueAsString(groupMapper.toGroupDto(group));
		
		when(groupService.update(group)).thenThrow(NotFoundException.class);
				
		this.mockMvc.perform(put("/api/groups/{id}", id)
				.contentType(MediaType.APPLICATION_JSON)
				.content(jsonGroupDto))
		   .andExpect(status().isNotFound())
		   .andExpect(content().contentType(MediaType.APPLICATION_JSON));
	}
	
	@Test
	@WithMockUser(username = "user", authorities={"MANAGE_GROUP"})
	void deleteShouldReturnStatusAcceptedIfSuchGroupExists() throws Exception {
		
		Long id = 1L;
		
		this.mockMvc.perform(delete("/api/groups/{id}", id))
		   .andExpect(status().isAccepted());
		
		verify(groupService).delete(id);
	}
	
	@Test
	@WithMockUser(username = "user", authorities={"MANAGE_GROUP"})
	void deleteShouldReturnStatusNotFoundIfSuchGroupDoesNotExist() throws Exception {
		
		Long id = 1L;
		
		doThrow(NotFoundException.class).when(groupService).delete(id);
		
		this.mockMvc.perform(delete("/api/groups/{id}", id))
		   .andExpect(status().isNotFound());
	}
	
	@Test
	@WithMockUser(username = "user", authorities={"MODIFY_GROUP"})
	void addStudentShouldReturnStatusOkAndListOfStudents() throws Exception {
		
		List<Student> studentList = studentGenerator.getStudentList();
		StudentDto studentDto = studentMapper.toStudentDto(studentList.get(0));
		String jsonStudentDto = objectMapper.writeValueAsString(studentDto);
		Long id = 1L;
		
		when(studentService.findByGroupId(id)).thenReturn(studentList);
		
		this.mockMvc.perform(put("/api/groups/{id}/add-student", id)
				.contentType(MediaType.APPLICATION_JSON)
				.content(jsonStudentDto))
		   .andExpect(status().isAccepted())
		   .andExpect(content().contentType(MediaType.APPLICATION_JSON))
		   .andExpect(content().json(objectMapper.writeValueAsString(studentMapper.toStudentDtos(studentList))));
	}
	
	@Test
	@WithMockUser(username = "user", authorities={"MODIFY_GROUP"})
	void addStudentShouldReturnStatusNotFoundIfSuchStudentDoesNotExist() throws Exception {
		
		List<Student> studentList = studentGenerator.getStudentList();
		StudentDto studentDto = studentMapper.toStudentDto(studentList.get(0));
		String jsonStudentDto = objectMapper.writeValueAsString(studentDto);
		Long id = 1L;
		
		when(studentService.findByGroupId(id)).thenThrow(NotFoundException.class);
		
		this.mockMvc.perform(put("/api/groups/{id}/add-student", id)
				.contentType(MediaType.APPLICATION_JSON)
				.content(jsonStudentDto))
		   .andExpect(status().isNotFound())
		   .andExpect(content().contentType(MediaType.APPLICATION_JSON));
	}
	
	@Test
	@WithMockUser(username = "user", authorities={"MODIFY_GROUP"})
	void addStudentShouldReturnStatusNotFoundIfRequestContentIsNotValid() throws Exception {
		
		String jsonStudentDto = "invalid content";
		Long id = 1L;
		
		this.mockMvc.perform(put("/api/groups/{id}/add-student", id)
				.contentType(MediaType.APPLICATION_JSON)
				.content(jsonStudentDto))
		   .andExpect(status().isBadRequest());
	}
}
