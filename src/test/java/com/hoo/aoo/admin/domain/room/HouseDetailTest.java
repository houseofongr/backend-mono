package com.hoo.aoo.admin.domain.room;

import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;

import static org.assertj.core.api.Assertions.assertThat;

class HouseDetailTest {

    @Test
    @DisplayName("룸 아이디 수정 테스트")
    void testUpdateInfo() {
        // given
        RoomName roomName = new RoomName("거실");
        RoomName roomName2 = new RoomName("거실");
        RoomName roomName3 = new RoomName("거실");

        // when
        roomName.update("현관");
        roomName2.update("");
        roomName3.update(null);

        // then
        assertThat(roomName.getName()).isEqualTo("현관");
        assertThat(roomName2.getName()).isEqualTo("");
        assertThat(roomName3.getName()).isEqualTo("거실");
    }
}