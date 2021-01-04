package ua.foxminded.yakovlev.university.dto;

import java.io.Serializable;

import javax.validation.constraints.NotBlank;
import javax.validation.constraints.Size;

import lombok.Data;

@Data 
public class CourseDto implements Serializable {

	private static final long serialVersionUID = 1L;
	
	private Long id;
		
	@NotBlank(message="validator.message.empty_course_name")
	private String name;
		
	@Size(max = 1024, message = "validator.message.course_description_too_long")
	private String description;
}
