package com.deliktas.internshipproject.mapper;


import com.deliktas.internshipproject.auth.model.User;
import com.deliktas.internshipproject.auth.model.UserDTO;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;

@Mapper(componentModel = "spring")
public interface UserMapper {

    @Mapping(expression = "java(user.getFirstName() + ' ' + user.getLastName())", target = "name")
    UserDTO userToUserDTO(User user);

    @Mapping(target = "firstName", expression = "java(user.getName().split(\" \")[0])")
    @Mapping(target = "lastName", expression = "java(user.getName().split(\" \")[1])")
    User userDTOToUser(UserDTO user);

}
