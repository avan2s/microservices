package com.eazybytes.accounts.entity;

import jakarta.persistence.Column;
import jakarta.persistence.MappedSuperclass;
import lombok.*;
import lombok.experimental.SuperBuilder;

import java.time.LocalDateTime;

@MappedSuperclass
@Getter
@Setter
@ToString
@NoArgsConstructor
@SuperBuilder
public class BaseEntity {
    @Column(name = "created_by", updatable = false)
    private String createdBy;

    @Column(name = "created_at", updatable = false)
    private LocalDateTime createdAt;

    @Column(name = "updated_at", insertable = false)
    private LocalDateTime updatedAt;

    @Column(name = "updated_by", insertable = false)
    private String updatedBy;
}


