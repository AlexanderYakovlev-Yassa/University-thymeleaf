package ua.foxminded.yakovlev.university.entity;

import lombok.Builder;
import lombok.Data;

@Builder
@Data public class Group {

	private Integer id;
	private String name;
}
