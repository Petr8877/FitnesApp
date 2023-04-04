package SingVersion.FitnesApp.util.converter.user;

import SingVersion.FitnesApp.core.dto.user.RegistrationDto;
import SingVersion.FitnesApp.core.enums.Role;
import SingVersion.FitnesApp.core.enums.Status;
import SingVersion.FitnesApp.entity.User;
import org.springframework.core.convert.converter.Converter;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Component;

import java.time.LocalDateTime;
import java.time.temporal.ChronoUnit;
import java.util.UUID;

@Component
public class RegistrationDtoToEntity implements Converter<RegistrationDto, User> {

    private final PasswordEncoder encoder;

    public RegistrationDtoToEntity(PasswordEncoder encoder) {
        this.encoder = encoder;
    }

    @Override
    public User convert(RegistrationDto source) {
        LocalDateTime timeNow = LocalDateTime.now().truncatedTo(ChronoUnit.MILLIS);
        return new User(UUID.randomUUID(),
                        source.email(),
                        source.fio(),
                        encoder.encode(source.password()),
                        timeNow,
                        timeNow,
                        Role.USER,
                        Status.WAITING_ACTIVATION);
    }
}
