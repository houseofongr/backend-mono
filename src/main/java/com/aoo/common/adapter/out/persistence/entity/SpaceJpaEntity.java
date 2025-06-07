package com.aoo.common.adapter.out.persistence.entity;

import com.aoo.admin.domain.universe.space.Space;
import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;

import java.util.ArrayList;
import java.util.List;

@Entity
@Table(name = "SPACE")
@Getter
@NoArgsConstructor
@AllArgsConstructor
public class SpaceJpaEntity extends DateColumnBaseEntity {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(nullable = false, length = 100)
    private String title;

    @Column(length = 5000)
    private String description;

    @Column(nullable = false)
    private Float dx;

    @Column(nullable = false)
    private Float dy;

    @Column(nullable = false, name = "SCALE_X")
    private Float scaleX;

    @Column(nullable = false, name = "SCALE_Y")
    private Float scaleY;

    @Column(nullable = false)
    private Long innerImageFileId;

    @Column(nullable = false)
    private Integer depth;

    @Column(nullable = false)
    private Long universeId;

    @Column
    private Long parentSpaceId;

    public static SpaceJpaEntity create(Space space, UniverseJpaEntity universeJpaEntity) {
//        return new SpaceJpaEntity(
//                null,
//                space.getBasicInfo().getTitle(),
//                space.getBasicInfo().getDescription(),
//                space.getPosInfo().getDx(),
//                space.getPosInfo().getDy(),
//                space.getPosInfo().getScaleX(),
//                space.getPosInfo().getScaleY(),
//                space.getFileInfo().getInnerImageId(),
//                space.getTreeInfo().getDepth(),
//                universeJpaEntity,
//                null,
//                new ArrayList<>()
//        );
        return null;
    }

    public static SpaceJpaEntity createChild(Space space, SpaceJpaEntity parent) {
//        SpaceJpaEntity childSpace = new SpaceJpaEntity(
//                null,
//                space.getBasicInfo().getTitle(),
//                space.getBasicInfo().getDescription(),
//                space.getPosInfo().getDx(),
//                space.getPosInfo().getDy(),
//                space.getPosInfo().getScaleX(),
//                space.getPosInfo().getScaleY(),
//                space.getInnerImageId(),
//                space.getTreeInfo().getDepth(),
//                parent.getUniverse(),
//                parent,
//                new ArrayList<>());
//
//        parent.getChildren().add(childSpace);

        return null;
    }

    public void update(Space space) {
        this.innerImageFileId = space.getFileInfo().getInnerImageId();
        this.title = space.getBasicInfo().getTitle();
        this.description = space.getBasicInfo().getDescription();
        this.dx = space.getPosInfo().getDx();
        this.dy = space.getPosInfo().getDy();
        this.scaleX = space.getPosInfo().getScaleX();
        this.scaleY = space.getPosInfo().getScaleY();
    }
}
