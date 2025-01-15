package com.hoo.aoo.admin.domain.house;

import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;

import static org.assertj.core.api.Assertions.assertThat;

class HouseIdTest {

    @Test
    @DisplayName("업데이트 테스트")
    void testUpdate() {
        // given
        HouseId houseId = new HouseId("cozy house", "leaf", "this is cozy house.");
        HouseId houseId2 = new HouseId("cozy house", "leaf", "this is cozy house.");

        String title = "not cozy house";
        String author = null;
        String description = "this is not cozy house.";

        // when
        houseId.update(title, author, description);
        houseId2.update(title, "", description);

        // then
        assertThat(houseId.getTitle()).isEqualTo(title);
        assertThat(houseId.getAuthor()).isEqualTo("leaf");
        assertThat(houseId.getDescription()).isEqualTo(description);

        assertThat(houseId2.getAuthor()).isEqualTo("");
    }

}