package com.hoo.aoo.admin.application.service.universe;

import com.hoo.aoo.admin.application.port.in.universe.SearchUniverseCommand;
import com.hoo.aoo.admin.application.port.out.universe.FindUniversePort;
import com.hoo.aoo.admin.application.service.AdminErrorCode;
import com.hoo.aoo.common.adapter.out.persistence.condition.UniverseSearchType;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.data.domain.Pageable;

import static org.assertj.core.api.Assertions.assertThatThrownBy;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.*;

class SearchUniverseServiceTest {

    SearchUniverseService sut;
    FindUniversePort findUniversePort = mock();

    @BeforeEach
    void init() {
        sut = new SearchUniverseService(findUniversePort);
    }

    @Test
    @DisplayName("유니버스 검색 서비스 테스트 - 어댑터 계층으로 위임")
    void testSearchUniverseService() {
        // given
        SearchUniverseCommand command = new SearchUniverseCommand(Pageable.ofSize(10), null, null, "life", "life", false);

        // when
        sut.search(command);

        // then
        verify(findUniversePort, times(1)).search(any());
    }

}