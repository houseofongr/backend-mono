package com.hoo.aoo.admin.domain.universe.space;

import org.assertj.core.api.Assertions;
import org.hibernate.loader.ast.internal.CollectionLoaderSingleKey;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;

import static org.assertj.core.api.Assertions.assertThat;
import static org.junit.jupiter.api.Assertions.*;

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
        assertThat(newSpace.getImageId()).isEqualTo(imageId);
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

}