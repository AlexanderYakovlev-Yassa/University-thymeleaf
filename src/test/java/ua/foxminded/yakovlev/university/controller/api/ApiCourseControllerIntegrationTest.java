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

import ua.foxminded.yakovlev.university.entity.Course;
import ua.foxminded.yakovlev.university.exception.NotFoundException;
import ua.foxminded.yakovlev.university.mapper.CourseMapper;
import ua.foxminded.yakovlev.university.service.AuthorityService;
import ua.foxminded.yakovlev.university.service.CourseService;
import ua.foxminded.yakovlev.university.service.GroupService;
import ua.foxminded.yakovlev.university.service.LecturerService;
import ua.foxminded.yakovlev.university.service.PositionService;
import ua.foxminded.yakovlev.university.service.RoleService;
import ua.foxminded.yakovlev.university.service.StudentService;
import ua.foxminded.yakovlev.university.service.TimetableRecordService;
import ua.foxminded.yakovlev.university.service.impl.UserService;
import ua.foxminded.yakovlev.university.util.CourseGenerator;

@WebMvcTest(ApiCourseController.class)
class ApiCourseControllerIntegrationTest {

	@MockBean
	private CourseService courseService;
	
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
	private RoleService rs;
	@MockBean
	private AuthorityService as;
	
	@Autowired
	private MockMvc mockMvc;
	private ObjectMapper objectMapper;
	
	@Autowired
	private CourseMapper courseMapper;
	
	private CourseGenerator courseGenerator;
	
	@BeforeEach
	private void init() {
		objectMapper = new ObjectMapper();
		courseGenerator = new CourseGenerator();
	}
	
	@Test
	@WithMockUser(username = "user", authorities={"READ_COURSE"})
	void findAllShouldReturnAllCourses() throws Exception {
		
		List<Course> courseList = courseGenerator.getCourseList();
		
		when(courseService.findAll()).thenReturn(courseList);
		
		this.mockMvc.perform(get("/api/courses"))
		   .andExpect(status().isOk())
		   .andExpect(content().contentType(MediaType.APPLICATION_JSON))
		   .andExpect(content().json(objectMapper.writeValueAsString(courseMapper.toCourseDtos(courseList))));
	}
	
	@Test
	@WithMockUser(username = "user", authorities={"MANAGE_COURSE"})
	void createShouldSaveCourseIfitValidAndReturnSavedCourse() throws Exception {
		
		Course course = courseGenerator.getCourseList().get(0);
		String jsonCourseDto = objectMapper.writeValueAsString(courseMapper.toCourseDto(course));
		
		when(courseService.save(course)).thenReturn(course);
				
		this.mockMvc.perform(post("/api/courses").contentType(MediaType.APPLICATION_JSON)
	            .content(jsonCourseDto))
		   .andExpect(status().isCreated())
		   .andExpect(content().contentType(MediaType.APPLICATION_JSON))
		   .andExpect(content().json(jsonCourseDto));
	}
	
	@Test
	@WithMockUser(username = "user", authorities={"MANAGE_COURSE"})
	void createShouldReturnStatusIfCourseIsInvalid() throws Exception {
		
		Course course = courseGenerator.getCourseList().get(0);
		course.setName("");
		String jsonCourseDto = objectMapper.writeValueAsString(courseMapper.toCourseDto(course));
		
		when(courseService.save(course)).thenReturn(course);
				
		this.mockMvc.perform(post("/api/courses").contentType(MediaType.APPLICATION_JSON)
	            .content(jsonCourseDto))
		   .andExpect(status().isBadRequest())
		   .andExpect(content().contentType(MediaType.APPLICATION_JSON));
		
		verify(courseService, never()).save(course);
	}
	
	@Test
	@WithMockUser(username = "user", authorities={"READ_COURSE"})
	void findByIdShouldReturnCourseIfSuchCourseExists() throws Exception {

		Course course = courseGenerator.getCourseList().get(0);
		Long id = 1L;
		String jsonCourseDto = objectMapper.writeValueAsString(courseMapper.toCourseDto(course));
		
		when(courseService.findById(id)).thenReturn(course);
				
		this.mockMvc.perform(get("/api/courses/{id}", id))
		   .andExpect(status().isOk())
		   .andExpect(content().contentType(MediaType.APPLICATION_JSON))
		   .andExpect(content().json(jsonCourseDto));
	}
	
	@Test
	@WithMockUser(username = "user", authorities={"READ_COURSE"})
	void findByIdShouldReturnStatusNotFoundIfSuchCourseDoesNotExist() throws Exception {

		Long id = 1L;
		
		when(courseService.findById(id)).thenThrow(NotFoundException.class);
				
		this.mockMvc.perform(get("/api/courses/{id}", id))
		   .andExpect(status().isNotFound())
		   .andExpect(content().contentType(MediaType.APPLICATION_JSON));
	}
	
	@Test
	@WithMockUser(username = "user", authorities={"MODIFY_COURSE"})
	void updateShouldReturnUpdatedCourseDtoIfSuchCourseExistsAndValid() throws Exception {

		Course course = courseGenerator.getCourseList().get(0);
		Long id = course.getId();
		String jsonCourseDto = objectMapper.writeValueAsString(courseMapper.toCourseDto(course));
		
		when(courseService.update(course)).thenReturn(course);
				
		this.mockMvc.perform(put("/api/courses/{id}", id)
				.contentType(MediaType.APPLICATION_JSON)
				.content(jsonCourseDto))
		   .andExpect(status().isAccepted())
		   .andExpect(content().contentType(MediaType.APPLICATION_JSON))
		   .andExpect(content().json(jsonCourseDto));
	}
	
	@Test
	@WithMockUser(username = "user", authorities={"MODIFY_COURSE"})
	void updateShouldReturnStatusBadRequestIfSuchCourseExistsButInvalid() throws Exception {

		Course course = courseGenerator.getCourseList().get(0);
		Long id = course.getId();
		course.setName("");
		String jsonCourseDto = objectMapper.writeValueAsString(courseMapper.toCourseDto(course));
		
		when(courseService.update(course)).thenReturn(course);
				
		this.mockMvc.perform(put("/api/courses/{id}", id)
				.contentType(MediaType.APPLICATION_JSON)
				.content(jsonCourseDto))
		   .andExpect(status().isBadRequest())
		   .andExpect(content().contentType(MediaType.APPLICATION_JSON));
		
		verify(courseService, never()).update(course);
	}
	
	@Test
	@WithMockUser(username = "user", authorities={"MODIFY_COURSE"})
	void updateShouldReturnStatusNotFoundIfSuchCourseDoesNotExist() throws Exception {

		Course course = courseGenerator.getCourseList().get(0);
		Long id = course.getId();
		String jsonCourseDto = objectMapper.writeValueAsString(courseMapper.toCourseDto(course));
		
		when(courseService.update(course)).thenThrow(NotFoundException.class);
				
		this.mockMvc.perform(put("/api/courses/{id}", id)
				.contentType(MediaType.APPLICATION_JSON)
				.content(jsonCourseDto))
		   .andExpect(status().isNotFound())
		   .andExpect(content().contentType(MediaType.APPLICATION_JSON));
	}
	
	@Test
	@WithMockUser(username = "user", authorities={"MANAGE_COURSE"})
	void deleteShouldReturnStatusAcceptedIfSuchCourseExists() throws Exception {
		
		Long id = 1L;
		
		this.mockMvc.perform(delete("/api/courses/{id}", id))
		   .andExpect(status().isAccepted());
		
		verify(courseService).delete(id);
	}
	
	@Test
	@WithMockUser(username = "user", authorities={"MANAGE_COURSE"})
	void deleteShouldReturnStatusNotFoundIfSuchCourseDoesNotExist() throws Exception {
		
		Long id = 1L;
		
		doThrow(NotFoundException.class).when(courseService).delete(id);
		
		this.mockMvc.perform(delete("/api/courses/{id}", id))
		   .andExpect(status().isNotFound());
	}
}
