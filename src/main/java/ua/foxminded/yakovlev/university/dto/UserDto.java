package ua.foxminded.yakovlev.university.dto;

import java.util.Set;

import javax.validation.constraints.NotBlank;
import javax.validation.constraints.NotNull;

import lombok.Data;

@Data
public class UserDto {

	private Long id;	
	
	@NotBlank(message="validator.message.empty_username")
	private String name;
	
	@NotNull(message="validator.message.empty_person")
	private Long personId;
	
	@NotBlank(message="validator.message.empty_person_first_name")
	private String firstName;
	
	@NotBlank(message="validator.message.empty_person_last_name")
	private String lastName;
	
	@NotBlank(message="validator.message.empty_password")
	private String password;
	
	private Boolean enabled;
	
	private Set<RoleDto> roleSet;
}
