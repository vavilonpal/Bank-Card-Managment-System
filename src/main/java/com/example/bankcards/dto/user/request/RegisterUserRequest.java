package com.example.bankcards.dto.user.request;

import com.example.bankcards.util.enums.RoleType;
import com.example.bankcards.util.validation.annotation.EmailNotOccupy;
import com.example.bankcards.util.validation.annotation.PasswordMatch;
import com.example.bankcards.util.validation.annotation.PhoneNumberNotOccupy;
import com.fasterxml.jackson.annotation.JsonProperty;
import jakarta.validation.constraints.Email;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.Pattern;
import jakarta.validation.constraints.Size;
import lombok.Builder;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
@Builder
@PasswordMatch
public class RegisterUserRequest {

    @NotBlank(message = "Full name is required")
    @Size(min = 2, max = 150, message = "Full name must be between 2 and 150 characters")
    private String fullName;

    @NotBlank(message = "Phone number is required")
    @Pattern(
            regexp = "^\\+?[0-9]{8,15}$",
            message = "Phone number must contain only digits and may start with +"
    )
    @PhoneNumberNotOccupy(message = "Phone number is occupy")
    private String phoneNumber;

    @NotBlank(message = "Email is required")
    @Email(message = "Invalid email format")
    @Size(max = 150, message = "Email must be less than 150 characters")
    @EmailNotOccupy(message = "Email is occupy")
    private String email;

    @NotBlank(message = "Password is required")
    @Size(min = 8, max = 64, message = "Password must be 8–64 characters long")
    @Pattern(
            regexp = "^(?=.*[a-z])(?=.*[A-Z])(?=.*\\d).+$",
            message = "Password must contain uppercase, lowercase letters and digits"
    )
    private String password;

    private String passwordConfirm;

    @JsonProperty(access = JsonProperty.Access.READ_ONLY)
    private boolean passwordsIsMatch = false;

    @JsonProperty(access = JsonProperty.Access.READ_ONLY)
    private final RoleType role = RoleType.USER;



}
