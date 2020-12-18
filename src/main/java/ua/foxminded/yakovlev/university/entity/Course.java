package ua.foxminded.yakovlev.university.entity;

import java.io.Serializable;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.Table;
import javax.validation.constraints.NotBlank;
import javax.validation.constraints.Size;

import lombok.Data;

@Data 
@Entity
@Table (name="courses")
public class Course implements Serializable {

	private static final long serialVersionUID = 1L;

	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "course_id")
	private Long id;
	
	@Column(name = "course_name")
	@NotBlank(message="validator.message.empty_course_name")
	//@NotBlank(message="vvv")
	private String name;
	
	@Column(name = "course_description")
	@Size(max = 1024, message = "validator.message.course_description_too_long")
	private String description;
}
