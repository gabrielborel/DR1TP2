package infnet.edu.br.DR1TP2.dtos;

import jakarta.validation.constraints.Email;
import jakarta.validation.constraints.NotBlank;

public record CreateUserDTO (
    @NotBlank(message = "Username is required") String username,
    @NotBlank(message = "Email is required") @Email(message = "Invalid email format") String email
) {}
