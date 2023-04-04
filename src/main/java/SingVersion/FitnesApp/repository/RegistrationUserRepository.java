package SingVersion.FitnesApp.repository;

import SingVersion.FitnesApp.entity.User;
import org.springframework.data.repository.Repository;

import java.util.Optional;
import java.util.UUID;

public interface RegistrationUserRepository extends Repository<User, UUID> {

    boolean existsByEmail(String email);

    Optional<User> findByEmail(String username);

    User save(User user);

    Optional<User> findById(UUID fromString);
}
