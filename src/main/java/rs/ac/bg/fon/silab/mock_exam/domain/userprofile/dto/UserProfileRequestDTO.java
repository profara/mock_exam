package rs.ac.bg.fon.silab.mock_exam.domain.userprofile.dto;

import jakarta.validation.Valid;
import jakarta.validation.constraints.Email;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Size;
import rs.ac.bg.fon.silab.mock_exam.domain.userrole.dto.UserRoleRequestDTO;

public record UserProfileRequestDTO(
    @NotBlank(message = "Email is mandatory")
    @Email(message = "Invalid email format")
    String email,
    @NotBlank(message = "Password is mandatory")
    @Size(min = 8,message = "Password must have at least 8 characters")
    String password,
    @NotNull(message = "User role is mandatory")
    @Valid
    UserRoleRequestDTO userRole
) {
}
