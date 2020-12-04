package ua.foxminded.yakovlev.university.entity;

import javax.persistence.Entity;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.PrimaryKeyJoinColumn;
import javax.persistence.Table;

import lombok.Data;
import lombok.EqualsAndHashCode;

@EqualsAndHashCode(callSuper=true)
@Data 
@Entity
@Table (name="students")
@PrimaryKeyJoinColumn(name = "student_person_id")
public class Student extends Person {

	private static final long serialVersionUID = 1L;
	
	@ManyToOne
    @JoinColumn(name="student_group_id")
    private Group group;
}
