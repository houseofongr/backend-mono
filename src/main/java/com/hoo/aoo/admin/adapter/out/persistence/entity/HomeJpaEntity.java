package com.hoo.aoo.admin.adapter.out.persistence.entity;

import com.hoo.aoo.aar.adapter.out.persistence.entity.UserJpaEntity;
import com.hoo.aoo.common.adapter.out.persistence.entity.DateColumnBaseEntity;
import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;

@Entity
@Table(name = "HOME")
@Getter
@NoArgsConstructor
@AllArgsConstructor
public class HomeJpaEntity extends DateColumnBaseEntity {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "HOUSE_ID", nullable = false)
    private HouseJpaEntity house;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "USER_ID", nullable = false)
    private UserJpaEntity user;

    public static HomeJpaEntity create(HouseJpaEntity house, UserJpaEntity user) {
        return new HomeJpaEntity(null,house,user);
    }
}
