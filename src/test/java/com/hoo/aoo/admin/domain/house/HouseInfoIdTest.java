package com.hoo.aoo.admin.domain.house;

import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;

import static org.assertj.core.api.Assertions.assertThat;

class HouseInfoIdTest {

    @Test
    @DisplayName("업데이트 테스트")
    void testUpdateInfo() {
        // given
        Detail detail = new Detail("cozy house", "leaf", "this is cozy house.");
        Detail detail2 = new Detail("cozy house", "leaf", "this is cozy house.");

        String title = "not cozy house";
        String author = null;
        String description = "this is not cozy house.";

        // when
        detail.update(title, author, description);
        detail2.update(title, "", description);

        // then
        assertThat(detail.getTitle()).isEqualTo(title);
        assertThat(detail.getAuthor()).isEqualTo("leaf");
        assertThat(detail.getDescription()).isEqualTo(description);

        assertThat(detail2.getAuthor()).isEqualTo("");
    }

}