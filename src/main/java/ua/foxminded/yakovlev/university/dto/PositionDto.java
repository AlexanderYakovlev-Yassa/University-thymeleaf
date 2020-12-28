package ua.foxminded.yakovlev.university.dto;

import javax.validation.constraints.NotBlank;

import lombok.Data;

@Data 
public class PositionDto {

	private Long id;
	
	@NotBlank(message="validator.message.empty_position_name")
	private String name;
}
