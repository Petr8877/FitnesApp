package SingVersion.FitnesApp.util.converter.audit;

import SingVersion.FitnesApp.core.dto.audit.CreateEntryDto;
import SingVersion.FitnesApp.core.enums.TypeOfEntity;
import SingVersion.FitnesApp.core.exception.SingleErrorResponse;
import SingVersion.FitnesApp.entity.Audit;
import org.springframework.core.convert.converter.Converter;
import org.springframework.stereotype.Component;

import java.time.LocalDateTime;
import java.util.UUID;

@Component
public class CreateEntryDtoToAudit implements Converter<CreateEntryDto, Audit> {
    @Override
    public Audit convert(CreateEntryDto source) {
        UUID uuid = UUID.randomUUID();
        LocalDateTime time = LocalDateTime.now();
        TypeOfEntity typeOfEntity = getTypeOfEntityById(source.id());

        return new Audit(uuid,
                         time,
                         source.text(),
                         typeOfEntity,
                         source.id(),
                         source.uuid(),
                         source.email(),
                         source.fio(),
                         source.role());
    }

    private TypeOfEntity getTypeOfEntityById(int id) {
        switch (id) {
            case 1 -> {
                return TypeOfEntity.PRODUCT;
            }
            case 2 -> {
                return TypeOfEntity.RECIPE;
            }
            case 3 -> {
                return TypeOfEntity.USER;
            }
            case 4 -> {
                return TypeOfEntity.REPORT;
            }
        }
        throw new SingleErrorResponse("""
                Check id:
                1 - PRODUCT
                2 - RECIPE
                3 - USER
                4 - REPORT
                """);
    }
}
