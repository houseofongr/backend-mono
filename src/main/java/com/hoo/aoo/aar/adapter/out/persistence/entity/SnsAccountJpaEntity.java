package com.hoo.aoo.aar.adapter.out.persistence.entity;

import com.hoo.aoo.aar.domain.account.SnsDomain;
import jakarta.persistence.*;
import lombok.*;

@Entity
@Table(name = "SNS_ACCOUNT")
@Getter
@NoArgsConstructor
@AllArgsConstructor
public class SnsAccountJpaEntity extends DateColumnBaseEntity {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(nullable = false, length = 255)
    private String realName;

    @Column(nullable = false, length = 255)
    private String nickname;

    @Column(nullable = false, length = 100)
    private String email;

    @Column(nullable = false, length = 255)
    private String snsId;

    @Column(nullable = false)
    @Enumerated(EnumType.STRING)
    private SnsDomain snsDomain;

    @ManyToOne
    @JoinColumn(name = "USER_ID", nullable = true)
    @Setter
    private UserJpaEntity userEntity;

    public static SnsAccountJpaEntity regist(String name, String email, String snsId, SnsDomain snsDomain) {
        return new SnsAccountJpaEntity(null, name, name, email, snsId, snsDomain, null);
    }
}
