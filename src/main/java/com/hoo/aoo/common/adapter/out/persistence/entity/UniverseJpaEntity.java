package com.hoo.aoo.common.adapter.out.persistence.entity;

import com.hoo.aoo.admin.domain.universe.Category;
import com.hoo.aoo.admin.domain.universe.PublicStatus;
import com.hoo.aoo.admin.domain.universe.Universe;
import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;

import java.util.ArrayList;
import java.util.List;

@Entity
@Table(name = "UNIVERSE")
@Getter
@NoArgsConstructor
@AllArgsConstructor
public class UniverseJpaEntity extends DateColumnBaseEntity {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(nullable = false, length = 100)
    private String title;

    @Column(nullable = false, length = 5000)
    private String description;

    @Column(nullable = false)
    private Long viewCount;

    @Column(nullable = false)
    @Enumerated(EnumType.STRING)
    private PublicStatus publicStatus;

    @Column(nullable = false)
    @Enumerated(EnumType.STRING)
    private Category category;

    @Column(nullable = false)
    private Long thumbMusicFileId;

    @Column(nullable = false)
    private Long thumbnailFileId;

    @OneToMany(fetch = FetchType.LAZY, mappedBy = "universe")
    private List<UniverseHashtagJpaEntity> universeHashtags;

    @OneToMany(fetch = FetchType.LAZY, mappedBy = "universe")
    private List<UniverseLikeJpaEntity> universeLikes;

    public static UniverseJpaEntity create(Universe universe) {
        return new UniverseJpaEntity(
                null,
                universe.getBasicInfo().getTitle(),
                universe.getBasicInfo().getDescription(),
                universe.getSocialInfo().getViewCount(),
                universe.getBasicInfo().getPublicStatus(),
                universe.getBasicInfo().getCategory(),
                universe.getThumbMusicId(),
                universe.getThumbnailId(),
                new ArrayList<>(),
                List.of());
    }
}
