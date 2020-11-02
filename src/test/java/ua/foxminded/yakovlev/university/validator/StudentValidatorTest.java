package ua.foxminded.yakovlev.university.validator;

import static org.junit.jupiter.api.Assertions.*;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import ua.foxminded.yakovlev.university.entity.Group;
import ua.foxminded.yakovlev.university.entity.Student;

class StudentValidatorTest {
	
	private StudentValidator validator;
	
	@BeforeEach
	void initValidator() {
		this.validator = new StudentValidator();
	}

	@Test
	void checkShouldNonReturnAnyExceptionWhenStudentNameConsistsOfRussianLetters() {
		
		Student student = getValidStudent();
		student.setFirstName("Имя");
		
		try {
		validator.check(student);		
		} catch (Exception e) {
			fail(e);
		}
	}
	
	@Test
	void checkShouldNonReturnAnyExceptionWhenStudentNameConsistsOfLatinLetters() {
		
		Student student = getValidStudent();
		
		try {
		validator.check(student);		
		} catch (Exception e) {
			fail(e);
		}
	}
	
	@Test
	void checkShouldReturnIllegalArgumentExceptionWhenStudentNameHasWhitespace() {
		
		Student student = getValidStudent();
		student.setFirstName(" Name");		
		
		assertThrows(IllegalArgumentException.class, () -> validator.check(student));	
	}
	
	@Test
	void checkShouldReturnIllegalArgumentExceptionWhenStudentNameHasFirstNotCapitalisedLetter() {
		
		Student student = getValidStudent();
		student.setFirstName("name");		
		
		assertThrows(IllegalArgumentException.class, () -> validator.check(student));	
	}
	
	Student getValidStudent() {
		
		Student student = new Student();
		Group group = new Group();
		
		group.setId(1L);
		group.setName("aa-11");
		student.setPersonId(1L);
		student.setStudentId(1L);
		student.setFirstName("Name");
		student.setLastName("Surename");
		student.setGroup(group);
		
		return student;
	}
}
