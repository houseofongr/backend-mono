package com.aoo.admin.application.port.in.space;

import com.aoo.common.adapter.out.persistence.entity.SpaceJpaEntity;

public record UpdateSpaceResult() {
    public record Detail(
            String message,
            String title,
            String description
    ) {
        public static Detail of(SpaceJpaEntity spaceJpaEntity) {
            return new Detail(
                    String.format("[#%d]번 스페이스의 상세정보가 수정되었습니다.", spaceJpaEntity.getId()),
                    spaceJpaEntity.getTitle(),
                    spaceJpaEntity.getDescription()
            );
        }
    }

    public record InnerImage(
            String message,
            Long deletedInnerImageId,
            Long newInnerImageId
    ) {

        public static InnerImage of(Long spaceId, Long deletedInnerImageId, Long newInnerImageId) {
            return new InnerImage(
                    String.format("[#%d]번 스페이스의 내부이미지가 수정되었습니다.", spaceId),
                    deletedInnerImageId,
                    newInnerImageId
            );
        }
    }
}
