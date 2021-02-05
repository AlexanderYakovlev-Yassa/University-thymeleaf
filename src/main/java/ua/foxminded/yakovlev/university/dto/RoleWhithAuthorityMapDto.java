package ua.foxminded.yakovlev.university.dto;

import java.util.Map;

import javax.validation.constraints.NotBlank;
import javax.validation.constraints.NotNull;

import lombok.Data;
import lombok.ToString;

@Data
@ToString
public class RoleWhithAuthorityMapDto {

	private Long id;
	
	@NotBlank(message="validator.message.empty_role_name")
	private String name;
	
	@NotNull(message="validator.message.null_authorities")
	private Map<String, Boolean> authorities;
}
