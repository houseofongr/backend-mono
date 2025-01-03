package com.hoo.aoo.aar.adapter.out.persistence.entity;

import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import org.hibernate.annotations.ColumnDefault;

import java.util.List;

@Entity
@Table(name = "AAR_USER")
@Getter
@NoArgsConstructor
@AllArgsConstructor
public class UserJpaEntity extends DateColumnBaseEntity {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(nullable = false, length = 255)
    private String name;

    @Column(nullable = true, length = 255)
    private String nickname;

    @Column(nullable = false, length = 20)
    private String phoneNumber;

    @Column(nullable = false)
    @ColumnDefault("0")
    private Boolean recordAgreement;

    @Column(nullable = false)
    @ColumnDefault("0")
    private Boolean personalInformationAgreement;

    @OneToMany(fetch = FetchType.LAZY, mappedBy = "userEntity")
    private List<SnsAccountJpaEntity> snsAccountEntities;
}
