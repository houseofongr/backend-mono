package com.hoo.aoo.admin.adapter.out.persistence.mapper;

import com.hoo.aoo.admin.domain.universe.space.Space;
import com.hoo.aoo.common.adapter.out.persistence.entity.SpaceJpaEntity;
import com.hoo.aoo.common.adapter.out.persistence.entity.UniverseJpaEntity;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;

import java.util.ArrayList;

import static org.assertj.core.api.Assertions.assertThat;
import static org.mockito.Mockito.*;

class SpaceMapperTest {

    SpaceMapper sut = new SpaceMapper();

    @Test
    @DisplayName("대상 스페이스 1개(single) 불러오기")
    void testLoadParentSpace() {
        // given
        UniverseJpaEntity universe = mock();
        SpaceJpaEntity entity = new SpaceJpaEntity(1L, "부모", null, 1f, 1f, 1f, 1f, 1L, 1, universe, null, new ArrayList<>());

        // when
        when(universe.getId()).thenReturn(123L);
        Space space = sut.mapToSingleEntity(entity);

        // then
        assertThat(space.getUniverseId()).isEqualTo(123L);
        assertThat(space.getTreeInfo().getDepth()).isEqualTo(1);
        assertThat(space.getTreeInfo().getParentSpace()).isNull();
        assertThat(space.isRoot()).isTrue();
    }

    @Test
    @DisplayName("조상 스페이스(전체 부모) 불러오기")
    void testLoadChildSpace() {
        // given
        UniverseJpaEntity universe = mock();
        SpaceJpaEntity parent = new SpaceJpaEntity(1L, "부모", null, 1f, 1f, 1f, 1f, 1L, 1, universe, null, new ArrayList<>());
        SpaceJpaEntity child = new SpaceJpaEntity(1L, "자식", null, 1f, 1f, 1f, 1f, 1L, 2, universe, parent, new ArrayList<>());
        parent.getChildren().add(child);

        SpaceJpaEntity grandChild = new SpaceJpaEntity(1L, "손자", null, 1f, 1f, 1f, 1f, 1L, 3, universe, child, new ArrayList<>());
        parent.getChildren().add(grandChild);

        // when
        when(universe.getId()).thenReturn(123L);
        Space parentSpace = sut.mapToAncestorEntity(parent);
        Space childSpace = sut.mapToAncestorEntity(child);
        Space grandChildSpace = sut.mapToAncestorEntity(grandChild);

        // then
        assertThat(grandChildSpace.getUniverseId()).isEqualTo(123L);
        assertThat(grandChildSpace.getTreeInfo().getDepth()).isEqualTo(3);
        assertThat(grandChildSpace.getTreeInfo().getParentSpace().getId()).isEqualTo(childSpace.getId());
        assertThat(grandChildSpace.isRoot()).isFalse();

        assertThat(childSpace.getTreeInfo().getDepth()).isEqualTo(2);
        assertThat(childSpace.getTreeInfo().getParentSpace().getId()).isEqualTo(parentSpace.getId());
        assertThat(childSpace.isRoot()).isFalse();

        assertThat(parentSpace.getTreeInfo().getDepth()).isEqualTo(1);
        assertThat(parentSpace.getTreeInfo().getParentSpace()).isNull();
        assertThat(parentSpace.isRoot()).isTrue();
    }
}