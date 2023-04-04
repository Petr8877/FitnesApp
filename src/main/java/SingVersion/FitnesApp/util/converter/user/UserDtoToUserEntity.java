package SingVersion.FitnesApp.util.converter.user;

import SingVersion.FitnesApp.core.dto.user.UserDto;
import SingVersion.FitnesApp.entity.User;
import org.springframework.core.convert.converter.Converter;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Component;

import java.time.LocalDateTime;
import java.time.temporal.ChronoUnit;
import java.util.UUID;

@Component
public class UserDtoToUserEntity implements Converter<UserDto, User> {

    private final PasswordEncoder encoder;

    public UserDtoToUserEntity(PasswordEncoder encoder) {
        this.encoder = encoder;
    }

    @Override
    public User convert(UserDto source) {
        LocalDateTime timeNow = LocalDateTime.now().truncatedTo(ChronoUnit.MILLIS);
        return new User(UUID.randomUUID(),
                        source.email(),
                        source.fio(),
                        encoder.encode(source.password()),
                        timeNow,
                        timeNow,
                        source.role(),
                        source.status());
    }

}
