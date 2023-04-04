package SingVersion.FitnesApp.util.converter.audit;

import SingVersion.FitnesApp.core.dto.audit.AuditDto;
import SingVersion.FitnesApp.entity.Audit;
import org.springframework.core.convert.converter.Converter;
import org.springframework.stereotype.Component;

@Component
public class AuditToAuditDto implements Converter<Audit, AuditDto> {

    @Override
    public AuditDto convert(Audit source) {
        return new AuditDto(source.getUuid(),
                            source.getDtCreate(),
                            source.getText(),
                            source.getType(),
                            source.getIdType(),
                            source.getClient(),
                            source.getClientEmail(),
                            source.getClientFio(),
                            source.getClientRole());
    }
}
