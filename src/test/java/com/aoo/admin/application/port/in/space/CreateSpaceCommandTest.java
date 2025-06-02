package com.aoo.admin.application.port.in.space;

import com.aoo.admin.application.port.in.space.CreateSpaceCommand;
import com.aoo.admin.application.service.AdminErrorCode;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.mock.web.MockMultipartFile;

import static org.assertj.core.api.AssertionsForClassTypes.assertThatThrownBy;

class CreateSpaceCommandTest {

    MockMultipartFile basicImage = new MockMultipartFile("image", "image.png", "image/png", "basic image".getBytes());

    @Test
    @DisplayName("제목(100자/NB), 내용(5000자) 조건 확인")
    void testBasicInfo() {
        String emptyTitle = "";
        String blankTitle = " ";
        String length100 = "a".repeat(101);
        String length5000 = "a".repeat(5001);

        assertThatThrownBy(() -> new CreateSpaceCommand(1L, 1L, emptyTitle, null, 0.5F, 0.5F, 0.5F, 0.5F, basicImage)).hasMessage(AdminErrorCode.ILLEGAL_ARGUMENT_EXCEPTION.getMessage());
        assertThatThrownBy(() -> new CreateSpaceCommand(1L, 1L, blankTitle, null, 0.5F, 0.5F, 0.5F, 0.5F, basicImage)).hasMessage(AdminErrorCode.ILLEGAL_ARGUMENT_EXCEPTION.getMessage());
        assertThatThrownBy(() -> new CreateSpaceCommand(1L, 1L, length100, null, 0.5F, 0.5F, 0.5F, 0.5F, basicImage)).hasMessage(AdminErrorCode.ILLEGAL_ARGUMENT_EXCEPTION.getMessage());
        assertThatThrownBy(() -> new CreateSpaceCommand(1L, 1L, "공간", length5000, 0.5F, 0.5F, 0.5F, 0.5F, basicImage)).hasMessage(AdminErrorCode.ILLEGAL_ARGUMENT_EXCEPTION.getMessage());
    }
    
    @Test
    @DisplayName("위치(dx, dy, scalex, scaley : 0 ~ 1) 조건 확인")
    void testPositionInfo() {
        // null
        assertThatThrownBy(() -> new CreateSpaceCommand(1L, 1L, "공간", null, null, 0.5F, 0.5F, 0.5F, basicImage)).hasMessage(AdminErrorCode.ILLEGAL_ARGUMENT_EXCEPTION.getMessage());
        assertThatThrownBy(() -> new CreateSpaceCommand(1L, 1L, "공간", null, 0.5F, null, 0.5F, 0.5F, basicImage)).hasMessage(AdminErrorCode.ILLEGAL_ARGUMENT_EXCEPTION.getMessage());
        assertThatThrownBy(() -> new CreateSpaceCommand(1L, 1L, "공간", null, 0.5F, 0.5F, null, 0.5F, basicImage)).hasMessage(AdminErrorCode.ILLEGAL_ARGUMENT_EXCEPTION.getMessage());
        assertThatThrownBy(() -> new CreateSpaceCommand(1L, 1L, "공간", null, 0.5F, 0.5F, 0.5F, null, basicImage)).hasMessage(AdminErrorCode.ILLEGAL_ARGUMENT_EXCEPTION.getMessage());

        // 0 미만
        assertThatThrownBy(() -> new CreateSpaceCommand(1L, 1L, "공간", null, -0.5F, 0.5F, 0.5F, 0.5F, basicImage)).hasMessage(AdminErrorCode.ILLEGAL_ARGUMENT_EXCEPTION.getMessage());
        assertThatThrownBy(() -> new CreateSpaceCommand(1L, 1L, "공간", null, 0.5F, -0.5F, 0.5F, 0.5F, basicImage)).hasMessage(AdminErrorCode.ILLEGAL_ARGUMENT_EXCEPTION.getMessage());
        assertThatThrownBy(() -> new CreateSpaceCommand(1L, 1L, "공간", null, 0.5F, 0.5F, -0.5F, 0.5F, basicImage)).hasMessage(AdminErrorCode.ILLEGAL_ARGUMENT_EXCEPTION.getMessage());
        assertThatThrownBy(() -> new CreateSpaceCommand(1L, 1L, "공간", null, 0.5F, 0.5F, 0.5F, -0.5F, basicImage)).hasMessage(AdminErrorCode.ILLEGAL_ARGUMENT_EXCEPTION.getMessage());

        // 1 이상
        assertThatThrownBy(() -> new CreateSpaceCommand(1L, 1L, "공간", null, 1.5F, 0.5F, 0.5F, 0.5F, basicImage)).hasMessage(AdminErrorCode.ILLEGAL_ARGUMENT_EXCEPTION.getMessage());
        assertThatThrownBy(() -> new CreateSpaceCommand(1L, 1L, "공간", null, 0.5F, 1.5F, 0.5F, 0.5F, basicImage)).hasMessage(AdminErrorCode.ILLEGAL_ARGUMENT_EXCEPTION.getMessage());
        assertThatThrownBy(() -> new CreateSpaceCommand(1L, 1L, "공간", null, 0.5F, 0.5F, 1.5F, 0.5F, basicImage)).hasMessage(AdminErrorCode.ILLEGAL_ARGUMENT_EXCEPTION.getMessage());
        assertThatThrownBy(() -> new CreateSpaceCommand(1L, 1L, "공간", null, 0.5F, 0.5F, 0.5F, 1.5F, basicImage)).hasMessage(AdminErrorCode.ILLEGAL_ARGUMENT_EXCEPTION.getMessage());
    }
    
    @Test
    @DisplayName("내부사진 없거나 100MB 초과 케이스")
    void testExceed2MB() {
        // given
        CreateSpaceCommand command = new CreateSpaceCommand(1L, -1L, "공간", null, 1f, 1f, 1f, 1f, null);
        byte[] content = new byte[100 * 1024 * 1024 + 1];
        MockMultipartFile over2MB = new MockMultipartFile("image", "image.png", "image/png", content);
        assertThatThrownBy(() -> CreateSpaceCommand.from(command, null)).hasMessage(AdminErrorCode.SPACE_FILE_REQUIRED.getMessage());
        assertThatThrownBy(() -> CreateSpaceCommand.from(command,over2MB)).hasMessage(AdminErrorCode.EXCEEDED_FILE_SIZE.getMessage());
    }

    @Test
    @DisplayName("유니버스 혹은 상위 스페이스 없음")
    void noUniverseOrParentId() {
        assertThatThrownBy(() -> new CreateSpaceCommand(null, 1L, "공간", null, 1.0F, 0.5F, 0.5F, 0.5F, basicImage)).hasMessage(AdminErrorCode.ILLEGAL_ARGUMENT_EXCEPTION.getMessage());
        assertThatThrownBy(() -> new CreateSpaceCommand(1L, null, "공간", null, 1.0F, 0.5F, 0.5F, 0.5F, basicImage)).hasMessage(AdminErrorCode.ILLEGAL_ARGUMENT_EXCEPTION.getMessage());
    }
    
    @Test
    @DisplayName("스페이스 생성 요청 정상 생성")
    void happyCase() {
        new CreateSpaceCommand(1L,-1L,"공간",null,1f,1f,1f,1f,basicImage);
    }
}