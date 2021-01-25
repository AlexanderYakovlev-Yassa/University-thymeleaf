package ua.foxminded.yakovlev.university.controller.api;

import static org.mockito.Mockito.*;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;

import java.time.LocalDateTime;
import java.util.List;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.http.MediaType;
import org.springframework.test.web.servlet.MockMvc;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.datatype.jdk8.Jdk8Module;
import com.fasterxml.jackson.datatype.jsr310.JavaTimeModule;
import com.fasterxml.jackson.module.paramnames.ParameterNamesModule;

import ua.foxminded.yakovlev.university.entity.TimetableRecord;
import ua.foxminded.yakovlev.university.exception.NotFoundException;
import ua.foxminded.yakovlev.university.mapper.TimetableRecordMapper;
import ua.foxminded.yakovlev.university.service.TimetableRecordService;
import ua.foxminded.yakovlev.university.service.GroupService;
import ua.foxminded.yakovlev.university.service.LecturerService;
import ua.foxminded.yakovlev.university.service.PositionService;
import ua.foxminded.yakovlev.university.service.StudentService;
import ua.foxminded.yakovlev.university.service.CourseService;
import ua.foxminded.yakovlev.university.util.TimetableGenerator;

@WebMvcTest(ApiTimetableRecordController.class)
class ApiTimetableRecordControllerIntegrationTest {

	@MockBean
	private TimetableRecordService timetableRecordService;
	
	@MockBean
	private GroupService gs;
	@MockBean
	private LecturerService ls;
	@MockBean
	private PositionService ps;
	@MockBean
	private StudentService ss;
	@MockBean
	private CourseService cs;
	
	@Autowired
	private MockMvc mockMvc;
	private ObjectMapper objectMapper;
	
	@Autowired
	private TimetableRecordMapper timetableRecordMapper;
	
	private TimetableGenerator timetableGenerator;
	
	@BeforeEach
	private void init() {
		objectMapper = new ObjectMapper()
				.registerModule(new ParameterNamesModule())
				.registerModule(new Jdk8Module())
				.registerModule(new JavaTimeModule());
		objectMapper.findAndRegisterModules();
		timetableGenerator = new TimetableGenerator();
	}
	
	@Test
	void findAllShouldReturnAllTimetableRecords() throws Exception {
		
		List<TimetableRecord> timetableRecordList = timetableGenerator.getTimetable();
		
		when(timetableRecordService.findAll()).thenReturn(timetableRecordList);
		
		this.mockMvc.perform(get("/api/timetableRecords"))
		   .andExpect(status().isOk())
		   .andExpect(content().contentType(MediaType.APPLICATION_JSON))
		   .andExpect(content().json(objectMapper.writeValueAsString(timetableRecordMapper.toTimetableRecordDtos(timetableRecordList))));
	}
	
	@Test
	void createShouldSaveTimetableRecordIfitValidAndReturnSavedTimetableRecord() throws Exception {
		
		TimetableRecord timetableRecord = timetableGenerator.getTimetable().get(0);
		String jsonTimetableRecordDto = objectMapper.writeValueAsString(timetableRecordMapper.toTimetableRecordDto(timetableRecord));
		
		when(timetableRecordService.save(timetableRecord)).thenReturn(timetableRecord);
				
		this.mockMvc.perform(post("/api/timetableRecords").contentType(MediaType.APPLICATION_JSON)
	            .content(jsonTimetableRecordDto))
		   .andExpect(status().isCreated())
		   .andExpect(content().contentType(MediaType.APPLICATION_JSON))
		   .andExpect(content().json(jsonTimetableRecordDto));
	}
	
	@Test
	void createShouldReturnStatusIfTimetableRecordIsInvalid() throws Exception {
		
		TimetableRecord timetableRecord = timetableGenerator.getTimetable().get(0);
		timetableRecord.setCourse(null);
		String jsonTimetableRecordDto = objectMapper.writeValueAsString(timetableRecordMapper.toTimetableRecordDto(timetableRecord));
		
		when(timetableRecordService.save(timetableRecord)).thenReturn(timetableRecord);
				
		this.mockMvc.perform(post("/api/timetableRecords").contentType(MediaType.APPLICATION_JSON)
	            .content(jsonTimetableRecordDto))
		   .andExpect(status().isBadRequest())
		   .andExpect(content().contentType(MediaType.APPLICATION_JSON));
		
		verify(timetableRecordService, never()).save(timetableRecord);
	}
	
	@Test
	void findByIdShouldReturnTimetableRecordIfSuchTimetableRecordExists() throws Exception {

		TimetableRecord timetableRecord = timetableGenerator.getTimetable().get(0);
		Long id = 1L;
		String jsonTimetableRecordDto = objectMapper.writeValueAsString(timetableRecordMapper.toTimetableRecordDto(timetableRecord));
		
		when(timetableRecordService.findById(id)).thenReturn(timetableRecord);
				
		this.mockMvc.perform(get("/api/timetableRecords/{id}", id))
		   .andExpect(status().isOk())
		   .andExpect(content().contentType(MediaType.APPLICATION_JSON))
		   .andExpect(content().json(jsonTimetableRecordDto));
	}
	
	@Test
	void findByIdShouldReturnStatusNotFoundIfSuchTimetableRecordDoesNotExist() throws Exception {

		Long id = 1L;
		
		when(timetableRecordService.findById(id)).thenThrow(NotFoundException.class);
				
		this.mockMvc.perform(get("/api/timetableRecords/{id}", id))
		   .andExpect(status().isNotFound())
		   .andExpect(content().contentType(MediaType.APPLICATION_JSON));
	}
	
	@Test
	void updateShouldReturnUpdatedTimetableRecordDtoIfSuchTimetableRecordExistsAndValid() throws Exception {

		TimetableRecord timetableRecord = timetableGenerator.getTimetable().get(0);
		Long id = timetableRecord.getId();
		String jsonTimetableRecordDto = objectMapper.writeValueAsString(timetableRecordMapper.toTimetableRecordDto(timetableRecord));
		
		when(timetableRecordService.update(timetableRecord)).thenReturn(timetableRecord);
				
		this.mockMvc.perform(put("/api/timetableRecords/{id}", id)
				.contentType(MediaType.APPLICATION_JSON)
				.content(jsonTimetableRecordDto))
		   .andExpect(status().isAccepted())
		   .andExpect(content().contentType(MediaType.APPLICATION_JSON))
		   .andExpect(content().json(jsonTimetableRecordDto));
	}
	
	@Test
	void updateShouldReturnStatusBadRequestIfSuchTimetableRecordExistsButInvalid() throws Exception {

		TimetableRecord timetableRecord = timetableGenerator.getTimetable().get(0);
		Long id = timetableRecord.getId();
		timetableRecord.setCourse(null);
		String jsonTimetableRecordDto = objectMapper.writeValueAsString(timetableRecordMapper.toTimetableRecordDto(timetableRecord));
		
		when(timetableRecordService.update(timetableRecord)).thenReturn(timetableRecord);
				
		this.mockMvc.perform(put("/api/timetableRecords/{id}", id)
				.contentType(MediaType.APPLICATION_JSON)
				.content(jsonTimetableRecordDto))
		   .andExpect(status().isBadRequest())
		   .andExpect(content().contentType(MediaType.APPLICATION_JSON));
		
		verify(timetableRecordService, never()).update(timetableRecord);
	}
	
	@Test
	void updateShouldReturnStatusNotFoundIfSuchTimetableRecordDoesNotExist() throws Exception {

		TimetableRecord timetableRecord = timetableGenerator.getTimetable().get(0);
		System.out.println(timetableGenerator.getTimetable().get(0).getDate());
		Long id = timetableRecord.getId();
		String jsonTimetableRecordDto = objectMapper.writeValueAsString(timetableRecordMapper.toTimetableRecordDto(timetableRecord));
		
		when(timetableRecordService.update(timetableRecord)).thenThrow(NotFoundException.class);
				
		this.mockMvc.perform(put("/api/timetableRecords/{id}", id)
				.contentType(MediaType.APPLICATION_JSON)
				.content(jsonTimetableRecordDto))
		   .andExpect(status().isNotFound())
		   .andExpect(content().contentType(MediaType.APPLICATION_JSON));
	}
	
	@Test
	void deleteShouldReturnStatusAcceptedIfSuchTimetableRecordExists() throws Exception {
		
		Long id = 1L;
		
		this.mockMvc.perform(delete("/api/timetableRecords/{id}", id))
		   .andExpect(status().isAccepted());
		
		verify(timetableRecordService).delete(id);
	}
	
	@Test
	void deleteShouldReturnStatusNotFoundIfSuchTimetableRecordDoesNotExist() throws Exception {
		
		Long id = 1L;
		
		doThrow(NotFoundException.class).when(timetableRecordService).delete(id);
		
		this.mockMvc.perform(delete("/api/timetableRecords/{id}", id))
		   .andExpect(status().isNotFound());
	}
	
	@Test
	void findByDateShouldReturnStatusOkAndTimetableRecords() throws Exception {
		
		List<TimetableRecord> timetableRecordList = timetableGenerator.getTimetable();
		String start = "2020-10-16T00:00:00";
		String end = "2020-10-17T00:00:00";
		LocalDateTime startDate = LocalDateTime.parse(start);
		LocalDateTime endDate = LocalDateTime.parse(end);		
		
		when(timetableRecordService.findByPeriodOfTime(startDate, endDate)).thenReturn(timetableRecordList);
		
		this.mockMvc.perform(get("/api/timetableRecords/date")
				.param("start", start)
				.param("end", end))
		   .andExpect(status().isOk())
		   .andExpect(content().contentType(MediaType.APPLICATION_JSON))
		   .andExpect(content().json(objectMapper.writeValueAsString(timetableRecordMapper.toTimetableRecordDtos(timetableRecordList))));
	}
	
	@Test
	void findByDateShouldReturnStatusBadRequestIfParamsIsNotValid() throws Exception {
		
		String start = "not valid date";
		String end = "not valid date";
		
		this.mockMvc.perform(get("/api/timetableRecords/date")
				.param("start", start)
				.param("end", end))
		   .andExpect(status().isBadRequest())
		   .andExpect(content().contentType(MediaType.APPLICATION_JSON));
		
		verify(timetableRecordService, never()).findByPeriodOfTime(any(LocalDateTime.class), any(LocalDateTime.class));
	}
	
	@Test
	void findByGroupShouldReturnStatusOkAndTimetableRecords() throws Exception {
		
		List<TimetableRecord> timetableRecordList = timetableGenerator.getTimetable();
		String start = "2020-10-16T00:00:00";
		String end = "2020-10-17T00:00:00";
		Long id = 1L;
		LocalDateTime startDate = LocalDateTime.parse(start);
		LocalDateTime endDate = LocalDateTime.parse(end);		
		
		when(timetableRecordService.findByGroup(id, startDate, endDate)).thenReturn(timetableRecordList);
		
		this.mockMvc.perform(get("/api/timetableRecords/date/group")
				.param("start", start)
				.param("end", end)
				.param("group-id", id.toString()))
		   .andExpect(status().isOk())
		   .andExpect(content().contentType(MediaType.APPLICATION_JSON))
		   .andExpect(content().json(objectMapper.writeValueAsString(timetableRecordMapper.toTimetableRecordDtos(timetableRecordList))));
	}
	
	@Test
	void findByGroupShouldReturnStatusNotFoundIfSuchGroupIdDoesNotExist() throws Exception {
		
		String start = "2020-10-16T00:00:00";
		String end = "2020-10-17T00:00:00";
		Long id = 1L;
		LocalDateTime startDate = LocalDateTime.parse(start);
		LocalDateTime endDate = LocalDateTime.parse(end);		
		
		when(timetableRecordService.findByGroup(id, startDate, endDate)).thenThrow(NotFoundException.class);
		
		this.mockMvc.perform(get("/api/timetableRecords/date/group")
				.param("start", start)
				.param("end", end)
				.param("group-id", id.toString()))
		   .andExpect(status().isNotFound())
		   .andExpect(content().contentType(MediaType.APPLICATION_JSON));
	}
	
	@Test
	void findByGroupShouldReturnStatusBadRequestIfDateIsNotValid() throws Exception {
		
		String start = "invalid date";
		String end = "2020-10-17T00:00:00";
		Long id = 1L;
		
		this.mockMvc.perform(get("/api/timetableRecords/date/group")
				.param("start", start)
				.param("end", end)
				.param("group-id", id.toString()))
		   .andExpect(status().isBadRequest())
		   .andExpect(content().contentType(MediaType.APPLICATION_JSON));
		
		verify(timetableRecordService, never()).findByGroup(anyLong(), any(LocalDateTime.class), any(LocalDateTime.class));
	}
	
	@Test
	void findByLecturerShouldReturnStatusOkAndTimetableRecords() throws Exception {
		
		List<TimetableRecord> timetableRecordList = timetableGenerator.getTimetable();
		String start = "2020-10-16T00:00:00";
		String end = "2020-10-17T00:00:00";
		Long id = 1L;
		LocalDateTime startDate = LocalDateTime.parse(start);
		LocalDateTime endDate = LocalDateTime.parse(end);		
		
		when(timetableRecordService.findByLecturer(id, startDate, endDate)).thenReturn(timetableRecordList);
		
		this.mockMvc.perform(get("/api/timetableRecords/date/lecturer")
				.param("start", start)
				.param("end", end)
				.param("lecturer-id", id.toString()))
		   .andExpect(status().isOk())
		   .andExpect(content().contentType(MediaType.APPLICATION_JSON))
		   .andExpect(content().json(objectMapper.writeValueAsString(timetableRecordMapper.toTimetableRecordDtos(timetableRecordList))));
	}
	
	@Test
	void findByLecturerShouldReturnStatusNotFoundIfSuchLecturerIdDoesNotExist() throws Exception {
		
		String start = "2020-10-16T00:00:00";
		String end = "2020-10-17T00:00:00";
		Long id = 1L;
		LocalDateTime startDate = LocalDateTime.parse(start);
		LocalDateTime endDate = LocalDateTime.parse(end);		
		
		when(timetableRecordService.findByLecturer(id, startDate, endDate)).thenThrow(NotFoundException.class);
		
		this.mockMvc.perform(get("/api/timetableRecords/date/lecturer")
				.param("start", start)
				.param("end", end)
				.param("lecturer-id", id.toString()))
		   .andExpect(status().isNotFound())
		   .andExpect(content().contentType(MediaType.APPLICATION_JSON));
	}
	
	@Test
	void findByLecturerShouldReturnStatusBadRequestIfDateIsNotValid() throws Exception {
		
		String start = "invalid date";
		String end = "2020-10-17T00:00:00";
		Long id = 1L;
		
		this.mockMvc.perform(get("/api/timetableRecords/date/lecturer")
				.param("start", start)
				.param("end", end)
				.param("lecturer-id", id.toString()))
		   .andExpect(status().isBadRequest())
		   .andExpect(content().contentType(MediaType.APPLICATION_JSON));
		
		verify(timetableRecordService, never()).findByLecturer(anyLong(), any(LocalDateTime.class), any(LocalDateTime.class));
	}
}
