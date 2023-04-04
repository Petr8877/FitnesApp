package SingVersion.FitnesApp.core.dto.user;

import jakarta.validation.constraints.Email;
import jakarta.validation.constraints.NotEmpty;

public record RegistrationDto(@NotEmpty @Email String email,
                              @NotEmpty String fio,
                              @NotEmpty String password) {

}
