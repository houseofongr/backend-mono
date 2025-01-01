package com.hoo.aar.adapter.out.persistence.entity;

import jakarta.persistence.*;
import org.springframework.data.annotation.CreatedDate;
import org.springframework.data.annotation.LastModifiedDate;
import org.springframework.data.jpa.domain.support.AuditingEntityListener;

import java.time.ZonedDateTime;

@MappedSuperclass
public class DateColumnBaseEntity {

    @Column
    @CreatedDate
    private ZonedDateTime createdDate;

    @Column
    @LastModifiedDate
    private ZonedDateTime updatedDate;

    @PrePersist
    public void prePersist() {
        this.createdDate = ZonedDateTime.now();
        this.updatedDate = ZonedDateTime.now();
    }

    @PreUpdate
    public void preUpdate() {
        this.updatedDate = ZonedDateTime.now();
    }
}
