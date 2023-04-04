package SingVersion.FitnesApp.service.impl.user;


import SingVersion.FitnesApp.core.dto.PageDto;
import SingVersion.FitnesApp.core.dto.audit.CreateEntryDto;
import SingVersion.FitnesApp.core.dto.DetailsDto;
import SingVersion.FitnesApp.core.dto.user.SaveUserDto;
import SingVersion.FitnesApp.core.dto.user.UserDto;
import SingVersion.FitnesApp.core.exception.SingleErrorResponse;
import SingVersion.FitnesApp.entity.User;
import SingVersion.FitnesApp.repository.UserRepository;
import SingVersion.FitnesApp.service.api.audit.AuditService;
import SingVersion.FitnesApp.service.api.user.UsersService;
import org.springframework.core.convert.ConversionService;
import org.springframework.data.annotation.ReadOnlyProperty;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.validation.annotation.Validated;

import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;
import java.util.Objects;
import java.util.UUID;

@Service
public class UsersServiceImpl implements UsersService {

    private final UserRepository userRepository;
    private final PasswordEncoder encoder;
    private final ConversionService conversionService;
    private final AuditService auditService;

    public UsersServiceImpl(UserRepository userRepository, PasswordEncoder encoder, ConversionService conversionService, AuditService auditService) {
        this.userRepository = userRepository;
        this.encoder = encoder;
        this.conversionService = conversionService;
        this.auditService = auditService;
    }

    @Override
    @Transactional
    public User createUser(@Validated UserDto userDTO) {
        User user = conversionService.convert(userDTO, User.class);
        userRepository.save(Objects.requireNonNull(user));
        toAudit("Создание новогопользователя uuid: " + user.getUuid());
        return user;
    }

    @Override
    @ReadOnlyProperty
    public SaveUserDto getUser(UUID id) {
        SaveUserDto saveUserDto = userRepository.existsById(id) ?
                conversionService.convert(userRepository.findById(id).get(), SaveUserDto.class) : null;
        if (saveUserDto != null) {
            toAudit("Просмотр информации о пользователе email: " + saveUserDto.email());
            return saveUserDto;
        } else {
            throw new SingleErrorResponse("Нет пользавателя с таким id");
        }
    }

    @Override
    @Transactional
    public User updateUser(UUID id, LocalDateTime dtUpdate, UserDto userDTO) {
        User entity = userRepository.findById(id).orElseThrow(() ->
                new SingleErrorResponse("Нет пользователя для обновления с таким id"));
        if (Objects.equals(entity.getDtUpdate(), dtUpdate)) {
            entity.setEmail(userDTO.email());
            entity.setFio(userDTO.fio());
            entity.setRole(userDTO.role());
            entity.setStatus(userDTO.status());
            entity.setPassword(encoder.encode(userDTO.password()));
            toAudit("Корректировка информации о пользователе uuid: " + entity.getUuid());
            return userRepository.save(entity);
        } else {
            throw new SingleErrorResponse("Введена не верная версия");
        }
    }

    @Override
    @ReadOnlyProperty
    public PageDto<SaveUserDto> getUsersPage(Pageable pageable) {
        Page<User> allPage = userRepository.findAllPage(pageable);
        List<SaveUserDto> content = new ArrayList<>();
        for (User user : allPage) {
            content.add(conversionService.convert(user, SaveUserDto.class));
        }
        toAudit("Просмотр информации о свех пользователях");
        return new PageDto<>(allPage.getNumber(), allPage.getSize(), allPage.getTotalPages(),
                allPage.getTotalElements(), allPage.isFirst(), allPage.getNumberOfElements(),
                allPage.isLast(), content);
    }

    private void toAudit(String text) {
        DetailsDto principal = (DetailsDto) SecurityContextHolder.getContext().getAuthentication().getPrincipal();
        auditService.createEntry(new CreateEntryDto(principal.getUuid(), principal.getUsername(), principal.getFio(),
                principal.getRole(), text, 3));
    }
}
