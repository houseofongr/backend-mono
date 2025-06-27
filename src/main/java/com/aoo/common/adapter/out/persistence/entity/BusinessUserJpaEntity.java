package com.aoo.common.adapter.out.persistence.entity;

import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;

import java.time.ZonedDateTime;

@Entity
@Table(name = "BUSINESS_USER")
@Getter
@NoArgsConstructor
@AllArgsConstructor
public class BusinessUserJpaEntity extends DateColumnBaseEntity {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(nullable = false)
    private String email;

    @Column(nullable = false)
    private String password;

    @Column(nullable = false)
    private String nickname;

    @Column(nullable = false)
    @Enumerated(EnumType.STRING)
    private Status status;

    public static BusinessUserJpaEntity create(String email, String encryptedPassword, String nickname) {
        return new BusinessUserJpaEntity(null, email, encryptedPassword, nickname, Status.WAITING, null);
    }

    public enum Status {
        WAITING, APPROVED, REJECTED
    }

    @Column
    private ZonedDateTime approvedAt;

}
