package com.aoo.common.adapter.out.persistence.entity;

import com.aoo.admin.domain.universe.space.Space;
import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;

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
    private Float sx;

    @Column(nullable = false)
    private Float sy;

    @Column(nullable = false)
    private Float ex;

    @Column(nullable = false)
    private Float ey;

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
        this.sx = space.getPosInfo().getSx();
        this.sy = space.getPosInfo().getSy();
        this.ex = space.getPosInfo().getEx();
        this.ey = space.getPosInfo().getEy();
    }
}
