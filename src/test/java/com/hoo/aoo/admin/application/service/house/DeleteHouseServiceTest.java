package com.hoo.aoo.admin.application.service.house;

import com.hoo.aoo.admin.application.port.out.house.DeleteHousePort;
import com.hoo.aoo.admin.application.port.out.house.FindHousePort;
import com.hoo.aoo.common.application.port.in.MessageDto;
import com.hoo.aoo.common.application.service.MockEntityFactoryService;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;

import java.util.Optional;

import static org.assertj.core.api.Assertions.assertThat;
import static org.mockito.Mockito.*;

class DeleteHouseServiceTest {

    DeleteHouseService sut;

    FindHousePort findHousePort;
    DeleteHousePort deleteHousePort;

    @BeforeEach
    void init() {
        findHousePort = mock();
        deleteHousePort = mock();
        sut = new DeleteHouseService(findHousePort, deleteHousePort);
    }

    @Test
    @DisplayName("하우스 삭제 서비스 테스트")
    void testDeleteHouseHouse() throws Exception {
        // given
        Long id = 1L;

        // when
        when(findHousePort.load(id)).thenReturn(Optional.of(MockEntityFactoryService.getHouse()));
        MessageDto messageDto = sut.deleteHouse(id);

        // then
        verify(deleteHousePort, times(1)).deleteHouse(anyLong());
        assertThat(messageDto.message()).isEqualTo("1번 하우스가 삭제되었습니다.");
    }

}