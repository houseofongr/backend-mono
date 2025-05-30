package com.hoo.aoo.admin.domain.universe.space;

import com.hoo.aoo.common.application.service.MockEntityFactoryService;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;

import static org.assertj.core.api.Assertions.assertThat;

class SpaceTest {
    
    @Test
    @DisplayName("요청 정보(제목, 내용, 위치좌표(dx, dy), 크기(scaleX, scaleY), 유니버스id, 상위 스페이스, 내부사진id)로 스페이스 생성")
    void createSpace() {
        // given
        Long id = 1L;
        Long universeId = 12L;
        Long imageId = 100L;
        String title = "공간";
        String description = "스페이스는 공간입니다.";
        Float dx = 1000 / 5000F;
        Float dy = 2000 / 5000F;
        Float scaleX = 1500 / 5000F;
        Float scaleY = 1500 / 5000F;

        // when
        Space newSpace = Space.create(id, imageId, universeId, title, description, dx, dy, scaleX, scaleY, null);
    
        // then
        assertThat(newSpace.getId()).isEqualTo(id);
        assertThat(newSpace.getUniverseId()).isEqualTo(universeId);
        assertThat(newSpace.getInnerImageId()).isEqualTo(imageId);
        assertThat(newSpace.getBasicInfo().getTitle()).isEqualTo(title);
        assertThat(newSpace.getBasicInfo().getDescription()).isEqualTo(description);
        assertThat(newSpace.getPosInfo().getDx()).isEqualTo(dx);
        assertThat(newSpace.getPosInfo().getDy()).isEqualTo(dy);
        assertThat(newSpace.getPosInfo().getScaleX()).isEqualTo(scaleX);
        assertThat(newSpace.getPosInfo().getScaleY()).isEqualTo(scaleY);
        assertThat(newSpace.getTreeInfo().getParentSpace()).isNull();
    }
    
    @Test
    @DisplayName("하위 스페이스 생성")
    void testCreateChildSpace() {
        // given
        Long id = 1L;
        Long universeId = 12L;
        Long imageId = 100L;
        String title = "공간";
        String description = "스페이스는 공간입니다.";
        Float dx = 1000 / 5000F;
        Float dy = 2000 / 5000F;
        Float scaleX = 1500 / 5000F;
        Float scaleY = 1500 / 5000F;
        Space parentSpace = Space.create(id, imageId, universeId, title, description, dx, dy, scaleX, scaleY, null);

        // when
        Space childSpace = Space.create(2L, imageId, universeId, title, description, dx, dy, scaleX, scaleY, parentSpace);

        // then
        assertThat(parentSpace.getUniverseId()).isEqualTo(childSpace.getUniverseId());
        assertThat(childSpace.getTreeInfo().getDepth()).isEqualTo(2);
        assertThat(childSpace.getTreeInfo().getParentSpace()).isEqualTo(parentSpace);
        assertThat(parentSpace.getTreeInfo().getChildSpaces()).contains(childSpace);
    }

    @Test
    @DisplayName("기본정보(제목, 내용) 수정하기")
    void testUpdateBasicInfo() {
        // given
        String title = "블랙홀";
        String description = "블랙홀은 빛도 빨아들입니다.";
        Space space = MockEntityFactoryService.getParentSpace();

        // try 1
        space.updateBasicInfo(null, null);
        assertThat(space.getBasicInfo().getTitle()).isEqualTo("공간");
        assertThat(space.getBasicInfo().getDescription()).isEqualTo(null);

        // try 2
        space.updateBasicInfo(title, null);
        assertThat(space.getBasicInfo().getTitle()).isEqualTo(title);
        assertThat(space.getBasicInfo().getDescription()).isEqualTo(null);

        // try 3
        space.updateBasicInfo(null, description);
        assertThat(space.getBasicInfo().getTitle()).isEqualTo(title);
        assertThat(space.getBasicInfo().getDescription()).isEqualTo(description);
    }
    
    @Test
    @DisplayName("위치정보(dx, dy, scaleX, scaleY) 수정하기")
    void testUpdatePosInfo() {
        // given
        Float dx = 0.1f;
        Float dy = 0.2f;
        Float scaleX = 0.3f;
        Float scaleY = 0.4f;
        Space space = MockEntityFactoryService.getParentSpace();

        // try 1
        space.updatePosInfo(dx, dy, null, null);
        assertThat(space.getPosInfo().getDx()).isEqualTo(dx);
        assertThat(space.getPosInfo().getDy()).isEqualTo(dy);
        assertThat(space.getPosInfo().getScaleX()).isEqualTo(0.8f);
        assertThat(space.getPosInfo().getScaleY()).isEqualTo(0.7f);

        // try 2
        space.updatePosInfo(null, null, scaleX, scaleY);
        assertThat(space.getPosInfo().getDx()).isEqualTo(dx);
        assertThat(space.getPosInfo().getDy()).isEqualTo(dy);
        assertThat(space.getPosInfo().getScaleX()).isEqualTo(scaleX);
        assertThat(space.getPosInfo().getScaleY()).isEqualTo(scaleY);
    }

    @Test
    @DisplayName("이미지 수정하기")
    void testUpdateInnerImageId() {
        // given
        Long imageId = 4321L;
        Space space = MockEntityFactoryService.getParentSpace();

        space.updateInnerImage(imageId);
        assertThat(space.getInnerImageId()).isEqualTo(imageId);
    }
}