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
    private String name;

    public static CategoryJpaEntity create(String name) {
        return new CategoryJpaEntity(null, name);
    }

    public void update(String name) {
        this.name = name;
    }
}
