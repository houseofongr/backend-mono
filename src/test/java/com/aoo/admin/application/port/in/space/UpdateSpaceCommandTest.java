package com.aoo.admin.application.port.in.space;

import com.aoo.admin.application.port.in.space.UpdateSpaceCommand;
import com.aoo.admin.application.service.AdminErrorCode;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;

import static org.assertj.core.api.Assertions.assertThatThrownBy;

class UpdateSpaceCommandTest {
    
    @Test
    @DisplayName("제목(100자/NB) 조건 확인")
    void testTitleCondition() {
        String over100 = "a".repeat(101);
        assertThatThrownBy(() -> new UpdateSpaceCommand.Detail( "", null)).hasMessage(AdminErrorCode.ILLEGAL_ARGUMENT_EXCEPTION.getMessage());
        assertThatThrownBy(() -> new UpdateSpaceCommand.Detail(" ", null)).hasMessage(AdminErrorCode.ILLEGAL_ARGUMENT_EXCEPTION.getMessage());
        assertThatThrownBy(() -> new UpdateSpaceCommand.Detail( over100, null)).hasMessage(AdminErrorCode.ILLEGAL_ARGUMENT_EXCEPTION.getMessage());
    }
    
    @Test
    @DisplayName("내용(5000자) 조건 확인")
    void testDescriptionCondition() {
        String over5000 = "a".repeat(5001);
        assertThatThrownBy(() -> new UpdateSpaceCommand.Detail("블랙홀", over5000)).hasMessage(AdminErrorCode.ILLEGAL_ARGUMENT_EXCEPTION.getMessage());
    }
    
    @Test
    @DisplayName("위치정보(0 ≤ posInfo ≤ 1) 조건 확인")
    void testPosCondition() {
        assertThatThrownBy(() -> new UpdateSpaceCommand.Position( -1F, null, null, null)).hasMessage(AdminErrorCode.ILLEGAL_ARGUMENT_EXCEPTION.getMessage());
        assertThatThrownBy(() -> new UpdateSpaceCommand.Position( 1.1F, null, null, null)).hasMessage(AdminErrorCode.ILLEGAL_ARGUMENT_EXCEPTION.getMessage());
        assertThatThrownBy(() -> new UpdateSpaceCommand.Position( null, -1F, null, null)).hasMessage(AdminErrorCode.ILLEGAL_ARGUMENT_EXCEPTION.getMessage());
        assertThatThrownBy(() -> new UpdateSpaceCommand.Position( null, 1.1F, null, null)).hasMessage(AdminErrorCode.ILLEGAL_ARGUMENT_EXCEPTION.getMessage());
        assertThatThrownBy(() -> new UpdateSpaceCommand.Position( null, null, -1F, null)).hasMessage(AdminErrorCode.ILLEGAL_ARGUMENT_EXCEPTION.getMessage());
        assertThatThrownBy(() -> new UpdateSpaceCommand.Position( null, null, 1.1F, null)).hasMessage(AdminErrorCode.ILLEGAL_ARGUMENT_EXCEPTION.getMessage());
        assertThatThrownBy(() -> new UpdateSpaceCommand.Position( null, null, null, -1F)).hasMessage(AdminErrorCode.ILLEGAL_ARGUMENT_EXCEPTION.getMessage());
        assertThatThrownBy(() -> new UpdateSpaceCommand.Position( null, null, null, 1.1F)).hasMessage(AdminErrorCode.ILLEGAL_ARGUMENT_EXCEPTION.getMessage());
    }
}