package com.hoo.aoo.admin.domain.home;

import com.hoo.aoo.admin.domain.house.House;
import com.hoo.aoo.admin.domain.user.User;
import com.hoo.aoo.common.FixtureRepository;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;

import static org.assertj.core.api.Assertions.assertThat;

class HomeDetailTest {

    @Test
    @DisplayName("홈 이름 생성 테스트")
    void testCreateHomeName() throws Exception {
        // given
        House house = FixtureRepository.getHouse();
        User user = FixtureRepository.getUser();

        // when
        HomeDetail detail = new HomeDetail(house, user);

        // then
        assertThat(detail.getName()).isEqualTo(user.getUserName().getNickName() + "의 " + house.getHouseDetail().getTitle());
    }
}