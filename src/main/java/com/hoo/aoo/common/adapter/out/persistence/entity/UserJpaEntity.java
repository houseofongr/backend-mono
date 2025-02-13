package com.hoo.aoo.common.adapter.out.persistence.entity;

import com.hoo.aoo.admin.domain.user.User;
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
    private String realName;

    @Column(nullable = true, length = 255)
    private String nickname;

    @Column(nullable = true, length = 255)
    private String email;

    @Column(length = 20)
    private String phoneNumber;

    @Column(nullable = false)
    @ColumnDefault("0")
    private Boolean termsOfUseAgreement;

    @Column(nullable = false)
    @ColumnDefault("0")
    private Boolean personalInformationAgreement;

    @OneToMany(fetch = FetchType.LAZY, mappedBy = "userEntity")
    private List<SnsAccountJpaEntity> snsAccountEntities;

    public static UserJpaEntity create(User user, List<SnsAccountJpaEntity> snsAccountJpaEntities) {

        UserJpaEntity userJpaEntity = new UserJpaEntity(null,
                user.getUserInfo().getRealName(),
                user.getUserInfo().getNickname(),
                user.getUserInfo().getEmail(),
                null,
                user.getAgreement().getTermsOfUseAgreement(),
                user.getAgreement().getPersonalInformationAgreement(),
                snsAccountJpaEntities);

        snsAccountJpaEntities.forEach(snsAccountJpaEntity -> snsAccountJpaEntity.setUserEntity(userJpaEntity));

        return userJpaEntity;
    }

    public void update(User user) {
        nickname = user.getUserInfo().getNickname();
    }
}
