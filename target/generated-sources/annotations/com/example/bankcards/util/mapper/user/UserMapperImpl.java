package com.example.bankcards.util.mapper.user;

import com.example.bankcards.dto.user.request.UserPersistRequest;
import com.example.bankcards.dto.user.response.OverallUserResponse;
import com.example.bankcards.dto.user.response.UserResponse;
import com.example.bankcards.entity.User;
import com.example.bankcards.util.enums.RoleType;
import javax.annotation.processing.Generated;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

@Generated(
    value = "org.mapstruct.ap.MappingProcessor",
    date = "2025-12-16T20:22:19+0200",
    comments = "version: 1.6.0.Beta1, compiler: javac, environment: Java 21 (Oracle Corporation)"
)
@Component
public class UserMapperImpl implements UserMapper {

    @Autowired
    private RoleMapper roleMapper;
    @Autowired
    private PasswordMapper passwordMapper;

    @Override
    public User toEntity(UserPersistRequest userRequest) {
        if ( userRequest == null ) {
            return null;
        }

        User.UserBuilder user = User.builder();

        user.passwordHash( passwordMapper.encode( userRequest.getPassword() ) );
        user.role( roleMapper.map( userRequest.getRole() ) );
        user.email( userRequest.getEmail() );
        user.fullName( userRequest.getFullName() );
        user.phoneNumber( userRequest.getPhoneNumber() );

        user.isActive( true );

        return user.build();
    }

    @Override
    public UserResponse toResponse(User user) {
        if ( user == null ) {
            return null;
        }

        UserResponse.UserResponseBuilder userResponse = UserResponse.builder();

        userResponse.id( user.getId() );
        userResponse.email( user.getEmail() );
        userResponse.fullName( user.getFullName() );
        userResponse.phoneNumber( user.getPhoneNumber() );
        userResponse.isActive( user.getIsActive() );
        userResponse.createdAt( user.getCreatedAt() );

        return userResponse.build();
    }

    @Override
    public OverallUserResponse toOverallResponse(User user) {
        if ( user == null ) {
            return null;
        }

        OverallUserResponse.OverallUserResponseBuilder overallUserResponse = OverallUserResponse.builder();

        overallUserResponse.role( roleMapper.mapToEnum( user.getRole() ) );
        overallUserResponse.id( user.getId() );
        overallUserResponse.email( user.getEmail() );
        overallUserResponse.passwordHash( user.getPasswordHash() );
        overallUserResponse.fullName( user.getFullName() );
        overallUserResponse.phoneNumber( user.getPhoneNumber() );
        overallUserResponse.isActive( user.getIsActive() );
        overallUserResponse.createdAt( user.getCreatedAt() );
        overallUserResponse.updatedAt( user.getUpdatedAt() );

        return overallUserResponse.build();
    }
}
