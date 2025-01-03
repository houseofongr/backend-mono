package com.hoo.aoo.aar.adapter.out.persistence.entity;

import jakarta.persistence.*;
import lombok.Getter;
import org.springframework.data.annotation.CreatedDate;
import org.springframework.data.annotation.LastModifiedDate;
import org.springframework.data.jpa.domain.support.AuditingEntityListener;

import java.time.ZoneId;
import java.time.ZonedDateTime;

@Getter
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
        this.createdDate = ZonedDateTime.now(ZoneId.systemDefault());
        this.updatedDate = ZonedDateTime.now(ZoneId.systemDefault());
    }

    @PreUpdate
    public void preUpdate() {
        this.updatedDate = ZonedDateTime.now(ZoneId.systemDefault());
    }
}
