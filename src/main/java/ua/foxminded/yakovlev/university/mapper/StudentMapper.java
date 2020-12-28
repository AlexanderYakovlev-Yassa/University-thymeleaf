package ua.foxminded.yakovlev.university.mapper;

import java.util.List;

import org.mapstruct.Mapper;
import org.mapstruct.InjectionStrategy;
import ua.foxminded.yakovlev.university.dto.StudentDto;
import ua.foxminded.yakovlev.university.entity.Student;

@Mapper(componentModel = "spring", uses = StudentMapper.class, injectionStrategy = InjectionStrategy.CONSTRUCTOR)
public interface StudentMapper {

	StudentDto toStudentDto(Student student);
	
	Student toStudent(StudentDto studentDto);
	List<StudentDto> toStudentDtos(List<Student> students);
}
