package SingVersion.FitnesApp.core.dto.audit;

import SingVersion.FitnesApp.entity.Audit;
import org.springframework.data.domain.Page;

import java.util.List;

public record ConvertDto(Page<Audit> allPage,
                         List<AuditDto> content) {
}
