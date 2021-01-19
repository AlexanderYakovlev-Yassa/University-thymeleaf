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
import org.springframework.test.web.servlet.MockMvc;

import com.fasterxml.jackson.databind.ObjectMapper;

import ua.foxminded.yakovlev.university.entity.Lecturer;
import ua.foxminded.yakovlev.university.exception.NotFoundException;
import ua.foxminded.yakovlev.university.mapper.LecturerMapper;
import ua.foxminded.yakovlev.university.service.LecturerService;
import ua.foxminded.yakovlev.university.service.GroupService;
import ua.foxminded.yakovlev.university.service.StudentService;
import ua.foxminded.yakovlev.university.service.PositionService;
import ua.foxminded.yakovlev.university.service.CourseService;
import ua.foxminded.yakovlev.university.service.TimetableRecordService;
import ua.foxminded.yakovlev.university.util.LecturerGenerator;

@WebMvcTest(ApiLecturerController.class)
class ApiLecturerControllerIntegrationTest {

	@MockBean
	private LecturerService lecturerService;
	
	@MockBean
	private GroupService gs;
	@MockBean
	private StudentService ss;
	@MockBean
	private PositionService ps;
	@MockBean
	private CourseService cs;
	@MockBean
	private TimetableRecordService ts;
	
	@Autowired
	private MockMvc mockMvc;
	private ObjectMapper objectMapper;
	
	@Autowired
	private LecturerMapper lecturerMapper;
	
	private LecturerGenerator lecturerGenerator;
	
	@BeforeEach
	private void init() {
		objectMapper = new ObjectMapper();
		lecturerGenerator = new LecturerGenerator();
	}
	
	@Test
	void findAllShouldReturnAllLecturers() throws Exception {
		
		List<Lecturer> lecturerList = lecturerGenerator.getLecturerList();
		
		when(lecturerService.findAll()).thenReturn(lecturerList);
		
		this.mockMvc.perform(get("/api/lecturers"))
		   .andExpect(status().isOk())
		   .andExpect(content().contentType(MediaType.APPLICATION_JSON))
		   .andExpect(content().json(objectMapper.writeValueAsString(lecturerMapper.toLecturerDtos(lecturerList))));
	}
	
	@Test
	void createShouldSaveLecturerIfitValidAndReturnSavedLecturer() throws Exception {
		
		Lecturer lecturer = lecturerGenerator.getLecturerList().get(0);
		String jsonLecturerDto = objectMapper.writeValueAsString(lecturerMapper.toLecturerDto(lecturer));
		
		when(lecturerService.save(lecturer)).thenReturn(lecturer);
				
		this.mockMvc.perform(post("/api/lecturers").contentType(MediaType.APPLICATION_JSON)
	            .content(jsonLecturerDto))
		   .andExpect(status().isCreated())
		   .andExpect(content().contentType(MediaType.APPLICATION_JSON))
		   .andExpect(content().json(jsonLecturerDto));
	}
	
	@Test
	void createShouldReturnStatusIfLecturerIsInvalid() throws Exception {
		
		Lecturer lecturer = lecturerGenerator.getLecturerList().get(0);
		lecturer.setFirstName("");
		String jsonLecturerDto = objectMapper.writeValueAsString(lecturerMapper.toLecturerDto(lecturer));
		
		when(lecturerService.save(lecturer)).thenReturn(lecturer);
				
		this.mockMvc.perform(post("/api/lecturers").contentType(MediaType.APPLICATION_JSON)
	            .content(jsonLecturerDto))
		   .andExpect(status().isBadRequest())
		   .andExpect(content().contentType(MediaType.APPLICATION_JSON));
		
		verify(lecturerService, never()).save(lecturer);
	}
	
	@Test
	void findByIdShouldReturnLecturerIfSuchLecturerExists() throws Exception {

		Lecturer lecturer = lecturerGenerator.getLecturerList().get(0);
		Long id = 1L;
		String jsonLecturerDto = objectMapper.writeValueAsString(lecturerMapper.toLecturerDto(lecturer));
		
		when(lecturerService.findById(id)).thenReturn(lecturer);
				
		this.mockMvc.perform(get("/api/lecturers/{id}", id))
		   .andExpect(status().isOk())
		   .andExpect(content().contentType(MediaType.APPLICATION_JSON))
		   .andExpect(content().json(jsonLecturerDto));
	}
	
	@Test
	void findByIdShouldReturnStatusNotFoundIfSuchLecturerDoesNotExist() throws Exception {

		Long id = 1L;
		
		when(lecturerService.findById(id)).thenThrow(NotFoundException.class);
				
		this.mockMvc.perform(get("/api/lecturers/{id}", id))
		   .andExpect(status().isNotFound())
		   .andExpect(content().contentType(MediaType.APPLICATION_JSON));
	}
	
	@Test
	void updateShouldReturnUpdatedLecturerDtoIfSuchLecturerExistsAndValid() throws Exception {

		Lecturer lecturer = lecturerGenerator.getLecturerList().get(0);
		Long id = lecturer.getPersonId();
		String jsonLecturerDto = objectMapper.writeValueAsString(lecturerMapper.toLecturerDto(lecturer));
		
		when(lecturerService.update(lecturer)).thenReturn(lecturer);
				
		this.mockMvc.perform(put("/api/lecturers/{id}", id)
				.contentType(MediaType.APPLICATION_JSON)
				.content(jsonLecturerDto))
		   .andExpect(status().isAccepted())
		   .andExpect(content().contentType(MediaType.APPLICATION_JSON))
		   .andExpect(content().json(jsonLecturerDto));
	}
	
	@Test
	void updateShouldReturnStatusBadRequestIfSuchLecturerExistsButInvalid() throws Exception {

		Lecturer lecturer = lecturerGenerator.getLecturerList().get(0);
		Long id = lecturer.getPersonId();
		lecturer.setFirstName("");
		String jsonLecturerDto = objectMapper.writeValueAsString(lecturerMapper.toLecturerDto(lecturer));
		
		when(lecturerService.update(lecturer)).thenReturn(lecturer);
				
		this.mockMvc.perform(put("/api/lecturers/{id}", id)
				.contentType(MediaType.APPLICATION_JSON)
				.content(jsonLecturerDto))
		   .andExpect(status().isBadRequest())
		   .andExpect(content().contentType(MediaType.APPLICATION_JSON));
		
		verify(lecturerService, never()).update(lecturer);
	}
	
	@Test
	void updateShouldReturnStatusNotFoundIfSuchLecturerDoesNotExist() throws Exception {

		Lecturer lecturer = lecturerGenerator.getLecturerList().get(0);
		Long id = lecturer.getPersonId();
		String jsonLecturerDto = objectMapper.writeValueAsString(lecturerMapper.toLecturerDto(lecturer));
		
		when(lecturerService.update(lecturer)).thenThrow(NotFoundException.class);
				
		this.mockMvc.perform(put("/api/lecturers/{id}", id)
				.contentType(MediaType.APPLICATION_JSON)
				.content(jsonLecturerDto))
		   .andExpect(status().isNotFound())
		   .andExpect(content().contentType(MediaType.APPLICATION_JSON));
	}
	
	@Test
	void deleteShouldReturnStatusAcceptedIfSuchLecturerExists() throws Exception {
		
		Long id = 1L;
		
		this.mockMvc.perform(delete("/api/lecturers/{id}", id))
		   .andExpect(status().isAccepted());
		
		verify(lecturerService).delete(id);
	}
	
	@Test
	void deleteShouldReturnStatusNotFoundIfSuchLecturerDoesNotExist() throws Exception {
		
		Long id = 1L;
		
		doThrow(NotFoundException.class).when(lecturerService).delete(id);
		
		this.mockMvc.perform(delete("/api/lecturers/{id}", id))
		   .andExpect(status().isNotFound());
	}
}
