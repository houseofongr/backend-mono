package com.aoo.common.adapter.out.persistence.entity;

import com.aoo.admin.domain.universe.space.piece.Piece;
import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;

import java.util.ArrayList;
import java.util.List;

@Entity
@Table(name = "PIECE")
@Getter
@NoArgsConstructor
@AllArgsConstructor
public class PieceJpaEntity extends DateColumnBaseEntity {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(nullable = false, length = 100)
    private String title;

    @Column(nullable = false, length = 5000)
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
    private Long universeId;

    @Column
    private Long parentSpaceId;

    @OneToMany(fetch = FetchType.LAZY, mappedBy = "piece")
    private List<SoundJpaEntity> sounds;

    public static PieceJpaEntity create(Piece piece) {
        return new PieceJpaEntity(
                null,
                piece.getBasicInfo().getTitle(),
                piece.getBasicInfo().getDescription(),
                piece.getPosInfo().getSx(),
                piece.getPosInfo().getSy(),
                piece.getPosInfo().getEx(),
                piece.getPosInfo().getEy(),
                piece.getFileInfo().getInnerImageId(),
                piece.getBasicInfo().getUniverseId(),
                piece.getBasicInfo().getParentSpaceId(),
                new ArrayList<>()
        );
    }

    public void update(Piece piece) {
        this.innerImageFileId = piece.getFileInfo().getInnerImageId();
        this.title = piece.getBasicInfo().getTitle();
        this.description = piece.getBasicInfo().getDescription();
        this.sx = piece.getPosInfo().getSx();
        this.sy = piece.getPosInfo().getSy();
        this.ex = piece.getPosInfo().getEx();
        this.ey = piece.getPosInfo().getEy();
    }
}
