package com.example.bankcards.util.mapper;

import com.example.bankcards.dto.user.request.RegisterUserRequest;
import com.example.bankcards.entity.User;
import com.example.bankcards.util.enums.RoleType;
import javax.annotation.processing.Generated;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

@Generated(
    value = "org.mapstruct.ap.MappingProcessor",
    date = "2025-12-14T01:38:41+0200",
    comments = "version: 1.6.0.Beta1, compiler: javac, environment: Java 21 (Oracle Corporation)"
)
@Component
public class UserMapperImpl implements UserMapper {

    @Autowired
    private PasswordMapper passwordMapper;

    @Override
    public User toEntity(RegisterUserRequest userRequest) {
        if ( userRequest == null ) {
            return null;
        }

        User.UserBuilder user = User.builder();

        user.passwordHash( passwordMapper.encode( userRequest.getPassword() ) );
        user.email( userRequest.getEmail() );
        user.fullName( userRequest.getFullName() );
        user.phoneNumber( userRequest.getPhoneNumber() );

        user.role( RoleType.USER );
        user.isActive( true );

        return user.build();
    }
}
