package SingVersion.FitnesApp.service.api.user;


import SingVersion.FitnesApp.core.dto.DetailsDto;
import SingVersion.FitnesApp.core.dto.user.LoginDto;
import SingVersion.FitnesApp.core.dto.user.RegistrationDto;
import SingVersion.FitnesApp.core.dto.user.SaveUserDto;
import SingVersion.FitnesApp.entity.User;

public interface RegistrationUserService {

    SaveUserDto registrationUser(RegistrationDto userRegistrationDTO);

    boolean verification(String code, String email);

    DetailsDto login(LoginDto loginDto);

    SaveUserDto aboutMe();
}
