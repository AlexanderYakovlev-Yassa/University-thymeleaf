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

import ua.foxminded.yakovlev.university.entity.Position;
import ua.foxminded.yakovlev.university.exception.NotFoundException;
import ua.foxminded.yakovlev.university.mapper.PositionMapper;
import ua.foxminded.yakovlev.university.service.PositionService;
import ua.foxminded.yakovlev.university.service.GroupService;
import ua.foxminded.yakovlev.university.service.LecturerService;
import ua.foxminded.yakovlev.university.service.CourseService;
import ua.foxminded.yakovlev.university.service.StudentService;
import ua.foxminded.yakovlev.university.service.TimetableRecordService;
import ua.foxminded.yakovlev.university.util.PositionGenerator;

@WebMvcTest(ApiPositionController.class)
class ApiPositionControllerIntegrationTest {

	@MockBean
	private PositionService positionService;
	
	@MockBean
	private GroupService gs;
	@MockBean
	private LecturerService ls;
	@MockBean
	private CourseService cs;
	@MockBean
	private StudentService ss;
	@MockBean
	private TimetableRecordService ts;
	
	@Autowired
	private MockMvc mockMvc;
	private ObjectMapper objectMapper;
	
	@Autowired
	private PositionMapper positionMapper;
	
	private PositionGenerator positionGenerator;
	
	@BeforeEach
	private void init() {
		objectMapper = new ObjectMapper();
		positionGenerator = new PositionGenerator();
	}
	
	@Test
	void findAllShouldReturnAllPositions() throws Exception {
		
		List<Position> positionList = positionGenerator.getPositionList();
		
		when(positionService.findAll()).thenReturn(positionList);
		
		this.mockMvc.perform(get("/api/positions"))
		   .andExpect(status().isOk())
		   .andExpect(content().contentType(MediaType.APPLICATION_JSON))
		   .andExpect(content().json(objectMapper.writeValueAsString(positionMapper.toPositionDtos(positionList))));
	}
	
	@Test
	void createShouldSavePositionIfitValidAndReturnSavedPosition() throws Exception {
		
		Position position = positionGenerator.getPositionList().get(0);
		String jsonPositionDto = objectMapper.writeValueAsString(positionMapper.toPositionDto(position));
		
		when(positionService.save(position)).thenReturn(position);
				
		this.mockMvc.perform(post("/api/positions").contentType(MediaType.APPLICATION_JSON)
	            .content(jsonPositionDto))
		   .andExpect(status().isCreated())
		   .andExpect(content().contentType(MediaType.APPLICATION_JSON))
		   .andExpect(content().json(jsonPositionDto));
	}
	
	@Test
	void createShouldReturnStatusIfPositionIsInvalid() throws Exception {
		
		Position position = positionGenerator.getPositionList().get(0);
		position.setName("");
		String jsonPositionDto = objectMapper.writeValueAsString(positionMapper.toPositionDto(position));
		
		when(positionService.save(position)).thenReturn(position);
				
		this.mockMvc.perform(post("/api/positions").contentType(MediaType.APPLICATION_JSON)
	            .content(jsonPositionDto))
		   .andExpect(status().isBadRequest())
		   .andExpect(content().contentType(MediaType.APPLICATION_JSON));
		
		verify(positionService, never()).save(position);
	}
	
	@Test
	void findByIdShouldReturnPositionIfSuchPositionExists() throws Exception {

		Position position = positionGenerator.getPositionList().get(0);
		Long id = 1L;
		String jsonPositionDto = objectMapper.writeValueAsString(positionMapper.toPositionDto(position));
		
		when(positionService.findById(id)).thenReturn(position);
				
		this.mockMvc.perform(get("/api/positions/{id}", id))
		   .andExpect(status().isOk())
		   .andExpect(content().contentType(MediaType.APPLICATION_JSON))
		   .andExpect(content().json(jsonPositionDto));
	}
	
	@Test
	void findByIdShouldReturnStatusNotFoundIfSuchPositionDoesNotExist() throws Exception {

		Long id = 1L;
		
		when(positionService.findById(id)).thenThrow(NotFoundException.class);
				
		this.mockMvc.perform(get("/api/positions/{id}", id))
		   .andExpect(status().isNotFound())
		   .andExpect(content().contentType(MediaType.APPLICATION_JSON));
	}
	
	@Test
	void updateShouldReturnUpdatedPositionDtoIfSuchPositionExistsAndValid() throws Exception {

		Position position = positionGenerator.getPositionList().get(0);
		Long id = position.getId();
		String jsonPositionDto = objectMapper.writeValueAsString(positionMapper.toPositionDto(position));
		
		when(positionService.update(position)).thenReturn(position);
				
		this.mockMvc.perform(put("/api/positions/{id}", id)
				.contentType(MediaType.APPLICATION_JSON)
				.content(jsonPositionDto))
		   .andExpect(status().isAccepted())
		   .andExpect(content().contentType(MediaType.APPLICATION_JSON))
		   .andExpect(content().json(jsonPositionDto));
	}
	
	@Test
	void updateShouldReturnStatusBadRequestIfSuchPositionExistsButInvalid() throws Exception {

		Position position = positionGenerator.getPositionList().get(0);
		Long id = position.getId();
		position.setName("");
		String jsonPositionDto = objectMapper.writeValueAsString(positionMapper.toPositionDto(position));
		
		when(positionService.update(position)).thenReturn(position);
				
		this.mockMvc.perform(put("/api/positions/{id}", id)
				.contentType(MediaType.APPLICATION_JSON)
				.content(jsonPositionDto))
		   .andExpect(status().isBadRequest())
		   .andExpect(content().contentType(MediaType.APPLICATION_JSON));
		
		verify(positionService, never()).update(position);
	}
	
	@Test
	void updateShouldReturnStatusNotFoundIfSuchPositionDoesNotExist() throws Exception {

		Position position = positionGenerator.getPositionList().get(0);
		Long id = position.getId();
		String jsonPositionDto = objectMapper.writeValueAsString(positionMapper.toPositionDto(position));
		
		when(positionService.update(position)).thenThrow(NotFoundException.class);
				
		this.mockMvc.perform(put("/api/positions/{id}", id)
				.contentType(MediaType.APPLICATION_JSON)
				.content(jsonPositionDto))
		   .andExpect(status().isNotFound())
		   .andExpect(content().contentType(MediaType.APPLICATION_JSON));
	}
	
	@Test
	void deleteShouldReturnStatusAcceptedIfSuchPositionExists() throws Exception {
		
		Long id = 1L;
		
		this.mockMvc.perform(delete("/api/positions/{id}", id))
		   .andExpect(status().isAccepted());
		
		verify(positionService).delete(id);
	}
	
	@Test
	void deleteShouldReturnStatusNotFoundIfSuchPositionDoesNotExist() throws Exception {
		
		Long id = 1L;
		
		doThrow(NotFoundException.class).when(positionService).delete(id);
		
		this.mockMvc.perform(delete("/api/positions/{id}", id))
		   .andExpect(status().isNotFound());
	}
}
