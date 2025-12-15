package com.example.bankcards.service;


import com.example.bankcards.entity.Role;
import com.example.bankcards.exception.entity.role.RoleNotFoundException;
import com.example.bankcards.repository.RoleRepository;
import com.example.bankcards.util.enums.RoleType;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class RoleService {
    private final RoleRepository roleRepository;

    public Role getById(Long id) {
        return roleRepository.findById(id)
                .orElseThrow(() -> new RoleNotFoundException("Role not found by id:" + id));
    }

    public Role getByName(RoleType roleType) {
        return roleRepository.findRoleByName(roleType)
                .orElseThrow(() -> new RoleNotFoundException("Role not found by id:" + roleType.toString()));
    }
}
