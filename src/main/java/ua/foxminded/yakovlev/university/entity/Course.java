package ua.foxminded.yakovlev.university.entity;

import lombok.Builder;
import lombok.Data;

@Builder
@Data public class Course {

	private Integer id;
	private String name;
	private String description;
}
