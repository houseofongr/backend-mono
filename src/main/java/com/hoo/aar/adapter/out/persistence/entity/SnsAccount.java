package com.hoo.aar.adapter.out.persistence.entity;

import jakarta.persistence.*;

@Entity
@Table(name = "AAR_USER")
public class SnsAccount {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(nullable = false, length = 100)
    private String email;

    @Column(nullable = false, length = 255)
    private String snsId;

    @Column(nullable = false)
    @Enumerated(EnumType.STRING)
    private SnsDomain snsDomain;

    @ManyToOne
    @JoinColumn(name = "USER_ID", nullable = false)
    private User user;
}
