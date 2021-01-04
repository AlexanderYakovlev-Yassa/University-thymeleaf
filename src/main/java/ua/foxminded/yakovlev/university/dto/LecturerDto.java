package ua.foxminded.yakovlev.university.dto;

import javax.validation.constraints.NotBlank;

import lombok.Data;

@Data 
public class LecturerDto {

	private Long personId;
	
	@NotBlank(message="validator.message.empty_person_first_name")
	private String firstName;
	
	@NotBlank(message="validator.message.empty_person_last_name")
	private String lastName;	

    private PositionDto position;
}