package com.aoo.admin.application.service.universe;

import com.aoo.admin.application.port.in.universe.TraversalUniverseResult;
import com.aoo.admin.application.port.out.universe.FindUniversePort;
import com.aoo.admin.domain.universe.Category;
import com.aoo.admin.domain.universe.PublicStatus;
import com.aoo.admin.domain.universe.TraversalComponents;
import com.aoo.admin.domain.universe.Universe;
import com.aoo.admin.domain.universe.space.Space;
import com.aoo.admin.domain.universe.space.element.Piece;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;

import java.util.List;

import static org.assertj.core.api.Assertions.assertThat;
import static org.mockito.Mockito.*;

class TraversalUniverseServiceTest {

    FindUniversePort findUniversePort = mock();
    TraversalUniverseService sut = new TraversalUniverseService(findUniversePort);

    Universe universe = Universe.create(1L, 1L, 2L, 3L, 1L, "new universe", "새 유니버스", Category.FASHION_AND_BEAUTY, PublicStatus.PRIVATE, List.of("1", "2", "3"));

    Space space1 = Space.create(1L, 4L, 1L, null, "space1", "유니버스의 스페이스-1", 0.5f, 0.5f, 0.7f, 0.6f);
    Space space2 = Space.create(2L, 5L, 1L, null, "space2", "유니버스의 스페이스-2", 0.4f, 0.2f, 0.5f, 0.1f);
    Space space3 = Space.create(3L, 6L, 1L, 1L, "space3", "스페이스1의 스페이스-1", 0.2f, 0.3f, 0.4f, 0.2f);
    Space space4 = Space.create(4L, 7L, 1L, 2L, "space4", "스페이스2의 스페이스-1", 0.7f, 0.4f, 0.3f, 0.3f);
    Space space5 = Space.create(5L, 8L, 1L, 2L, "space5", "스페이스2의 스페이스-2", 0.8f, 0.1f, 0.2f, 0.4f);
    List<Space> spaces = List.of(space1, space2, space3, space4, space5);

    Piece piece1 = Piece.create(1L, 9L, 1L, null, "element1", "유니버스의 엘리먼트-1", 0.5f, 0.5f, 0.7f, 0.6f);
    Piece piece2 = Piece.create(2L, 10L, 1L, 1L, "element2", "스페이스1의 엘리먼트-1", 0.4f, 0.2f, 0.5f, 0.1f);
    Piece piece3 = Piece.create(3L, 11L, 1L, 3L, "element3", "스페이스3의 엘리먼트-1", 0.2f, 0.3f, 0.4f, 0.2f);
    Piece piece4 = Piece.create(4L, 12L, 1L, 4L, "element4", "스페이스4의 엘리먼트-1", 0.7f, 0.4f, 0.3f, 0.3f);
    Piece piece5 = Piece.create(5L, 13L, 1L, 4L, "element5", "스페이스4의 엘리먼트-2", 0.8f, 0.1f, 0.2f, 0.4f);
    Piece piece6 = Piece.create(6L, 14L, 1L, 5L, "element6", "스페이스5의 엘리먼트-1", 0.8f, 0.1f, 0.2f, 0.4f);
    Piece piece7 = Piece.create(7L, 15L, 1L, 5L, "element7", "스페이스5의 엘리먼트-2", 0.8f, 0.1f, 0.2f, 0.4f);
    List<Piece> pieces = List.of(piece1, piece2, piece3, piece4, piece5, piece6, piece7);

    @Test
    @DisplayName("순회 서비스")
    void testTraversalService() {
        // given
        Long universeId = 1L;
        TraversalComponents components = new TraversalComponents(universe, spaces, pieces);

        // when
        when(findUniversePort.findTreeComponents(universeId)).thenReturn(components);
        TraversalUniverseResult result = sut.traversal(universeId);

        // then
        assertThat(result.universeId()).isEqualTo(1L);
        assertThat(result.innerImageId()).isEqualTo(3L);
        assertThat(result.elements()).hasSize(1);
        assertThat(result.spaces()).hasSize(2)
                .anySatisfy(spaceTreeInfo -> {
                    assertThat(spaceTreeInfo.spaceId()).isEqualTo(1L);
                    assertThat(spaceTreeInfo.spaces()).hasSize(1)
                                    .anySatisfy(spaceTreeInfo1 -> {
                                        assertThat(spaceTreeInfo1.spaceId()).isEqualTo(3L);
                                        assertThat(spaceTreeInfo1.elements().getFirst().elementId()).isEqualTo(3L);
                                    });
                    assertThat(spaceTreeInfo.elements()).hasSize(1);
                })
                .anySatisfy(spaceTreeInfo -> {
                    assertThat(spaceTreeInfo.spaceId()).isEqualTo(2L);
                    assertThat(spaceTreeInfo.spaces()).hasSize(2)
                            .anySatisfy(spaceTreeInfo1 -> {
                                assertThat(spaceTreeInfo1.spaceId()).isEqualTo(4L);
                                assertThat(spaceTreeInfo1.elements()).hasSize(2);
                            })
                            .anySatisfy(spaceTreeInfo1 -> {
                                assertThat(spaceTreeInfo1.spaceId()).isEqualTo(5L);
                                assertThat(spaceTreeInfo1.elements()).hasSize(2);
                            });
                });
    }

}