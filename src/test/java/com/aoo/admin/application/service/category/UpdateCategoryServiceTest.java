package com.aoo.admin.application.service.category;

import com.aoo.admin.application.port.out.category.UpdateCategoryPort;
import com.aoo.admin.application.service.AdminErrorCode;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;

import static org.assertj.core.api.Assertions.assertThatThrownBy;
import static org.mockito.Mockito.*;

class UpdateCategoryServiceTest {

    UpdateCategoryPort updateCategoryPort = mock();
    UpdateCategoryService sut = new UpdateCategoryService(updateCategoryPort);

    @Test
    @DisplayName("입력값 검증")
    void testVerify() {
        String nullName = null;
        String empty = "";
        String blank = " ";
        String enough = "a".repeat(100);
        String tooLong = "a".repeat(101);
        sut.update(1L, enough);
        assertThatThrownBy(() -> sut.update(1L, nullName)).hasMessage(AdminErrorCode.ILLEGAL_ARGUMENT_EXCEPTION.getMessage());
        assertThatThrownBy(() -> sut.update(1L, empty)).hasMessage(AdminErrorCode.ILLEGAL_ARGUMENT_EXCEPTION.getMessage());
        assertThatThrownBy(() -> sut.update(1L, blank)).hasMessage(AdminErrorCode.ILLEGAL_ARGUMENT_EXCEPTION.getMessage());
        assertThatThrownBy(() -> sut.update(1L, tooLong)).hasMessage(AdminErrorCode.ILLEGAL_ARGUMENT_EXCEPTION.getMessage());
    }

    @Test
    @DisplayName("카테고리 업데이트 서비스")
    void testUpdateCategoryService() {
        sut.update(1L, "업데이트");
        verify(updateCategoryPort, times(1)).update(1L, "업데이트");
    }

}