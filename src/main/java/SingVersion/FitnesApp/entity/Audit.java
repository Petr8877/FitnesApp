package SingVersion.FitnesApp.entity;

import SingVersion.FitnesApp.core.enums.Role;
import SingVersion.FitnesApp.core.enums.TypeOfEntity;
import jakarta.persistence.*;

import java.time.LocalDateTime;
import java.util.UUID;

@Entity
@Table(schema = "app", name = "audit")
@Inheritance(strategy = InheritanceType.JOINED)
public class Audit {

    @Id
    private UUID uuid;
    private LocalDateTime dtCreate;
    private String text;
    @Enumerated(value = EnumType.STRING)
    private TypeOfEntity type;
    private int idType;
    private UUID client;
    private String clientEmail;
    private String clientFio;
    @Enumerated(value = EnumType.STRING)
    private Role clientRole;

    public Audit(UUID uuid, LocalDateTime dtCreate, String text, TypeOfEntity type, int idType, UUID client, String clientEmail, String clientFio, Role clientRole) {
        this.uuid = uuid;
        this.dtCreate = dtCreate;
        this.text = text;
        this.type = type;
        this.idType = idType;
        this.client = client;
        this.clientEmail = clientEmail;
        this.clientFio = clientFio;
        this.clientRole = clientRole;
    }

    public Audit() {
    }

    public UUID getUuid() {
        return uuid;
    }

    public void setUuid(UUID uuid) {
        this.uuid = uuid;
    }

    public LocalDateTime getDtCreate() {
        return dtCreate;
    }

    public void setDtCreate(LocalDateTime dtCreate) {
        this.dtCreate = dtCreate;
    }

    public String getText() {
        return text;
    }

    public void setText(String text) {
        this.text = text;
    }

    public TypeOfEntity getType() {
        return type;
    }

    public void setType(TypeOfEntity type) {
        this.type = type;
    }

    public int getIdType() {
        return idType;
    }

    public void setIdType(int idType) {
        this.idType = idType;
    }

    public UUID getClient() {
        return client;
    }

    public void setClient(UUID client) {
        this.client = client;
    }

    public String getClientEmail() {
        return clientEmail;
    }

    public void setClientEmail(String clientEmail) {
        this.clientEmail = clientEmail;
    }

    public String getClientFio() {
        return clientFio;
    }

    public void setClientFio(String clientFio) {
        this.clientFio = clientFio;
    }

    public Role getClientRole() {
        return clientRole;
    }

    public void setClientRole(Role clientRole) {
        this.clientRole = clientRole;
    }
}
