package SingVersion.FitnesApp.service.api.audit;

import SingVersion.FitnesApp.core.dto.PageDto;
import SingVersion.FitnesApp.core.dto.audit.AuditDto;
import SingVersion.FitnesApp.core.dto.audit.CreateEntryDto;
import org.springframework.data.domain.Pageable;


public interface AuditService {

    void createEntry(CreateEntryDto createEntryDto);

    PageDto<AuditDto> getAuditPage(Pageable pageable);

    AuditDto getById(String id);

}
