package ua.foxminded.yakovlev.university.entity;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.Table;

import lombok.Data;
import lombok.EqualsAndHashCode;

@EqualsAndHashCode(callSuper=true)
@Data 
@Entity
@Table (name="students")
public class Student extends Person {

	@Id
    @Column(name = "student_id")
	private Long studentId;
	
	@ManyToOne
    @JoinColumn(name="student_group_id", nullable=false)
    private Group group;
}
