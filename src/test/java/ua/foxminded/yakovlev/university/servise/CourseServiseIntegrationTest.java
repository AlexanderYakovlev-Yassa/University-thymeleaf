package ua.foxminded.yakovlev.university.servise;

import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;
import static org.mockito.ArgumentMatchers.*;
import static org.junit.jupiter.api.Assertions.*;

import java.util.List;
import java.util.Optional;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import ua.foxminded.yakovlev.university.entity.Course;
import ua.foxminded.yakovlev.university.exception.NotFoundException;
import ua.foxminded.yakovlev.university.repository.CourseRepository;
import ua.foxminded.yakovlev.university.service.CourseService;
import ua.foxminded.yakovlev.university.service.impl.CourseServiceImpl;
import ua.foxminded.yakovlev.university.util.CourseGenerator;

@ExtendWith(MockitoExtension.class)
class CourseServiseIntegrationTest {
	
	CourseService servise;
	CourseGenerator courseGenerator;
	List<Course> courseList;
	Course course;
	Long id;
	
	@Mock
	CourseRepository repository;
	
	@BeforeEach
    public void init() {
    	servise = new CourseServiceImpl(repository);
    	courseGenerator = new CourseGenerator();
    	courseList = courseGenerator.getCourseList();
    	course = courseList.get(0);
    	id = course.getId();
    }
    
	@Test
	void findAllShouldReturnAllTheCourses() {
		when(repository.findAll()).thenReturn(courseList);
		assertEquals(servise.findAll(), courseList);
	}
    
	@Test
	void findByIdShouldReturnCourse() {		
		when(repository.findById(id)).thenReturn(Optional.of(course));		
		assertEquals(servise.findById(id), course);
	}
    
	@Test
	void findByIdShouldThrowNotFoundExceptionWhenSuchCourseDoesNotExist() {		
		when(repository.findById(id)).thenReturn(Optional.empty());		
		assertThrows(NotFoundException.class, () -> servise.findById(id));
	}
	
	@Test
	void saveShouldReturnSavedCourse() {		
		when(repository.save(course)).thenReturn(course);		
		assertEquals(servise.save(course), course);		
		verify(repository).save(course);
	}
    
	@Test
	void updateShouldSholdReturnUpdatedCourseWhecSuchOneExists() {
		when(repository.saveAndFlush(course)).thenReturn(course);		
		assertEquals(servise.update(course), course);		
		verify(repository).saveAndFlush(course);
	}
    
	@Test
	void updateShouldThrowNotFoundExceptionWhenSuchCourseDoesNotExist() {	
		when(repository.saveAndFlush(course)).thenThrow(NotFoundException.class);
		assertThrows(NotFoundException.class, () -> servise.update(course));
		verify(repository).saveAndFlush(course);
	}
	
	@Test
	void deleteShouldReturnNothingWhenSuchCourseExists() {
		when(repository.findById(id)).thenReturn(Optional.of(course));
		servise.delete(id);
		verify(repository).delete(course);
	}
	
	@Test
	void deleteShouldReturnNotFoundExceptionWhenSuchCourseDoesNotExist() {
		when(repository.findById(id)).thenThrow(NotFoundException.class);
		assertThrows(NotFoundException.class, () -> servise.delete(id));
	}
    
	@Test
	void findCourseByNameShouldReturnCourse() {
		when(repository.findCourseByName(anyString())).thenReturn(course);
		assertEquals(servise.findCourseByName(anyString()), course);
	}
    
	@Test
	void findCourseByNameShouldThrowNotFoundExceptionWhenSuchCourseDoesNotExist() {		
		when(repository.findCourseByName(anyString())).thenThrow(NotFoundException.class);
		assertThrows(NotFoundException.class, () -> servise.findCourseByName(anyString()));
	}
}