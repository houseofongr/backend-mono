package com.hoo.aoo.admin.domain.home;

import com.hoo.aoo.admin.domain.house.House;
import com.hoo.aoo.admin.domain.user.User;
import com.hoo.aoo.common.FixtureRepository;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;

import static org.assertj.core.api.Assertions.assertThat;

class HomeNameTest {

    @Test
    @DisplayName("홈 이름 생성 테스트")
    void testCreateHomeName() throws Exception {
        // given
        House house = FixtureRepository.getHouse();
        User user = FixtureRepository.getUser();

        // when
        HomeName homeName = new HomeName(house, user);

        // then
        assertThat(homeName.getName()).isEqualTo(user.getUserName().getNickName() + "의 " + house.getDetail().getTitle());
    }
}