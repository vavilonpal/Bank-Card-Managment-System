package com.example.bankcards.util.mapper;


import com.example.bankcards.entity.Role;
import com.example.bankcards.util.enums.RoleType;
import org.mapstruct.Mapper;
import org.mapstruct.Named;
import org.springframework.stereotype.Component;

@Component
public class RoleMapper {

    @Named("defaultUserRole")
    public RoleType defaultUserRole() {
        return RoleType.USER;
    }
}
