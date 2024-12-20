/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package ss1.api.models;

import com.fasterxml.jackson.annotation.JsonProperty;
import io.swagger.v3.oas.annotations.media.Schema;
import java.time.LocalDateTime;
import javax.persistence.Column;
import javax.persistence.EntityListeners;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.MappedSuperclass;
import org.springframework.data.annotation.CreatedDate;
import org.springframework.data.annotation.LastModifiedDate;
import org.springframework.data.jpa.domain.support.AuditingEntityListener;

/**
 * Clase encargada de asignar losid y las auditorias de cada clase que extienda
 * de ella.
 *
 * @author luid
 */
@MappedSuperclass
@EntityListeners(AuditingEntityListener.class)
public class Auditor {

    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    private Long id;

    @CreatedDate
    @Column(nullable = false, updatable = false)
    @JsonProperty(access = JsonProperty.Access.WRITE_ONLY)
    @Schema(hidden = true)
    private LocalDateTime createdAt;

    @LastModifiedDate
    @JsonProperty(access = JsonProperty.Access.WRITE_ONLY)
    @Schema(hidden = true)
    private LocalDateTime updatedAt;

    @JsonProperty(access = JsonProperty.Access.WRITE_ONLY)
    @Schema(hidden = true)
    private LocalDateTime desactivatedAt;

    @JsonProperty(access = JsonProperty.Access.WRITE_ONLY)
    @Schema(hidden = true)
    private LocalDateTime deletedAt;

 
    @Schema(hidden = true)
    private Boolean deleted;

    @JsonProperty(access = JsonProperty.Access.WRITE_ONLY)
    @Schema(hidden = true)
    private Boolean desactivated;

    public Auditor(Long id, LocalDateTime createdAt, LocalDateTime updatedAt, LocalDateTime desactivatedAt, LocalDateTime deletedAt, Boolean deleted, Boolean desactivated) {
        this.id = id;
        this.createdAt = createdAt;
        this.updatedAt = updatedAt;
        this.desactivatedAt = desactivatedAt;
        this.deletedAt = deletedAt;
        this.deleted = deleted;
        this.desactivated = desactivated;
    }

    public Auditor(Long id) {
        this.id = id;
    }

    public Auditor() {
    }

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public LocalDateTime getCreatedAt() {
        return createdAt;
    }

    public void setCreatedAt(LocalDateTime createdAt) {
        this.createdAt = createdAt;
    }

    public LocalDateTime getUpdatedAt() {
        return updatedAt;
    }

    public void setUpdatedAt(LocalDateTime updatedAt) {
        this.updatedAt = updatedAt;
    }

    public LocalDateTime getDesactivatedAt() {
        return desactivatedAt;
    }

    public void setDesactivatedAt(LocalDateTime desactivatedAt) {
        this.desactivatedAt = desactivatedAt;
    }

    public LocalDateTime getDeletedAt() {
        return deletedAt;
    }

    public void setDeletedAt(LocalDateTime deletedAt) {
        this.deletedAt = deletedAt;
    }

    public Boolean getDeleted() {
        return deleted;
    }

    public void setDeleted(Boolean deleted) {
        this.deleted = deleted;
    }

    public Boolean getDesactivated() {
        return desactivated;
    }

    public void setDesactivated(Boolean desactivated) {
        this.desactivated = desactivated;
    }

}
