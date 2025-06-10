package com.aoo.admin.application.service.universe;

import com.aoo.admin.application.port.in.universe.TraversalUniverseResult;
import com.aoo.admin.application.port.out.universe.FindUniversePort;
import com.aoo.admin.domain.universe.*;
import com.aoo.admin.domain.universe.space.Space;
import com.aoo.admin.domain.universe.space.piece.Piece;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;

import java.time.ZonedDateTime;
import java.util.List;

import static org.assertj.core.api.Assertions.assertThat;
import static org.mockito.Mockito.*;

class TraversalUniverseServiceTest {

    FindUniversePort findUniversePort = mock();
    TraversalUniverseService sut = new TraversalUniverseService(findUniversePort);

    @Test
    @DisplayName("순회 서비스")
    void testTraversalService() {
        // given
        Long universeId = 1L;
        TraversalComponents components = MockTreeInfo.getTraversalComponent();

        // when
        when(findUniversePort.findTreeComponents(universeId)).thenReturn(components);
        TraversalUniverseResult result = sut.traversal(universeId);

        // then
        assertThat(result.universeId()).isEqualTo(1L);
        assertThat(result.innerImageId()).isEqualTo(3L);
        assertThat(result.pieces()).hasSize(1);
        assertThat(result.spaces()).hasSize(2)
                .anySatisfy(spaceTreeInfo -> {
                    assertThat(spaceTreeInfo.spaceId()).isEqualTo(1L);
                    assertThat(spaceTreeInfo.spaces()).hasSize(1)
                            .anySatisfy(spaceTreeInfo1 -> {
                                assertThat(spaceTreeInfo1.spaceId()).isEqualTo(3L);
                                assertThat(spaceTreeInfo1.pieces().getFirst().pieceId()).isEqualTo(3L);
                            });
                    assertThat(spaceTreeInfo.pieces()).hasSize(1);
                })
                .anySatisfy(spaceTreeInfo -> {
                    assertThat(spaceTreeInfo.spaceId()).isEqualTo(2L);
                    assertThat(spaceTreeInfo.spaces()).hasSize(2)
                            .anySatisfy(spaceTreeInfo1 -> {
                                assertThat(spaceTreeInfo1.spaceId()).isEqualTo(4L);
                                assertThat(spaceTreeInfo1.pieces()).hasSize(2);
                            })
                            .anySatisfy(spaceTreeInfo1 -> {
                                assertThat(spaceTreeInfo1.spaceId()).isEqualTo(5L);
                                assertThat(spaceTreeInfo1.pieces()).hasSize(2);
                            });
                });
    }

}