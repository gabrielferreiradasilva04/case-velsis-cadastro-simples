package com.backend.backend.util.mapper;


import com.backend.backend.domain.UserEntity;
import com.backend.backend.dto.UserRequestDTO;
import com.backend.backend.dto.UserResponseDTO;
import org.mapstruct.Mapper;

@Mapper(componentModel = "spring")
public interface UserMapper {

    UserEntity responseDtoToEntity(UserResponseDTO dto);
    UserEntity requestDtoToEntity(UserRequestDTO dto);

    UserResponseDTO entityToResponseDto(UserEntity entity);


}
