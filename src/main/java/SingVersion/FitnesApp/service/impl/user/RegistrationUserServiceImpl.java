package SingVersion.FitnesApp.service.impl.user;

import SingVersion.FitnesApp.core.dto.audit.CreateEntryDto;
import SingVersion.FitnesApp.core.dto.DetailsDto;
import SingVersion.FitnesApp.core.dto.user.LoginDto;
import SingVersion.FitnesApp.core.dto.user.RegistrationDto;
import SingVersion.FitnesApp.core.dto.user.SaveUserDto;
import SingVersion.FitnesApp.core.enums.Role;
import SingVersion.FitnesApp.core.enums.Status;
import SingVersion.FitnesApp.core.exception.SingleErrorResponse;
import SingVersion.FitnesApp.entity.User;
import SingVersion.FitnesApp.repository.RegistrationUserRepository;
import SingVersion.FitnesApp.service.api.audit.AuditService;
import SingVersion.FitnesApp.service.api.user.RegistrationUserService;
import SingVersion.FitnesApp.service.impl.audit.AuditServiceImpl;
import org.springframework.core.convert.ConversionService;
import org.springframework.data.annotation.ReadOnlyProperty;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.Objects;
import java.util.UUID;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;


@Service
public class RegistrationUserServiceImpl implements RegistrationUserService {

    private final AuditService auditService;
    private final EmailServiceImpl emailService;
    private final PasswordEncoder encoder;
    private final ConversionService conversionService;
    private final RegistrationUserRepository repository;

    private final ExecutorService executorService = Executors.newFixedThreadPool(7);


    public RegistrationUserServiceImpl(AuditServiceImpl auditService, EmailServiceImpl emailService,
                                       PasswordEncoder encoder, ConversionService conversionService,
                                       RegistrationUserRepository repo) {
        this.auditService = auditService;
        this.emailService = emailService;
        this.encoder = encoder;
        this.conversionService = conversionService;
        this.repository = repo;
    }

    @Override
    @Transactional
    public User registrationUser(RegistrationDto registrationDTO) {
        if (!repository.existsByEmail(registrationDTO.email())) {
            User user = conversionService.convert(registrationDTO, User.class);
            User save = repository.save(user);
            toAudit("Регистрация нового пользователя", save.getUuid(), save.getEmail(), save.getFio(), save.getRole());
            executorService.submit(new Thread(() ->
                    emailService.sendSimpleMessage(save.getEmail(), createLink(save.getEmail(), save.getUuid()))));
//            emailService.sendSimpleMessage(save.getEmail(), createLink(save.getEmail(), save.getUuid()));
            return save;
        } else {
            throw new SingleErrorResponse("Данная почта уже была использована для регистрации");
        }

    }

    @Override
    @Transactional
    public boolean verification(String code, String email) {
        UUID uuid = UUID.fromString(code);
        User user = repository.findById(uuid).orElseThrow(()
                -> new SingleErrorResponse("Такого пользователя нет в базе"));
        if (Objects.equals(user.getEmail(), email)) {
            toAudit("Верификация пользователя", user.getUuid(), email, user.getFio(), user.getRole());
            switch (user.getStatus()) {
                case WAITING_ACTIVATION -> {
                    user.setStatus(Status.ACTIVATED);
                    repository.save(user);
                    return true;
                }
                case DEACTIVATED -> throw new SingleErrorResponse("Пользователь деактивирован");
                case ACTIVATED -> throw new SingleErrorResponse("Данный пользователь уже был активирован");
            }
        } else {
            throw new SingleErrorResponse("Указан не корректный адрес электронной почты");
        }
        return false;
    }

    @Override
    @ReadOnlyProperty
    public DetailsDto login(LoginDto loginDto) {
        User userByEmail = repository.findByEmail(loginDto.email()).orElseThrow(()
                -> new UsernameNotFoundException("User nit found"));
        DetailsDto user = conversionService.convert(userByEmail, DetailsDto.class);
        if (!encoder.matches(loginDto.password(), Objects.requireNonNull(user).getPassword())) {
            throw new IllegalArgumentException("Пароль неверный");
        }
        toAudit("Вход в систему", userByEmail.getUuid(), user.getUsername(), userByEmail.getFio(), userByEmail.getRole());
        return user;
    }

    @Override
    @ReadOnlyProperty
    public SaveUserDto aboutMe() {
        User user = repository.findByEmail(SecurityContextHolder.getContext().getAuthentication().getName())
                .orElseThrow(() -> new UsernameNotFoundException("User nit found"));
        toAudit("Получение информации о себе");
        return conversionService.convert(user, SaveUserDto.class);
    }

    private String createLink(String email, UUID uuid) {
        return "http://localhost:8080/users/verification?code="
                + uuid + "&email=" + email;
    }

    private void toAudit(String text, UUID uuid, String email, String fio, Role role) {
        auditService.createEntry(new CreateEntryDto(uuid, email, fio, role, text, 3));
    }

    private void toAudit(String text) {
        DetailsDto principal = (DetailsDto) SecurityContextHolder.getContext().getAuthentication().getPrincipal();
        auditService.createEntry(new CreateEntryDto(principal.getUuid(), principal.getUsername(), principal.getFio(),
                principal.getRole(), text, 3));
    }
}
