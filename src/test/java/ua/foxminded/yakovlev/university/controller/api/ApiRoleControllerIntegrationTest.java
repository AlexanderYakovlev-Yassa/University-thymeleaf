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

import ua.foxminded.yakovlev.university.entity.Role;
import ua.foxminded.yakovlev.university.exception.NotFoundException;
import ua.foxminded.yakovlev.university.mapper.RoleMapper;
import ua.foxminded.yakovlev.university.service.AuthorityService;
import ua.foxminded.yakovlev.university.service.CourseService;
import ua.foxminded.yakovlev.university.service.RoleService;
import ua.foxminded.yakovlev.university.service.GroupService;
import ua.foxminded.yakovlev.university.service.LecturerService;
import ua.foxminded.yakovlev.university.service.PositionService;
import ua.foxminded.yakovlev.university.service.StudentService;
import ua.foxminded.yakovlev.university.service.TimetableRecordService;
import ua.foxminded.yakovlev.university.service.impl.UserService;
import ua.foxminded.yakovlev.university.util.RoleGenerator;

@WebMvcTest(ApiRoleController.class)
class ApiRoleControllerIntegrationTest {

	@MockBean
	private RoleService roleService;
	
	@MockBean
	private GroupService gs;
	@MockBean
	private LecturerService ls;
	@MockBean
	private PositionService ps;
	@MockBean
	private StudentService ss;
	@MockBean
	private TimetableRecordService ts;
	@MockBean
	private UserService us;
	@MockBean
	private CourseService cs;
	@MockBean
	private AuthorityService as;
	
	@Autowired
	private MockMvc mockMvc;
	private ObjectMapper objectMapper;
	
	@Autowired
	private RoleMapper roleMapper;
	
	private RoleGenerator roleGenerator;
	
	@BeforeEach
	private void init() {
		objectMapper = new ObjectMapper();
		roleGenerator = new RoleGenerator();
	}
	
	@Test
	@WithMockUser(username = "user", authorities={"READ_ROLE"})
	void findAllShouldReturnAllRoles() throws Exception {
		
		List<Role> roleList = roleGenerator.getRoleList();
		
		when(roleService.findAll()).thenReturn(roleList);
		
		this.mockMvc.perform(get("/api/roles"))
		   .andExpect(status().isOk())
		   .andExpect(content().contentType(MediaType.APPLICATION_JSON))
		   .andExpect(content().json(objectMapper.writeValueAsString(roleMapper.toRoleDtos(roleList))));
	}
	
	@Test
	@WithMockUser(username = "user", authorities={"MANAGE_ROLE"})
	void createShouldSaveRoleIfitValidAndReturnSavedRole() throws Exception {
		
		Role role = roleGenerator.getRoleList().get(0);
		String jsonRoleDto = objectMapper.writeValueAsString(roleMapper.toRoleDto(role));
		
		when(roleService.save(role)).thenReturn(role);
				
		this.mockMvc.perform(post("/api/roles").contentType(MediaType.APPLICATION_JSON)
	            .content(jsonRoleDto))
		   .andExpect(status().isCreated())
		   .andExpect(content().contentType(MediaType.APPLICATION_JSON))
		   .andExpect(content().json(jsonRoleDto));
	}
	
	@Test
	@WithMockUser(username = "user", authorities={"MANAGE_ROLE"})
	void createShouldReturnStatusIfRoleIsInvalid() throws Exception {
		
		Role role = roleGenerator.getRoleList().get(0);
		role.setName("");
		String jsonRoleDto = objectMapper.writeValueAsString(roleMapper.toRoleDto(role));
		
		when(roleService.save(role)).thenReturn(role);
				
		this.mockMvc.perform(post("/api/roles").contentType(MediaType.APPLICATION_JSON)
	            .content(jsonRoleDto))
		   .andExpect(status().isBadRequest())
		   .andExpect(content().contentType(MediaType.APPLICATION_JSON));
		
		verify(roleService, never()).save(role);
	}
	
	@Test
	@WithMockUser(username = "user", authorities={"READ_ROLE"})
	void findByIdShouldReturnRoleIfSuchRoleExists() throws Exception {

		Role role = roleGenerator.getRoleList().get(0);
		Long id = 1L;
		String jsonRoleDto = objectMapper.writeValueAsString(roleMapper.toRoleDto(role));
		
		when(roleService.findById(id)).thenReturn(role);
				
		this.mockMvc.perform(get("/api/roles/{id}", id))
		   .andExpect(status().isOk())
		   .andExpect(content().contentType(MediaType.APPLICATION_JSON))
		   .andExpect(content().json(jsonRoleDto));
	}
	
	@Test
	@WithMockUser(username = "user", authorities={"READ_ROLE"})
	void findByIdShouldReturnStatusNotFoundIfSuchRoleDoesNotExist() throws Exception {

		Long id = 1L;
		
		when(roleService.findById(id)).thenThrow(NotFoundException.class);
				
		this.mockMvc.perform(get("/api/roles/{id}", id))
		   .andExpect(status().isNotFound())
		   .andExpect(content().contentType(MediaType.APPLICATION_JSON));
	}
	
	@Test
	@WithMockUser(username = "user", authorities={"MODIFY_ROLE"})
	void updateShouldReturnUpdatedRoleDtoIfSuchRoleExistsAndValid() throws Exception {

		Role role = roleGenerator.getRoleList().get(0);
		Long id = role.getId();
		String jsonRoleDto = objectMapper.writeValueAsString(roleMapper.toRoleDto(role));
		
		when(roleService.update(role)).thenReturn(role);
				
		this.mockMvc.perform(put("/api/roles/{id}", id)
				.contentType(MediaType.APPLICATION_JSON)
				.content(jsonRoleDto))
		   .andExpect(status().isAccepted())
		   .andExpect(content().contentType(MediaType.APPLICATION_JSON))
		   .andExpect(content().json(jsonRoleDto));
	}
	
	@Test
	@WithMockUser(username = "user", authorities={"MODIFY_ROLE"})
	void updateShouldReturnStatusBadRequestIfSuchRoleExistsButInvalid() throws Exception {

		Role role = roleGenerator.getRoleList().get(0);
		Long id = role.getId();
		role.setName("");
		String jsonRoleDto = objectMapper.writeValueAsString(roleMapper.toRoleDto(role));
		
		when(roleService.update(role)).thenReturn(role);
				
		this.mockMvc.perform(put("/api/roles/{id}", id)
				.contentType(MediaType.APPLICATION_JSON)
				.content(jsonRoleDto))
		   .andExpect(status().isBadRequest())
		   .andExpect(content().contentType(MediaType.APPLICATION_JSON));
		
		verify(roleService, never()).update(role);
	}
	
	@Test
	@WithMockUser(username = "user", authorities={"MODIFY_ROLE"})
	void updateShouldReturnStatusNotFoundIfSuchRoleDoesNotExist() throws Exception {

		Role role = roleGenerator.getRoleList().get(0);
		Long id = role.getId();
		String jsonRoleDto = objectMapper.writeValueAsString(roleMapper.toRoleDto(role));
		
		when(roleService.update(role)).thenThrow(NotFoundException.class);
				
		this.mockMvc.perform(put("/api/roles/{id}", id)
				.contentType(MediaType.APPLICATION_JSON)
				.content(jsonRoleDto))
		   .andExpect(status().isNotFound())
		   .andExpect(content().contentType(MediaType.APPLICATION_JSON));
	}
	
	@Test
	@WithMockUser(username = "user", authorities={"MANAGE_ROLE"})
	void deleteShouldReturnStatusAcceptedIfSuchRoleExists() throws Exception {
		
		Long id = 1L;
		
		this.mockMvc.perform(delete("/api/roles/{id}", id))
		   .andExpect(status().isAccepted());
		
		verify(roleService).delete(id);
	}
	
	@Test
	@WithMockUser(username = "user", authorities={"MANAGE_ROLE"})
	void deleteShouldReturnStatusNotFoundIfSuchRoleDoesNotExist() throws Exception {
		
		Long id = 1L;
		
		doThrow(NotFoundException.class).when(roleService).delete(id);
		
		this.mockMvc.perform(delete("/api/roles/{id}", id))
		   .andExpect(status().isNotFound());
	}
}
