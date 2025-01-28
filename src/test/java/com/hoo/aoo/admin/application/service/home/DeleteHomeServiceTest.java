package com.hoo.aoo.admin.application.service.home;

import com.hoo.aoo.admin.application.port.out.home.DeleteHomePort;
import com.hoo.aoo.common.application.port.in.MessageDto;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;

import static org.assertj.core.api.Assertions.assertThat;
import static org.mockito.Mockito.*;

class DeleteHomeServiceTest {

    DeleteHomeService sut;
    DeleteHomePort deleteHomePort;

    @BeforeEach
    void init() {
        deleteHomePort = mock();
        sut = new DeleteHomeService(deleteHomePort);
    }

    @Test
    @DisplayName("홈 삭제 테스트")
    void testDeleteHome() {
        // given
        Long id = 1L;

        // when
        MessageDto messageDto = sut.delete(id);

        // then
        verify(deleteHomePort, times(1)).delete(id);
        assertThat(messageDto.message()).isEqualTo(id + "번 홈이 삭제되었습니다.");
    }
}