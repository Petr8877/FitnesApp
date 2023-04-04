package SingVersion.FitnesApp.util.converter.user;

import SingVersion.FitnesApp.core.dto.user.SaveUserDto;
import SingVersion.FitnesApp.entity.User;
import org.springframework.core.convert.converter.Converter;
import org.springframework.stereotype.Component;

import java.time.ZoneId;
import java.time.ZonedDateTime;

@Component
public class UserEntityToSaveUserDto implements Converter<User, SaveUserDto> {

    @Override
    public SaveUserDto convert(User source) {
        return new SaveUserDto(source.getUuid(),
                               ZonedDateTime.of(source.getDtCreate(), ZoneId.systemDefault()).toInstant().toEpochMilli(),
                               ZonedDateTime.of(source.getDtUpdate(), ZoneId.systemDefault()).toInstant().toEpochMilli(),
                               source.getEmail(),
                               source.getFio(),
                               source.getRole(),
                               source.getStatus());
    }

}
