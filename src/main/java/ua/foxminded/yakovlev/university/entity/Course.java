package ua.foxminded.yakovlev.university.entity;

import java.io.Serializable;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.Table;

import lombok.Data;

@Data 
@Entity
@Table (name="courses")
public class Course implements Serializable {

	private static final long serialVersionUID = 1L;

	@Id
    @Column(name = "course_id")
	private Long id;
	
	@Column(name = "course_name, nullable=false, unique=false")
	private String name;
	
	@Column(name = "course_description, nullable=true, unique=false")
	private String description;
}
