package SingVersion.FitnesApp.core.dto.user;


import SingVersion.FitnesApp.core.enums.Role;
import SingVersion.FitnesApp.core.enums.Status;
import jakarta.validation.constraints.Email;
import jakarta.validation.constraints.NotBlank;
import org.springframework.lang.NonNull;

public record UserDto(@NotBlank @Email String email,
                      @NotBlank String fio,
                      @NonNull Role role,
                      @NonNull Status status,
                      @NotBlank String password) {

}
