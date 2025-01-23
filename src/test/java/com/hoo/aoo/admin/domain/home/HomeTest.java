package com.hoo.aoo.admin.domain.home;

import com.hoo.aoo.admin.domain.house.House;
import com.hoo.aoo.admin.domain.user.User;
import com.hoo.aoo.common.FixtureRepository;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;

import static org.assertj.core.api.Assertions.assertThat;

class HomeTest {

    @Test
    @DisplayName("하우스와 유저로부터 홈 생성")
    void testCreateHome() throws Exception {
        // given
        House house = FixtureRepository.getHouse();
        User user = FixtureRepository.getUser();

        // when
        Home home = Home.create(house, user);

        // then
        assertThat(home).isNotNull();
        assertThat(home.getHomeName().getName()).isEqualTo("leaf의 cozy house");
    }
}