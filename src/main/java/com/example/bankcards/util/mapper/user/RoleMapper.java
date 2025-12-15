package com.example.bankcards.util.mapper.user;


import com.example.bankcards.entity.Role;
import com.example.bankcards.service.RoleService;
import com.example.bankcards.util.enums.RoleType;
import lombok.RequiredArgsConstructor;
import org.mapstruct.Named;
import org.springframework.stereotype.Component;

@Component
@RequiredArgsConstructor
public class RoleMapper {
    private final RoleService roleService;

    @Named("roleFromEnum")
    public Role map(RoleType roleType) {
        return roleService.getByName(RoleType.USER);
    }
}
