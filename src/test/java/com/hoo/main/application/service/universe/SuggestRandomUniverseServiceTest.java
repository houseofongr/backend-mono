package com.hoo.main.application.service.universe;

import com.hoo.admin.domain.universe.Universe;
import com.hoo.admin.domain.user.User;
import com.hoo.main.application.port.in.universe.SuggestRandomUniverseCommand;
import com.hoo.main.application.port.in.universe.SuggestRandomUniverseResult;
import com.hoo.main.application.port.out.persistence.universe.LoadUniversePort;
import com.hoo.main.application.port.out.persistence.universe.SearchPublicUniversePort;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;

import java.util.ArrayList;
import java.util.List;
import java.util.stream.LongStream;

import static org.assertj.core.api.Assertions.assertThat;
import static org.mockito.Mockito.*;

class SuggestRandomUniverseServiceTest {

    SearchPublicUniversePort searchPublicUniversePort = mock();
    LoadUniversePort loadUniversePort = mock();

    SuggestRandomUniverseService sut = new SuggestRandomUniverseService(searchPublicUniversePort, loadUniversePort);

    @Test
    @DisplayName("랜덤 유니버스 추천 서비스")
    void suggestRandomUniverseService() {
        // given
        List<Long> exceptIds = List.of(1L, 3L);
        List<Long> idList = new ArrayList<>(LongStream.rangeClosed(1, 102)
                .filter(n -> n != 1 && n != 3) // 1과 3 제외
                .boxed()
                .toList());

        // when
        when(searchPublicUniversePort.findNewPublicUniverseIdsLimit100Except(exceptIds)).thenReturn(idList);
        when(loadUniversePort.loadAllUniverseOnly(anyList())).thenAnswer(invocation -> {
            List<Long> ids = invocation.getArgument(0);
            return ids.stream().map(id -> Universe.load(id, null, null, null, null, null, null, null, null, null, List.of(), User.load(1L, "leaf"), null, null)).toList();
        });
        SuggestRandomUniverseResult result = sut.suggestRandomSidebarUniverse(4, new SuggestRandomUniverseCommand(exceptIds));

        // then
        assertThat(result.universes()).hasSizeLessThanOrEqualTo(4);
        assertThat(result.universes()).noneMatch(universeSidebarInfo -> universeSidebarInfo.id().equals(1L));
        assertThat(result.universes()).noneMatch(universeSidebarInfo -> universeSidebarInfo.id().equals(3L));
    }

}