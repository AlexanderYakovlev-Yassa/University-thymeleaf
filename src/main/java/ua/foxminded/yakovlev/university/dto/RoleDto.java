package ua.foxminded.yakovlev.university.dto;

import java.util.Set;

import javax.validation.constraints.NotBlank;
import javax.validation.constraints.NotNull;

import lombok.Data;
import lombok.ToString;
import ua.foxminded.yakovlev.university.entity.Authority;

@Data
@ToString
public class RoleDto {

	private Long id;
	
	@NotBlank(message="validator.message.empty_role_name")
	private String name;
	
	@NotNull(message="validator.message.null_authorities")
	private Set<Authority> authorities;
}
