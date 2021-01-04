package ua.foxminded.yakovlev.university.mapper;

import java.util.List;

import org.mapstruct.Mapper;
import org.mapstruct.InjectionStrategy;
import ua.foxminded.yakovlev.university.dto.TimetableRecordDto;
import ua.foxminded.yakovlev.university.entity.TimetableRecord;

@Mapper(componentModel = "spring", uses = TimetableRecordMapper.class, injectionStrategy = InjectionStrategy.CONSTRUCTOR)
public interface TimetableRecordMapper {

	TimetableRecordDto toTimetableRecordDto(TimetableRecord timetableRecord);
	TimetableRecord toTimetableRecord(TimetableRecordDto timetableRecordDto);	
	List<TimetableRecordDto> toTimetableRecordDtos(List<TimetableRecord> timetableRecords);
}
