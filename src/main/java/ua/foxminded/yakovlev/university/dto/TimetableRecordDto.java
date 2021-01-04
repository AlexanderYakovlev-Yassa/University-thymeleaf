package ua.foxminded.yakovlev.university.dto;

import java.time.LocalDateTime;
import java.util.List;

import javax.validation.constraints.NotNull;

import org.springframework.format.annotation.DateTimeFormat;
import org.springframework.format.annotation.DateTimeFormat.ISO;

import lombok.Data;

@Data 
public class TimetableRecordDto {

	private Long id;

	@DateTimeFormat(iso = ISO.DATE_TIME)
	@NotNull
	private LocalDateTime date;

	@NotNull
	private CourseDto course;

	private List<GroupDto> groupList;

	@NotNull
	private LecturerDto lecturer;
}