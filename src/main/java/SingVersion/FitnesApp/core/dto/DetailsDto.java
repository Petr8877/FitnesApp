package SingVersion.FitnesApp.core.dto;

import SingVersion.FitnesApp.core.enums.Role;
import SingVersion.FitnesApp.core.enums.Status;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.userdetails.UserDetails;

import java.time.LocalDateTime;
import java.util.Collection;
import java.util.List;
import java.util.UUID;

public class DetailsDto implements UserDetails {

    private final Role role;
    private final String password;
    private final String username;
    private final LocalDateTime dtCreate;
    private final LocalDateTime dtUpdate;
    private final String fio;
    private final UUID uuid;
    private final Status status;

    public DetailsDto(Role role, String password, String username, LocalDateTime dtCreate, LocalDateTime dtUpdate, String fio, UUID uuid, Status status) {
        this.role = role;
        this.password = password;
        this.username = username;
        this.dtCreate = dtCreate;
        this.dtUpdate = dtUpdate;
        this.fio = fio;
        this.uuid = uuid;
        this.status = status;
    }

    @Override
    public Collection<? extends GrantedAuthority> getAuthorities() {
        return List.of(new SimpleGrantedAuthority((role.name())));
    }

    @Override
    public String getPassword() {
        return password;
    }

    @Override
    public String getUsername() {
        return username;
    }

    @Override
    public boolean isAccountNonExpired() {
        return true;
    }

    @Override
    public boolean isAccountNonLocked() {
        return true;
    }

    @Override
    public boolean isCredentialsNonExpired() {
        return true;
    }

    @Override
    public boolean isEnabled() {
        return true;
    }

    public Role role() {
        return role;
    }

    public LocalDateTime getDtCreate() {
        return dtCreate;
    }

    public LocalDateTime getDtUpdate() {
        return dtUpdate;
    }

    public String getFio() {
        return fio;
    }

    public Status getStatus() {
        return status;
    }

    public UUID getUuid() {
        return uuid;
    }

    public Role getRole() {
        return role;
    }
}
