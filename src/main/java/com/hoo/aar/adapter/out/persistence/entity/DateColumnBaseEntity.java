package com.hoo.aar.adapter.out.persistence.entity;

import jakarta.persistence.Column;
import jakarta.persistence.EntityListeners;
import jakarta.persistence.MappedSuperclass;
import org.springframework.data.annotation.CreatedDate;
import org.springframework.data.annotation.LastModifiedDate;
import org.springframework.data.jpa.domain.support.AuditingEntityListener;

import java.time.ZonedDateTime;

@MappedSuperclass
@EntityListeners(AuditingEntityListener.class)
public class DateColumnBaseEntity {

    @Column(nullable = false)
    @CreatedDate
    private ZonedDateTime createdDate;

    @Column(nullable = false)
    @LastModifiedDate
    private ZonedDateTime updatedDate;
}
