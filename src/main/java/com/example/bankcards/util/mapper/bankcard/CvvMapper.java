package com.example.bankcards.util.mapper.bankcard;


import lombok.RequiredArgsConstructor;
import org.mapstruct.Named;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Component;

@Component
@RequiredArgsConstructor
public class CvvMapper {
    private final PasswordEncoder passwordEncoder;

    @Named("encodeCvv")
    public  String  encode(String cvv){
        return passwordEncoder.encode(cvv);
    }
}
