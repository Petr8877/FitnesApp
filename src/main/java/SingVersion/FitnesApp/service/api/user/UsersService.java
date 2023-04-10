package SingVersion.FitnesApp.service.api.user;

import SingVersion.FitnesApp.core.dto.PageDto;
import SingVersion.FitnesApp.core.dto.user.SaveUserDto;
import SingVersion.FitnesApp.core.dto.user.UserDto;
import SingVersion.FitnesApp.entity.User;
import org.springframework.data.domain.Pageable;

import java.time.LocalDateTime;
import java.util.UUID;

public interface UsersService {

    SaveUserDto createUser(UserDto userDTO);

    SaveUserDto getUser(UUID id);

    SaveUserDto updateUser(UUID id, LocalDateTime dtUpdate, UserDto userDTO);

    PageDto<SaveUserDto> getUsersPage(Pageable pageable);
}
