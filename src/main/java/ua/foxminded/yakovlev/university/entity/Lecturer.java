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
@Table (name="lecturers")
public class Lecturer extends Person {

	@Id
    @Column(name = "lecturer_id")
	private Long lecturerId;
	
	@ManyToOne
    @JoinColumn(name="lecturer_position_id", nullable=true)
    private Position position;
}