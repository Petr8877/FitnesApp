package SingVersion.FitnesApp.core.dto.user;

import SingVersion.FitnesApp.core.enums.Role;
import SingVersion.FitnesApp.core.enums.Status;
import com.fasterxml.jackson.annotation.JsonProperty;
import jakarta.validation.constraints.Email;
import jakarta.validation.constraints.NotEmpty;
import org.springframework.lang.NonNull;

import java.util.UUID;

public record SaveUserDto(@NonNull UUID uuid,
                          @NotEmpty @JsonProperty("dt_create") long dtCreate,
                          @NotEmpty @JsonProperty("dt_update") long dtUpdate,
                          @NotEmpty @Email String email,
                          @NotEmpty String fio,
                          @NonNull Role role,
                          @NonNull Status status){

}
