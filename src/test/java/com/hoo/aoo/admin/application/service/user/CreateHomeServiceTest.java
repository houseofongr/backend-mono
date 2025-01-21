package com.hoo.aoo.admin.application.service.user;

import com.hoo.aoo.admin.adapter.out.persistence.HomePersistenceAdapter;
import com.hoo.aoo.admin.application.port.in.home.CreateHomeCommand;
import com.hoo.aoo.admin.application.port.in.home.CreateHomeResult;
import com.hoo.aoo.admin.application.service.home.CreateHomeService;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;

import static org.assertj.core.api.Assertions.assertThat;
import static org.mockito.Mockito.*;

class CreateHomeServiceTest {

    CreateHomeService sut;

    HomePersistenceAdapter homePersistenceAdapter;

    @BeforeEach
    void init() {
        homePersistenceAdapter = mock();
        sut = new CreateHomeService(homePersistenceAdapter);
    }

    @Test
    @DisplayName("홈 생성 서비스 테스트")
    void testCreateHome() {
        // given
        CreateHomeCommand command = new CreateHomeCommand(10L, 20L);

        // when
        when(homePersistenceAdapter.save(command)).thenReturn(new CreateHomeResult(100L));
        CreateHomeResult createHomeResult = sut.create(command);

        // then
        verify(homePersistenceAdapter, times(1)).save(command);
        assertThat(createHomeResult.createdHomeId()).isEqualTo(100L);
    }
}