package v._1.PokeNest.auth.dto.request;

import jakarta.validation.constraints.Email;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.Size;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class RegisterRequest {
    @NotBlank(message = "Username is required.")
    @Size(min = 4, max = 20, message = "Username must be between 4 and 20 characters.")
    private String username;

    @NotBlank(message = "Password is required.")
    @Size(min = 8, max = 120, message = "Password must be at least 8 characters long.")
    private String password;

    @NotBlank(message = "Email is required.")
    @Size(max = 50)
    @Email(message = "Invalid email address.")
    //@Pattern(regexp = "^(?=.*[A-Za-z])(?=.*\\d)[A-Za-z\\d]+$",
    //            message = "Password must contain at least one letter and one number.")
    private String email;
}
