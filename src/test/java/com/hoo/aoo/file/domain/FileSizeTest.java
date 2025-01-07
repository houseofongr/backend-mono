package com.hoo.aoo.file.domain;

import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;

import static org.assertj.core.api.Assertions.assertThat;

class FileSizeTest {

    @Test
    @DisplayName("Byte 단위 변환")
    void testGetByte() {
        // given
        FileSize _999ByteSize = new FileSize(999L);

        // when
        String _999Byte = _999ByteSize.getUnitSize();

        // then
        assertThat(_999Byte).isEqualTo("999Byte");
    }

    @Test
    @DisplayName("KB 단위 변환")
    void testGetKBSize() {
        // given
        FileSize _1KBSize = new FileSize(1001L);
        FileSize _1KBSize2 = new FileSize(1024L);
        FileSize _999KBSize = new FileSize(1024 * 999L);

        // when
        String _1KB = _1KBSize.getUnitSize();
        String _1KB2 = _1KBSize2.getUnitSize();
        String _999KB = _999KBSize.getUnitSize();

        // then
        assertThat(_1KB).isEqualTo("0.98KB");
        assertThat(_1KB2).isEqualTo("1.00KB");
        assertThat(_999KB).isEqualTo("999.00KB");
    }

    @Test
    @DisplayName("MB 단위 변환")
    void testGetMBSize() {
        // given
        FileSize _1MBSize = new FileSize(1024 * 1000L);

        // when
        String _1MB = _1MBSize.getUnitSize();

        // then
        assertThat(_1MB).isEqualTo("0.98MB");
    }
}