package com.hoo.aoo.admin.domain;

import com.hoo.aoo.admin.domain.exception.AreaLimitExceededException;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;

import static org.assertj.core.api.Assertions.*;

class AreaTest {

    @Test
    @DisplayName("영역 범위 테스트")
    void testAreaBoundary() throws AreaLimitExceededException {

        assertThatThrownBy(() -> new Area(32768, 32768)).isInstanceOf(AreaLimitExceededException.class);
        assertThatThrownBy(() -> new Area(32767, 32768)).isInstanceOf(AreaLimitExceededException.class);
        assertThatThrownBy(() -> new Area(32768, 32767)).isInstanceOf(AreaLimitExceededException.class);

        Area area = new Area(32767, 32767);

        assertThatThrownBy(() -> new Area(0, 0)).isInstanceOf(AreaLimitExceededException.class);
        assertThatThrownBy(() -> new Area(1, 0)).isInstanceOf(AreaLimitExceededException.class);
        assertThatThrownBy(() -> new Area(0, 1)).isInstanceOf(AreaLimitExceededException.class);

        area = new Area(1, 1);
    }

    @Test
    @DisplayName("영역 Getter 테스트")
    void testGetter() throws AreaLimitExceededException {
        Area area = new Area(32766, 32767);
        assertThat(area.getWidth()).isEqualTo(32766);
        assertThat(area.getHeight()).isEqualTo(32767);
    }

}