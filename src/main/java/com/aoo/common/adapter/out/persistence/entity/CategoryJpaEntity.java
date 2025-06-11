package com.aoo.common.adapter.out.persistence.entity;

import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;

@Entity
@Table(name = "CATEGORY")
@Getter
@NoArgsConstructor
@AllArgsConstructor
public class CategoryJpaEntity {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(nullable = false, length = 100)
    private String titleKor;

    @Column(nullable = false, length = 100)
    private String titleEng;

    public static CategoryJpaEntity create(String kor, String eng) {
        return new CategoryJpaEntity(null, kor, eng);
    }

    public void update(String kor, String eng) {
        this.titleKor = kor != null? kor : this.titleKor;
        this.titleEng = eng != null? eng : this.titleEng;
    }
}
