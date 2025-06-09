package com.aoo.admin.domain.universe.space;

import com.aoo.common.application.service.MockEntityFactoryService;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;

import static org.assertj.core.api.Assertions.assertThat;

class SpaceTest {

    @Test
    @DisplayName("요청 정보(제목, 내용, 위치좌표(startX, startY), 크기(endX, endY), 유니버스id, 상위 스페이스, 내부사진id)로 스페이스 생성")
    void createSpace() {
        // given
        Long id = 1L;
        Long universeId = 12L;
        Long imageId = 100L;
        String title = "공간";
        String description = "스페이스는 공간입니다.";
        Float sx = 1000 / 5000F;
        Float sy = 2000 / 5000F;
        Float ex = 1500 / 5000F;
        Float ey = 1500 / 5000F;

        // when
        Space newSpace = Space.create(id, imageId, universeId, null, title, description, sx, sy, ex, ey);

        // then
        assertThat(newSpace.getId()).isEqualTo(id);
        assertThat(newSpace.getBasicInfo().getUniverseId()).isEqualTo(universeId);
        assertThat(newSpace.getFileInfo().getInnerImageId()).isEqualTo(imageId);
        assertThat(newSpace.getBasicInfo().getTitle()).isEqualTo(title);
        assertThat(newSpace.getBasicInfo().getDescription()).isEqualTo(description);
        assertThat(newSpace.getPosInfo().getSx()).isEqualTo(sx);
        assertThat(newSpace.getPosInfo().getSy()).isEqualTo(sy);
        assertThat(newSpace.getPosInfo().getEx()).isEqualTo(ex);
        assertThat(newSpace.getPosInfo().getEy()).isEqualTo(ey);
    }

//    @Test
//    @DisplayName("하위 스페이스 생성")
//    void testCreateChildSpace() {
//        // given
//        Long id = 1L;
//        Long universeId = 12L;
//        Long imageId = 100L;
//        String title = "공간";
//        String description = "스페이스는 공간입니다.";
//        Float startX = 1000 / 5000F;
//        Float startY = 2000 / 5000F;
//        Float endX = 1500 / 5000F;
//        Float endY = 1500 / 5000F;
//        Space parentSpace = Space.create(id, imageId, id, title, description, startX, startY, endX, endY, null);
//
//        // when
//        Space childSpace = Space.create(2L, imageId, id, title, description, startX, startY, endX, endY, parentSpace);
//
//        // then
//        assertThat(parentSpace.getUniverseId()).isEqualTo(childSpace.getUniverseId());
//        assertThat(childSpace.getTreeInfo().getDepth()).isEqualTo(2);
//        assertThat(childSpace.getTreeInfo().getParentSpace()).isEqualTo(parentSpace);
//        assertThat(parentSpace.getTreeInfo().getChildSpaces()).contains(childSpace);
//    }

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
    @DisplayName("위치정보(startX, startY, endX, endY) 수정하기")
    void testUpdatePosInfo() {
        // given
        Float sx = 0.1f;
        Float sy = 0.2f;
        Float ex = 0.3f;
        Float ey = 0.4f;
        Space space = MockEntityFactoryService.getParentSpace();

        // try 1
        space.updatePosInfo(sx, sy, null, null);
        assertThat(space.getPosInfo().getSx()).isEqualTo(sx);
        assertThat(space.getPosInfo().getSy()).isEqualTo(sy);
        assertThat(space.getPosInfo().getEx()).isEqualTo(0.8f);
        assertThat(space.getPosInfo().getEy()).isEqualTo(0.7f);

        // try 2
        space.updatePosInfo(null, null, ex, ey);
        assertThat(space.getPosInfo().getSx()).isEqualTo(sx);
        assertThat(space.getPosInfo().getSy()).isEqualTo(sy);
        assertThat(space.getPosInfo().getEx()).isEqualTo(ex);
        assertThat(space.getPosInfo().getEy()).isEqualTo(ey);
    }

//    @Test
//    @DisplayName("이미지 수정하기")
//    void testUpdateInnerImageId() {
//        // given
//        Long imageId = 4321L;
//        Space space = MockEntityFactoryService.getParentSpace();
//
//        space.updateInnerImage(imageId);
//        assertThat(space.getInnerImageId()).isEqualTo(imageId);
//    }
}