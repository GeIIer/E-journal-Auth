package org.example.service_auth.mappers;

import org.example.service_auth.dto.UserDto;
import org.example.service_auth.models.User;
import org.mapstruct.Mapper;
import org.mapstruct.MappingConstants;
import org.mapstruct.ReportingPolicy;

@Mapper(unmappedTargetPolicy = ReportingPolicy.IGNORE, componentModel = MappingConstants.ComponentModel.SPRING)
public interface UserMapper extends CommonMapper<UserDto, User> {

}
