package com.hoo.aoo.admin.application.service.soundsource;

import com.hoo.aoo.admin.application.port.in.soundsource.QuerySoundSourceResult;
import com.hoo.aoo.admin.application.port.out.soundsource.FindSoundSourcePort;
import com.hoo.aoo.common.application.service.MockEntityFactoryService;
import org.assertj.core.api.Assertions;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;

import java.util.Optional;

import static org.assertj.core.api.Assertions.assertThat;
import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.*;

class QuerySoundSourceServiceTest {

    QuerySoundSourceService sut;

    FindSoundSourcePort findSoundSourcePort;

    @BeforeEach
    void init() {
        findSoundSourcePort = mock();
        sut = new QuerySoundSourceService(findSoundSourcePort);
    }

    @Test
    @DisplayName("음원 조회 서비스 테스트")
    void testQuerySoundSource() throws Exception {
        // given
        Long id = 1L;

        // when
        when(findSoundSourcePort.loadSoundSource(id)).thenReturn(Optional.of(MockEntityFactoryService.loadSoundSource()));
        QuerySoundSourceResult querySoundSourceResult = sut.querySoundSource(id);

        // then
        assertThat(querySoundSourceResult).isNotNull();
        assertThat(querySoundSourceResult.createdDate()).matches("\\d{4}\\.\\d{2}\\.\\d{2}\\.");
    }
}