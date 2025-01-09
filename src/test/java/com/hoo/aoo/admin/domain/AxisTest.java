package com.hoo.aoo.admin.domain;

import com.hoo.aoo.admin.domain.exception.AxisLimitExceededException;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;

import static org.assertj.core.api.Assertions.*;

class AxisTest {

    @Test
    @DisplayName("좌표 범위 테스트")
    void testAxisBoundary() throws AxisLimitExceededException {
        assertThatThrownBy(() -> new Axis(-1, 0, 0)).isInstanceOf(AxisLimitExceededException.class);
        assertThatThrownBy(() -> new Axis(0, -1, 0)).isInstanceOf(AxisLimitExceededException.class);
        assertThatThrownBy(() -> new Axis(0, 0, -1)).isInstanceOf(AxisLimitExceededException.class);

        new Axis(0, 0, 0);

        assertThatThrownBy(() -> new Axis(32768, 32767, 32767)).isInstanceOf(AxisLimitExceededException.class);
        assertThatThrownBy(() -> new Axis(32767, 32768, 32767)).isInstanceOf(AxisLimitExceededException.class);
        assertThatThrownBy(() -> new Axis(32767, 32767, 32768)).isInstanceOf(AxisLimitExceededException.class);

        Axis axis1 = new Axis(32765, 32766, 32767);
        assertThat(axis1.getX()).isEqualTo(32765);
        assertThat(axis1.getY()).isEqualTo(32766);
        assertThat(axis1.getZ()).isEqualTo(32767);

        assertThatThrownBy(() -> new Axis(-1, 0)).isInstanceOf(AxisLimitExceededException.class);
        assertThatThrownBy(() -> new Axis(0, -1)).isInstanceOf(AxisLimitExceededException.class);

        new Axis(0, 0);

        assertThatThrownBy(() -> new Axis(32767, 32768)).isInstanceOf(AxisLimitExceededException.class);
        assertThatThrownBy(() -> new Axis(32768, 32767)).isInstanceOf(AxisLimitExceededException.class);

        Axis axis2 = new Axis(32767, 32767);

        assertThat(axis2.getZ()).isNull();
    }

    @Test
    @DisplayName("좌표 Getter 테스트")
    void testGetter() throws AxisLimitExceededException {
        Axis axis1 = new Axis(32765, 32766, 32767);
        assertThat(axis1.getX()).isEqualTo(32765);
        assertThat(axis1.getY()).isEqualTo(32766);
        assertThat(axis1.getZ()).isEqualTo(32767);
    }
}