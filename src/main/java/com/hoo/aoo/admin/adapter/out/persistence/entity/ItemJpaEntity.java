package com.hoo.aoo.admin.adapter.out.persistence.entity;

import com.hoo.aoo.admin.domain.item.*;
import com.hoo.aoo.common.adapter.out.persistence.entity.DateColumnBaseEntity;
import com.hoo.aoo.common.adapter.out.persistence.entity.UserJpaEntity;
import jakarta.persistence.*;
import lombok.Getter;
import lombok.NoArgsConstructor;
import org.hibernate.annotations.DiscriminatorOptions;

import java.util.List;

@Entity
@Table(name = "ITEM")
@Getter
@NoArgsConstructor
public class ItemJpaEntity extends DateColumnBaseEntity {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(nullable = false, length = 255)
    private String name;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "HOME_ID")
    private HomeJpaEntity home;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "ROOM_ID")
    private RoomJpaEntity room;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "USER_ID")
    private UserJpaEntity user;

    @OneToOne(fetch = FetchType.EAGER, cascade = CascadeType.ALL, orphanRemoval = true)
    @JoinColumn(name = "ITEM_SHAPE_ID")
    private ItemShapeJpaEntity shape;

    @OneToMany(fetch = FetchType.LAZY, mappedBy = "item")
    List<SoundSourceJpaEntity> soundSources;

    public ItemJpaEntity(Long id, String name, HomeJpaEntity home, RoomJpaEntity room, UserJpaEntity user, ItemShapeJpaEntity shape, List<SoundSourceJpaEntity> soundSources) {
        this.id = id;
        this.name = name;
        this.home = home;
        this.room = room;
        this.user = user;
        this.shape = shape;
        this.soundSources = soundSources;
    }

    public void update(Item item) {
       this.name = item.getItemDetail().getName();
        switch (item.getShape()) {
           case Rectangle rectangle -> {
               this.shape = new ItemShapeRectangleJpaEntity(
                       null,
                       rectangle.getX(),
                       rectangle.getY(),
                       rectangle.getWidth(),
                       rectangle.getHeight(),
                       rectangle.getRotation()
               );
           }
           case Circle circle -> {
               this.shape = new ItemShapeCircleJpaEntity(
                       null,
                       circle.getX(),
                       circle.getY(),
                       circle.getRadius()
               );
           }
           case Ellipse ellipse -> {
               this.shape = new ItemShapeEllipseJpaEntity(
                       null,
                       ellipse.getX(),
                       ellipse.getY(),
                       ellipse.getRadiusX(),
                       ellipse.getRadiusY(),
                       ellipse.getRotation()
               );
           }
            default -> throw new IllegalStateException("Unexpected value: " + item.getShape());
        }
    }
}
