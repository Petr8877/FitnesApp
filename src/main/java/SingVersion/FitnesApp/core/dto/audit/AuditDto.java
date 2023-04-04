package SingVersion.FitnesApp.core.dto.audit;


import SingVersion.FitnesApp.core.enums.Role;
import SingVersion.FitnesApp.core.enums.TypeOfEntity;

import java.time.LocalDateTime;
import java.util.UUID;

public record AuditDto(UUID uuid,
                       LocalDateTime dtCreate,
                       String text,
                       TypeOfEntity type,
                       int idType,
                       UUID client,
                       String clientEmail,
                       String clientFio,
                       Role clientRole) {
}
