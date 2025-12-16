package com.example.bankcards.service;

import com.example.bankcards.entity.Role;
import com.example.bankcards.exception.entity.role.RoleNotFoundException;
import com.example.bankcards.repository.RoleRepository;
import com.example.bankcards.util.enums.RoleType;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import java.util.Optional;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.when;

@ExtendWith(MockitoExtension.class)
class RoleServiceTest {

    @Mock
    private RoleRepository roleRepository;

    @InjectMocks
    private RoleService roleService;

    @Test
    void shouldReturnRoleById() {
        Long roleId = 1L;
        Role role = new Role();
        role.setId(roleId);
        role.setName(RoleType.USER);

        when(roleRepository.findById(roleId)).thenReturn(Optional.of(role));

        Role result = roleService.getById(roleId);

        assertNotNull(result);
        assertEquals(roleId, result.getId());
        assertEquals(RoleType.USER, result.getName());
    }

    @Test
    void shouldThrowWhenRoleByIdNotFound() {
        Long roleId = 1L;
        when(roleRepository.findById(roleId)).thenReturn(Optional.empty());

        assertThrows(RoleNotFoundException.class, () -> roleService.getById(roleId));
    }


    @Test
    void shouldReturnRoleByName() {
        RoleType roleType = RoleType.ADMIN;
        Role role = new Role();
        role.setId(2L);
        role.setName(roleType);

        when(roleRepository.findRoleByName(roleType)).thenReturn(Optional.of(role));

        Role result = roleService.getByName(roleType);

        assertNotNull(result);
        assertEquals(roleType, result.getName());
        assertEquals(2L, result.getId());
    }

    @Test
    void shouldThrowWhenRoleByNameNotFound() {
        RoleType roleType = RoleType.ADMIN;
        when(roleRepository.findRoleByName(roleType)).thenReturn(Optional.empty());

        assertThrows(RoleNotFoundException.class, () -> roleService.getByName(roleType));
    }

}
