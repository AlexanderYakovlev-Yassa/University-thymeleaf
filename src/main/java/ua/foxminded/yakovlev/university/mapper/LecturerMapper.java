package ua.foxminded.yakovlev.university.mapper;

import java.util.List;

import org.mapstruct.Mapper;
import org.mapstruct.InjectionStrategy;
import ua.foxminded.yakovlev.university.dto.LecturerDto;
import ua.foxminded.yakovlev.university.entity.Lecturer;

@Mapper(componentModel = "spring", uses = LecturerMapper.class, injectionStrategy = InjectionStrategy.CONSTRUCTOR)
public interface LecturerMapper {

	LecturerDto toLecturerDto(Lecturer lecturer);
	Lecturer toLecturer(LecturerDto lecturerDto);
	List<LecturerDto> toLecturerDtos(List<Lecturer> lecturers);
}
