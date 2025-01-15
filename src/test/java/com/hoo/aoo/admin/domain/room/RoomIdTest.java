package com.hoo.aoo.admin.domain.room;

import com.hoo.aoo.admin.domain.house.HouseId;
import com.hoo.aoo.admin.domain.house.room.RoomId;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;

import static org.assertj.core.api.Assertions.assertThat;

class RoomIdTest {

    @Test
    @DisplayName("룸 아이디 수정 테스트")
    void testUpdate() {
        // given
        RoomId roomId = new RoomId(new HouseId("", "", ""), "거실");
        RoomId roomId2 = new RoomId(new HouseId("", "", ""), "거실");
        RoomId roomId3 = new RoomId(new HouseId("", "", ""), "거실");

        // when
        roomId.update("현관");
        roomId2.update("");
        roomId3.update(null);

        // then
        assertThat(roomId.getName()).isEqualTo("현관");
        assertThat(roomId2.getName()).isEqualTo("");
        assertThat(roomId3.getName()).isEqualTo("거실");
    }
}