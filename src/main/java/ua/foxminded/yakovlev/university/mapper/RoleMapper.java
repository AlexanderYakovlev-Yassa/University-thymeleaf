package ua.foxminded.yakovlev.university.mapper;

import java.util.List;

import org.mapstruct.Mapper;
import org.mapstruct.InjectionStrategy;
import ua.foxminded.yakovlev.university.dto.RoleDto;
import ua.foxminded.yakovlev.university.entity.Role;

@Mapper(componentModel = "spring", uses = RoleMapper.class, injectionStrategy = InjectionStrategy.CONSTRUCTOR)
public interface RoleMapper {

	RoleDto toRoleDto(Role role);
	Role toRole(RoleDto roleDto);
	List<RoleDto> toRoleDtos(List<Role> roles);
}
