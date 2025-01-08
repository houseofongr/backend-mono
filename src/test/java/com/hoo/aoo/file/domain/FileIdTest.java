package com.hoo.aoo.file.domain;

import org.assertj.core.api.Assertions;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;

import static org.assertj.core.api.Assertions.assertThat;
import static org.junit.jupiter.api.Assertions.*;

class FileIdTest {

    @Test
    @DisplayName("생성 시 directory 뒤에 '/' 가 없으면 추가해주기")
    void testAddSlash() {
        // given
        String directory = "/tmp";
        String fileName = "test.png";

        // when
        FileId fileId = new FileId(directory, fileName);

        // then
        assertThat(fileId.getPath()).isEqualTo("/tmp/test.png");
    }
}