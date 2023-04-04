package SingVersion.FitnesApp.web.controller.audit;

import SingVersion.FitnesApp.core.dto.PageDto;
import SingVersion.FitnesApp.core.dto.audit.AuditDto;
import SingVersion.FitnesApp.service.api.audit.AuditService;
import org.springframework.data.domain.Pageable;
import org.springframework.data.web.PageableDefault;
import org.springframework.web.bind.annotation.*;


@RestController
@RequestMapping("/audit")
public class AuditController {

    private final AuditService service;

    public AuditController(AuditService service) {
        this.service = service;
    }


    @GetMapping
    public PageDto<AuditDto> getPage(
            @PageableDefault(page = 0, size = 20)
            Pageable pageable) {
        return service.getAuditPage(pageable);
    }

    @GetMapping("/{id}")
    public AuditDto getById(@PathVariable("id") String id) {
        return service.getById(id);
    }
}
