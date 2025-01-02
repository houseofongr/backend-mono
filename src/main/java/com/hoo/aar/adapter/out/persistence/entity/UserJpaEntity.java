package com.hoo.aar.adapter.out.persistence.entity;

import com.hoo.aar.domain.User;
import jakarta.persistence.*;
import lombok.AccessLevel;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import org.hibernate.annotations.ColumnDefault;

@Entity
@Table(name = "AAR_USER")

@Getter
@NoArgsConstructor(access = AccessLevel.PROTECTED)
@AllArgsConstructor
public class UserJpaEntity extends DateColumnBaseEntity {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(nullable = false, length = 255)
    private String name;

    @Column(nullable = false, length = 20)
    private String phoneNumber;

    @Column(nullable = true, length = 255)
    private String nickname;

    @Column(nullable = false)
    @ColumnDefault("0")
    private Boolean recordAgreement;

    @Column(nullable = false)
    @ColumnDefault("0")
    private Boolean personalInformationAgreement;

    public static UserJpaEntity regist(User user) {
        return new UserJpaEntity(null, user.getName(), user.getPhoneNumber(), user.getNickname(), user.getRecordAgreement(), user.getPersonalInformationAgreement());
    }
}
