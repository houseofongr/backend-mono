package com.hoo.aoo.admin.application.port.in.space;

import com.hoo.aoo.admin.application.service.AdminErrorCode;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.mock.web.MockMultipartFile;

import static org.assertj.core.api.Assertions.assertThatThrownBy;

class UpdateSpaceCommandTest {

    @Test
    @DisplayName("요청 ID 필수조건 확인")
    void testContainsTargetId() {
        assertThatThrownBy(() -> new UpdateSpaceCommand(null, null, null, null, null, null, null, null)).hasMessage(AdminErrorCode.ILLEGAL_ARGUMENT_EXCEPTION.getMessage());
        assertThatThrownBy(() -> new UpdateSpaceCommand(-1L, null, null, null, null, null, null, null)).hasMessage(AdminErrorCode.ILLEGAL_ARGUMENT_EXCEPTION.getMessage());
    }
    
    @Test
    @DisplayName("제목(100자/NB) 조건 확인")
    void testTitleCondition() {
        String over100 = "a".repeat(101);
        assertThatThrownBy(() -> new UpdateSpaceCommand(1L, "", null, null, null, null, null, null)).hasMessage(AdminErrorCode.ILLEGAL_ARGUMENT_EXCEPTION.getMessage());
        assertThatThrownBy(() -> new UpdateSpaceCommand(1L, " ", null, null, null, null, null, null)).hasMessage(AdminErrorCode.ILLEGAL_ARGUMENT_EXCEPTION.getMessage());
        assertThatThrownBy(() -> new UpdateSpaceCommand(1L, over100, null, null, null, null, null, null)).hasMessage(AdminErrorCode.ILLEGAL_ARGUMENT_EXCEPTION.getMessage());
    }
    
    @Test
    @DisplayName("내용(5000자) 조건 확인")
    void testDescriptionCondition() {
        String over5000 = "a".repeat(5001);
        assertThatThrownBy(() -> new UpdateSpaceCommand(1L, "블랙홀", over5000, null, null, null, null, null)).hasMessage(AdminErrorCode.ILLEGAL_ARGUMENT_EXCEPTION.getMessage());
    }
    
    @Test
    @DisplayName("위치정보(0 ≤ posInfo ≤ 1) 조건 확인")
    void testPosCondition() {
        assertThatThrownBy(() -> new UpdateSpaceCommand(1L, null, null, -1F, null, null, null, null)).hasMessage(AdminErrorCode.ILLEGAL_ARGUMENT_EXCEPTION.getMessage());
        assertThatThrownBy(() -> new UpdateSpaceCommand(1L, null, null, 1.1F, null, null, null, null)).hasMessage(AdminErrorCode.ILLEGAL_ARGUMENT_EXCEPTION.getMessage());
        assertThatThrownBy(() -> new UpdateSpaceCommand(1L, null, null, null, -1F, null, null, null)).hasMessage(AdminErrorCode.ILLEGAL_ARGUMENT_EXCEPTION.getMessage());
        assertThatThrownBy(() -> new UpdateSpaceCommand(1L, null, null, null, 1.1F, null, null, null)).hasMessage(AdminErrorCode.ILLEGAL_ARGUMENT_EXCEPTION.getMessage());
        assertThatThrownBy(() -> new UpdateSpaceCommand(1L, null, null, null, null, -1F, null, null)).hasMessage(AdminErrorCode.ILLEGAL_ARGUMENT_EXCEPTION.getMessage());
        assertThatThrownBy(() -> new UpdateSpaceCommand(1L, null, null, null, null, 1.1F, null, null)).hasMessage(AdminErrorCode.ILLEGAL_ARGUMENT_EXCEPTION.getMessage());
        assertThatThrownBy(() -> new UpdateSpaceCommand(1L, null, null, null, null, null, -1F, null)).hasMessage(AdminErrorCode.ILLEGAL_ARGUMENT_EXCEPTION.getMessage());
        assertThatThrownBy(() -> new UpdateSpaceCommand(1L, null, null, null, null, null, 1.1F, null)).hasMessage(AdminErrorCode.ILLEGAL_ARGUMENT_EXCEPTION.getMessage());
    }
    
    @Test
    @DisplayName("이미지 크기(2MB) 확인")
    void TestImageCondition() {
        byte[] content = new byte[2 * 1024 * 1024 + 1];
        UpdateSpaceCommand base = new UpdateSpaceCommand(1L, null, null, null, null, null, null, null);
        MockMultipartFile file = new MockMultipartFile("thumbnail", "universe_thumb.png", "image/png", content);
        assertThatThrownBy(() -> UpdateSpaceCommand.from(base, file)).hasMessage(AdminErrorCode.SPACE_FILE_REQUIRED.getMessage());
    }
}