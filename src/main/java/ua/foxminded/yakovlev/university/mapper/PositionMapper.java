package ua.foxminded.yakovlev.university.mapper;

import java.util.List;

import org.mapstruct.Mapper;
import org.mapstruct.InjectionStrategy;
import ua.foxminded.yakovlev.university.dto.PositionDto;
import ua.foxminded.yakovlev.university.entity.Position;

@Mapper(componentModel = "spring", uses = PositionMapper.class, injectionStrategy = InjectionStrategy.CONSTRUCTOR)
public interface PositionMapper {

	PositionDto toPositionDto(Position position);
	Position toPosition(PositionDto positionDto);
	List<PositionDto> toPositionDtos(List<Position> positions);
}
