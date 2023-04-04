package SingVersion.FitnesApp.entity;

import SingVersion.FitnesApp.core.enums.Role;
import SingVersion.FitnesApp.core.enums.Status;
import com.fasterxml.jackson.annotation.JsonIgnore;
import jakarta.persistence.*;
import org.springframework.format.annotation.DateTimeFormat;

import java.time.LocalDateTime;
import java.util.UUID;

@Entity
@Table(schema = "app", name = "user")
public class User {

    @Id
    private UUID uuid;
    private LocalDateTime dtCreate;
    @Version
    private LocalDateTime dtUpdate;
    private String email;
    private String fio;
    @JsonIgnore
    private String password;
    @Enumerated(EnumType.STRING)
    private Role role;
    @Enumerated(EnumType.STRING)
    private Status status;

    public User() {
    }

    public User(UUID uuid, String email, String fio, String password, LocalDateTime dtCreate, LocalDateTime dtUpdate, Role role, Status status) {
        this.uuid = uuid;
        this.email = email;
        this.fio = fio;
        this.password = password;
        this.dtCreate = dtCreate;
        this.dtUpdate = dtUpdate;
        this.role = role;
        this.status = status;
    }

    public UUID getUuid() {
        return uuid;
    }

    public String getEmail() {
        return email;
    }

    public String getFio() {
        return fio;
    }

    public String getPassword() {
        return password;
    }

    public LocalDateTime getDtCreate() {
        return dtCreate;
    }

    @DateTimeFormat(pattern = "yyyy-MM-dd HH:mm:ss")
    public LocalDateTime getDtUpdate() {
        return dtUpdate;
    }

    public Role getRole() {
        return role;
    }

    public Status getStatus() {
        return status;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public void setFio(String fio) {
        this.fio = fio;
    }

    public void setPassword(String password) {
        this.password = password;
    }

    public void setRole(Role role) {
        this.role = role;
    }

    public void setStatus(Status status) {
        this.status = status;
    }

    public void setDtUpdate(LocalDateTime dtUpdate) {
        this.dtUpdate = dtUpdate;
    }
}
