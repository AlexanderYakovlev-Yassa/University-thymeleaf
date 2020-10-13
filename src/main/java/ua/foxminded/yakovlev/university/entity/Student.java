package ua.foxminded.yakovlev.university.entity;

import lombok.Builder;
import lombok.Data;
import lombok.EqualsAndHashCode;

@Builder
@EqualsAndHashCode(callSuper=true)
@Data public class Student extends Person {

	private Long id;
	private Group group;
}
