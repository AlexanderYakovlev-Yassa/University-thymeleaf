package ua.foxminded.yakovlev.university.entity;

import lombok.Data;
import lombok.EqualsAndHashCode;

@EqualsAndHashCode(callSuper=true)
@Data public class Student extends Person {

	private Long studentId;
	private Group group;
}
