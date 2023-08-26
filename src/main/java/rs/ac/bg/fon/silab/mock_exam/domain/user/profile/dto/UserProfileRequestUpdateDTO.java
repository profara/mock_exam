package rs.ac.bg.fon.silab.mock_exam.domain.user.profile.dto;

import jakarta.validation.constraints.Email;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.Size;

public record UserProfileRequestUpdateDTO(
        @NotBlank(message = "Email is mandatory")
        @Email(message = "Invalid email format")
        String email,
        @NotBlank(message = "Password is mandatory")
        @Size(min = 8,message = "Password must have at least 8 characters")
        String password
) { }
