package ua.foxminded.yakovlev.university.controller;

import static org.mockito.Mockito.*;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;

import java.util.List;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.test.web.servlet.MockMvc;

import ua.foxminded.yakovlev.university.entity.Course;
import ua.foxminded.yakovlev.university.service.CourseService;
import ua.foxminded.yakovlev.university.service.GroupService;
import ua.foxminded.yakovlev.university.service.LecturerService;
import ua.foxminded.yakovlev.university.service.PositionService;
import ua.foxminded.yakovlev.university.service.StudentService;
import ua.foxminded.yakovlev.university.service.TimetableRecordService;
import ua.foxminded.yakovlev.university.util.CourseGenerator;

@WebMvcTest(CourseController.class)
class CourseControllerIntegrationTest {

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
	
	@Autowired
	private MockMvc mockMvc;
	
	private CourseGenerator courseGenerator;
	
	@BeforeEach
	private void init() {
		courseGenerator = new CourseGenerator();
	}
	
	@Test
	void showMustReturnPageWithAllCourses() throws Exception {
		
		List<Course> courseList = courseGenerator.getCourseList();
		
		when(courseService.findAll()).thenReturn(courseList);
		
		this. mockMvc.perform(get("/courses"))
		   .andExpect(status().isOk())
		   .andExpect(content().contentType("text/html;charset=UTF-8"))
		   .andExpect(model().attribute("courses", courseList));
	}
	
	@Test
	void createMustReturnPageForCompletingNewCourse() throws Exception {
		
		Course course = new Course();
		
		this. mockMvc.perform(get("/courses/new"))		
		   .andExpect(status().isOk())
		   .andExpect(content().contentType("text/html;charset=UTF-8"))
		   .andExpect(model().attribute("course", course));
	}
	
	@Test
	void saveMustSaveCourseWhenCourseIsValid() throws Exception {
		
		Course course = courseGenerator.getCourse(null, "Name", "Description");
		
		when(courseService.save(course)).thenReturn(course);
		
		this. mockMvc.perform(post("/courses/new").flashAttr("course", course))
		   .andExpect(status().is3xxRedirection())
		   .andExpect(redirectedUrl("/courses"));
		
		verify(courseService).save(course);
	}

	@Test
	void saveMustRedirectToCreateWhenCourseIsInvalid() throws Exception {
		
		Course course = courseGenerator.getCourse(null, " ", "Description");		
		
		this. mockMvc.perform(post("/courses/new").flashAttr("course", course))
		   .andExpect(status().is3xxRedirection())
		   .andExpect(redirectedUrlPattern("/courses/new*"));
		
		verify(courseService, never()).save(course);		
	}
	
	@Test
	void editMustReturnPageForEditingCourse() throws Exception {
		
		Course course = courseGenerator.getCourse(5L, "Name", "Description");
		
		when(courseService.findById(anyLong())).thenReturn(course);
		
		this. mockMvc.perform(get("/courses/edit").param("id", "5"))		
		   .andExpect(status().isOk())
		   .andExpect(content().contentType("text/html;charset=UTF-8"))
		   .andExpect(model().attribute("course", course));
	}
	
	@Test
	void updateMustRedirectToEditWhenCourseIsInvalid() throws Exception {
		
		Course course = courseGenerator.getCourse(5L, " ", "Description");
		
		this. mockMvc.perform(post("/courses/edit").flashAttr("course", course))
		   .andExpect(status().is3xxRedirection())
		   .andExpect(redirectedUrlPattern("/courses/edit*"));
		
		verify(courseService, never()).update(course);
	}
	
	@Test
	void updateMustUpdateCourseWhenCourseIsValid() throws Exception {
		
		Course course = courseGenerator.getCourse(5L, "Name", "Description");
		
		this. mockMvc.perform(post("/courses/edit").flashAttr("course", course))
		   .andExpect(status().is3xxRedirection())
		   .andExpect(redirectedUrl("/courses"));
		
		verify(courseService).update(course);
	}
	
	@Test
	void deleteMustRetrieveDeleteMethodAndRedirectToCourses() throws Exception {
		
		Long id = 5L;
				
		this. mockMvc.perform(post("/courses/delete").param("id", id.toString()))
		   .andExpect(status().is3xxRedirection())
		   .andExpect(redirectedUrl("/courses"));
		
		verify(courseService).delete(id);
	}
}
