package ua.foxminded.yakovlev.university.entity;

import java.time.LocalDateTime;
import java.util.List;

import lombok.Builder;
import lombok.Data;

@Builder
@Data public class TimetableRecord {

	private Long id;
	private LocalDateTime date;
	private Course course;
	private List<Group> groupList;
	private Lecturer lecturer;
}