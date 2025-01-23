package com.hoo.aoo.admin.application.service.user;

import com.hoo.aoo.admin.application.port.in.home.CreateHomeCommand;
import com.hoo.aoo.admin.application.port.in.home.CreateHomeResult;
import com.hoo.aoo.admin.application.port.out.home.SaveHomePort;
import com.hoo.aoo.admin.application.port.out.house.FindHousePort;
import com.hoo.aoo.admin.application.port.out.user.FindUserPort;
import com.hoo.aoo.admin.application.service.home.CreateHomeService;
import com.hoo.aoo.common.FixtureRepository;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;

import java.util.Optional;

import static org.assertj.core.api.Assertions.assertThat;
import static org.mockito.Mockito.*;

class CreateHomeServiceTest {

    CreateHomeService sut;

    FindHousePort findHousePort;

    FindUserPort findUserPort;

    SaveHomePort saveHomePort;

    @BeforeEach
    void init() {
        findHousePort = mock();
        findUserPort = mock();
        saveHomePort = mock();
        sut = new CreateHomeService(findHousePort, findUserPort, saveHomePort);
    }

    @Test
    @DisplayName("홈 생성 서비스 테스트")
    void testCreateHome() throws Exception {
        // given
        CreateHomeCommand command = new CreateHomeCommand(10L, 20L);

        // when
        when(findHousePort.load(20L)).thenReturn(Optional.of(FixtureRepository.getHouse()));
        when(findUserPort.load(10L)).thenReturn(Optional.of(FixtureRepository.getUser()));
        when(saveHomePort.save(any(), any())).thenReturn(new CreateHomeResult(100L, null));
        CreateHomeResult createHomeResult = sut.create(command);

        // then
        verify(saveHomePort, times(1)).save(any(), any());
        assertThat(createHomeResult.createdHomeId()).isEqualTo(100L);
    }
}