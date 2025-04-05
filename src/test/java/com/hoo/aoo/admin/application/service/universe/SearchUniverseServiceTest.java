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
    @DisplayName("검색조건 검증 -> 카테고리에 존재하지 않는 키워드 사용")
    void testBadKeyword() {
        // given
        SearchUniverseCommand invalidCommand = new SearchUniverseCommand(Pageable.ofSize(10), null, false, UniverseSearchType.CATEGORY, "bad keyword");
        SearchUniverseCommand validCommand = new SearchUniverseCommand(Pageable.ofSize(10), null, false, UniverseSearchType.CATEGORY, "life");

        // when
        assertThatThrownBy(() -> sut.search(invalidCommand)).hasMessage(AdminErrorCode.INVALID_SEARCH_TYPE.getMessage());

        // then
        sut.search(validCommand);
    }

    @Test
    @DisplayName("유니버스 검색 서비스 테스트 - 어댑터 계층으로 위임")
    void testSearchUniverseService() {
        // given
        SearchUniverseCommand command = new SearchUniverseCommand(Pageable.ofSize(10), null, false, UniverseSearchType.CATEGORY, "life");

        // when
        sut.search(command);

        // then
        verify(findUniversePort, times(1)).search(any());
    }

}