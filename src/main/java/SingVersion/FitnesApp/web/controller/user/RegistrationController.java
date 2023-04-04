package SingVersion.FitnesApp.web.controller.user;

import SingVersion.FitnesApp.core.dto.DetailsDto;
import SingVersion.FitnesApp.core.dto.user.LoginDto;
import SingVersion.FitnesApp.core.dto.user.RegistrationDto;
import SingVersion.FitnesApp.core.dto.user.SaveUserDto;
import SingVersion.FitnesApp.service.api.user.RegistrationUserService;
import SingVersion.FitnesApp.service.impl.JwtService;
import jakarta.validation.constraints.Email;
import jakarta.validation.constraints.NotEmpty;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;

import java.util.Map;

@RestController
@RequestMapping("/users")
public class RegistrationController {

    private final RegistrationUserService service;
    private final JwtService jwtService;

    public RegistrationController(RegistrationUserService service, JwtService jwtService) {
        this.service = service;
        this.jwtService = jwtService;
    }

    @PostMapping(path = "/registration")
    public void registration(@RequestBody @Validated RegistrationDto userRegistrationDTO) {
        service.registrationUser(userRegistrationDTO);
    }

    @GetMapping(path = "/verification")
    public void verification(@RequestParam("code") @NotEmpty String code,
                             @RequestParam("email") @NotEmpty @Email String email) {
        service.verification(code, email);
    }

    @PostMapping(path = "/login")
    public ResponseEntity<String> login(@RequestBody @Validated LoginDto loginDTO) {
        DetailsDto login = service.login(loginDTO);
        Map<String, Object> claim = jwtService.getClaim(login);
        return ResponseEntity.ok(jwtService.generationToken(claim, login));
    }

    @GetMapping(path = "/me")
    public ResponseEntity<SaveUserDto> aboutMe() {
        return ResponseEntity.ok(service.aboutMe());
    }
}
