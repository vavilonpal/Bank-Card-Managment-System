package com.example.bankcards.util.mapper;


import com.example.bankcards.dto.user.request.RegisterUserRequest;
import com.example.bankcards.entity.User;
import com.example.bankcards.util.enums.RoleType;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;

@Mapper(
        componentModel = "spring",
        uses = {
                RoleMapper.class,
                PasswordMapper.class
        },
        imports = RoleType.class
)
public interface UserMapper {


    @Mapping(target = "id", ignore = true)

    @Mapping(
            source = "password",
            target = "passwordHash",
            qualifiedByName = "encodePassword"

    )
    @Mapping(
            target = "role",
            expression = "java(RoleType.USER)"
    )
    @Mapping(target = "isActive", constant = "true")

    @Mapping(target = "createdAt", ignore = true)
    @Mapping(target = "updatedAt", ignore = true)

    User toEntity(RegisterUserRequest userRequest);
}
