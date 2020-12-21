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
@Table (name="lecturers")
@PrimaryKeyJoinColumn(name = "lecturer_person_id")
public class Lecturer extends Person {

	private static final long serialVersionUID = 1L;

	@ManyToOne
    @JoinColumn(name="lecturer_position_id", nullable=true)
    private Position position;
}