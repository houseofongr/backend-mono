package com.hoo.aoo.common.adapter.out.persistence.entity;

import com.hoo.aoo.admin.domain.universe.Category;
import com.hoo.aoo.admin.domain.universe.PublicStatus;
import com.hoo.aoo.admin.domain.universe.Universe;
import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;

import java.util.ArrayList;
import java.util.HashSet;
import java.util.List;
import java.util.Set;

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

    @OneToMany(fetch = FetchType.LAZY, mappedBy = "universe", cascade = CascadeType.REMOVE, orphanRemoval = true)
    private List<UniverseHashtagJpaEntity> universeHashtags;

    @OneToMany(fetch = FetchType.LAZY, mappedBy = "universe", cascade = CascadeType.REMOVE)
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

    public void update(Universe universe) {
        this.title = universe.getBasicInfo().getTitle();
        this.description = universe.getBasicInfo().getDescription();
        this.category = universe.getBasicInfo().getCategory();
        this.publicStatus = universe.getBasicInfo().getPublicStatus();
        this.thumbnailFileId = universe.getThumbnailId();
        this.thumbMusicFileId = universe.getThumbMusicId();
    }
}
