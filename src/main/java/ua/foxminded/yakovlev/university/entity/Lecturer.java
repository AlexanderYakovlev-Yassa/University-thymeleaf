package ua.foxminded.yakovlev.university.entity;

import lombok.Builder;
import lombok.Data;
import lombok.EqualsAndHashCode;

@Builder
@EqualsAndHashCode(callSuper=true)
@Data public class Lecturer extends Person {

	private Long id;
	private Position position;
}