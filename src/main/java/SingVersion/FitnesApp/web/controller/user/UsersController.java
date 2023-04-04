package SingVersion.FitnesApp.web.controller.user;

import SingVersion.FitnesApp.core.dto.PageDto;
import SingVersion.FitnesApp.core.dto.user.SaveUserDto;
import SingVersion.FitnesApp.core.dto.user.UserDto;
import SingVersion.FitnesApp.service.api.user.UsersService;
import org.springframework.data.domain.Pageable;
import org.springframework.data.web.PageableDefault;
import org.springframework.lang.NonNull;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;

import java.time.LocalDateTime;
import java.util.UUID;

@RestController
@RequestMapping("/users")
public class UsersController {

    private final UsersService service;

    public UsersController(UsersService service) {
        this.service = service;
    }

    @PostMapping
    public void addNewUser(@RequestBody @Validated UserDto userDTO) {
        service.createUser(userDTO);
    }

    @GetMapping
    public PageDto<SaveUserDto> getUsersPage(
            @PageableDefault(page = 0, size = 20)
            Pageable pageable){
        return service.getUsersPage(pageable);
    }

    @GetMapping(path = "/{id}")
    public SaveUserDto getUserById(@PathVariable("id") @NonNull UUID id) {
        return service.getUser(id);
    }

    @PutMapping(path = "/{id}/dt_update/{dt_update}")
    public void updateUser(@PathVariable("id") @NonNull UUID id,
                           @PathVariable("dt_update") @NonNull LocalDateTime dtUpdate,
                           @RequestBody @Validated UserDto userDTO) {
        service.updateUser(id, dtUpdate, userDTO);
    }
}
