package ua.foxminded.yakovlev.university.mapper;

import java.util.List;

import org.mapstruct.InjectionStrategy;
import org.mapstruct.Mapper;

import ua.foxminded.yakovlev.university.dto.GroupDto;
import ua.foxminded.yakovlev.university.entity.Group;

@Mapper(componentModel = "spring", uses = GroupMapper.class, injectionStrategy = InjectionStrategy.CONSTRUCTOR)
public interface GroupMapper {

	GroupDto toGroupDto(Group group);
	Group toGroup(GroupDto groupDto);
	List<GroupDto> toGroupDtos(List<Group> groups);
}
