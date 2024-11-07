package org.example.service_auth.models;

import jakarta.persistence.*;
import lombok.Getter;
import lombok.RequiredArgsConstructor;
import lombok.Setter;
import org.hibernate.annotations.CreationTimestamp;
import org.hibernate.annotations.UpdateTimestamp;
import org.springframework.data.annotation.CreatedBy;
import org.springframework.data.annotation.LastModifiedBy;

import java.io.Serializable;
import java.time.LocalDateTime;

@Getter
@Setter
@MappedSuperclass
@RequiredArgsConstructor
public class AuditEntity implements Serializable {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    protected Long id;

    @CreationTimestamp
    protected LocalDateTime createdAt;

    @CreatedBy
    protected String createdBy;

    @UpdateTimestamp
    protected LocalDateTime updatedAt;

    @LastModifiedBy
    protected String updatedBy;

    protected LocalDateTime deletedAt;

    protected String deletedBy;

}
