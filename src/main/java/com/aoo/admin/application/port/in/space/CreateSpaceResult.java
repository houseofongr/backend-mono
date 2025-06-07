package com.aoo.admin.application.port.in.space;

import com.aoo.common.adapter.out.persistence.entity.SpaceJpaEntity;

public record CreateSpaceResult(
        String message,
        Long spaceId,
        Long innerImageId,
        String title,
        String description,
        Float dx,
        Float dy,
        Float scaleX,
        Float scaleY
) {
    public static CreateSpaceResult of(SpaceJpaEntity spaceJpaEntity) {
        return new CreateSpaceResult(
                String.format("[#%d]번 스페이스가 생성되었습니다.", spaceJpaEntity.getId()),
                spaceJpaEntity.getId(),
                spaceJpaEntity.getInnerImageFileId(),
                spaceJpaEntity.getTitle(),
                spaceJpaEntity.getDescription(),
                spaceJpaEntity.getSx(),
                spaceJpaEntity.getSy(),
                spaceJpaEntity.getEx(),
                spaceJpaEntity.getEy()
        );
    }
}
