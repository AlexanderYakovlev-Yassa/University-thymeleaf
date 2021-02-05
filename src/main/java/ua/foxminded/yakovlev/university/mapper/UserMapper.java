package ua.foxminded.yakovlev.university.mapper;

import java.util.List;

import org.mapstruct.Mapper;
import org.mapstruct.Mapping;
import org.mapstruct.InjectionStrategy;
import ua.foxminded.yakovlev.university.dto.UserDto;
import ua.foxminded.yakovlev.university.entity.User;

@Mapper(componentModel = "spring", uses = UserMapper.class, injectionStrategy = InjectionStrategy.CONSTRUCTOR)
public interface UserMapper {

	@Mapping(source = "person.personId", target = "personId")
	@Mapping(source = "person.firstName", target = "firstName")
	@Mapping(source = "person.lastName", target = "lastName")
	UserDto toUserDto(User user);
	
	@Mapping(source = "personId", target = "person.personId")
	@Mapping(source = "firstName", target = "person.firstName")
	@Mapping(source = "lastName", target = "person.lastName")
	User toUser(UserDto userDto);	
		
	List<UserDto> toUserDtos(List<User> users);
}
