package SingVersion.FitnesApp.core.dto.audit;

import SingVersion.FitnesApp.core.enums.Role;
import jakarta.validation.constraints.Email;
import jakarta.validation.constraints.NotEmpty;
import org.springframework.lang.NonNull;

import java.util.UUID;

public record CreateEntryDto(@NonNull UUID uuid,
                             @NotEmpty @Email String email,
                             @NotEmpty String fio,
                             @NonNull Role role,
                             @NotEmpty String text,
                             @NotEmpty int id) {
}
