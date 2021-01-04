package ua.foxminded.yakovlev.university.dto;

import javax.validation.constraints.NotBlank;
import javax.validation.constraints.Pattern;

import lombok.Data;

@Data
public class GroupDto {

	private Long id;
		
	@NotBlank(message="validator.message.empty_group_name")
	@Pattern(regexp="^[a-z]{2}[-][0-9]{2}$", 
			message="validator.message.name_pattern_not_matches")
	private String name;
}
