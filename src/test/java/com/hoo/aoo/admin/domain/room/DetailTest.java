package com.hoo.aoo.admin.domain.room;

import com.hoo.aoo.admin.domain.house.room.Detail;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;

import static org.assertj.core.api.Assertions.assertThat;

class DetailTest {

    @Test
    @DisplayName("룸 아이디 수정 테스트")
    void testUpdateInfo() {
        // given
        Detail detail = new Detail("거실");
        Detail detail2 = new Detail("거실");
        Detail detail3 = new Detail("거실");

        // when
        detail.update("현관");
        detail2.update("");
        detail3.update(null);

        // then
        assertThat(detail.getName()).isEqualTo("현관");
        assertThat(detail2.getName()).isEqualTo("");
        assertThat(detail3.getName()).isEqualTo("거실");
    }
}