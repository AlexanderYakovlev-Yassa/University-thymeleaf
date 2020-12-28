package ua.foxminded.yakovlev.university.mapper;

import java.util.List;

import org.mapstruct.Mapper;
import org.mapstruct.InjectionStrategy;
import ua.foxminded.yakovlev.university.dto.CourseDto;
import ua.foxminded.yakovlev.university.entity.Course;

@Mapper(componentModel = "spring", uses = CourseMapper.class, injectionStrategy = InjectionStrategy.CONSTRUCTOR)
public interface CourseMapper {

	CourseDto toCourseDto(Course course);
	Course toCourse(CourseDto courseDto);
	List<CourseDto> toCourseDtos(List<Course> courses);
}
