package com.smartosc.fintech.lms.service.mapper;

import com.smartosc.fintech.lms.dto.UserDto;
import com.smartosc.fintech.lms.entity.UserEntity;
import org.mapstruct.Mapper;
import org.mapstruct.factory.Mappers;

@Mapper
public interface UserMapper {
    UserMapper INSTANCE = Mappers.getMapper(UserMapper.class);

    UserDto mapToDto(UserEntity userEntity);
    UserEntity mapToEntity(UserDto userDto);
}
