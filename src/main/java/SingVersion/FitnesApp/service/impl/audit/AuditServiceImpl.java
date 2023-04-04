package SingVersion.FitnesApp.service.impl.audit;

import SingVersion.FitnesApp.core.dto.PageDto;
import SingVersion.FitnesApp.core.dto.audit.AuditDto;
import SingVersion.FitnesApp.core.dto.audit.ConvertDto;
import SingVersion.FitnesApp.core.dto.audit.CreateEntryDto;
import SingVersion.FitnesApp.core.dto.DetailsDto;
import SingVersion.FitnesApp.core.exception.SingleErrorResponse;
import SingVersion.FitnesApp.entity.Audit;
import SingVersion.FitnesApp.repository.AuditRepository;
import SingVersion.FitnesApp.service.api.audit.AuditService;
import org.springframework.core.convert.ConversionService;
import org.springframework.data.annotation.ReadOnlyProperty;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.ArrayList;
import java.util.List;
import java.util.Objects;
import java.util.UUID;

@Service
public class AuditServiceImpl implements AuditService {

    private final AuditRepository repository;
    private final ConversionService conversionService;

    public AuditServiceImpl(AuditRepository repository, ConversionService conversionService) {
        this.repository = repository;
        this.conversionService = conversionService;
    }

    @Override
    @Transactional
    public void createEntry(CreateEntryDto createEntryDto) {
        repository.save(Objects.requireNonNull(conversionService.convert(createEntryDto, Audit.class)));
    }

    @Override
    @ReadOnlyProperty
    public PageDto<AuditDto> getAuditPage(Pageable pageable) {
        Page<Audit> allPage = repository.findAllPage(pageable);
        List<AuditDto> content = new ArrayList<>();
        for (Audit audit : allPage) {
            content.add(conversionService.convert(audit, AuditDto.class));
        }
        toAudit("Просмотр всех данных аудита");
        return conversionService.convert(new ConvertDto(allPage, content), PageDto.class);
    }

    @Override
    @ReadOnlyProperty
    public AuditDto getById(String id) {
        Audit audit = repository.findById(UUID.fromString(id)).orElseThrow(()
                -> new SingleErrorResponse("Audit not found"));
        toAudit("Просмотр сведений аудита по id: " + id);
        return conversionService.convert(audit, AuditDto.class);
    }

    private void toAudit(String text) {
        DetailsDto principal = (DetailsDto) SecurityContextHolder.getContext().getAuthentication().getPrincipal();
        createEntry(new CreateEntryDto(principal.getUuid(), principal.getUsername(), principal.getFio(),
                principal.getRole(), text, 4));
    }
}
