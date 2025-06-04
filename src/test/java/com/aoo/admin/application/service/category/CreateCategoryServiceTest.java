package com.aoo.admin.application.service.category;

import com.aoo.admin.application.port.out.category.SaveCategoryPort;
import com.aoo.admin.application.service.AdminErrorCode;
import org.assertj.core.api.Assertions;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;

import static org.assertj.core.api.Assertions.*;
import static org.mockito.Mockito.*;

class CreateCategoryServiceTest {

    SaveCategoryPort saveCategoryPort = mock();
    CreateCategoryService sut = new CreateCategoryService(saveCategoryPort);

    @Test
    @DisplayName("입력값 검증")
    void testVerify() {
        String nullName = null;
        String empty = "";
        String blank = " ";
        String enough = "a".repeat(100);
        String tooLong = "a".repeat(101);
        sut.create(enough);
        assertThatThrownBy(() -> sut.create(nullName)).hasMessage(AdminErrorCode.ILLEGAL_ARGUMENT_EXCEPTION.getMessage());
        assertThatThrownBy(() -> sut.create(empty)).hasMessage(AdminErrorCode.ILLEGAL_ARGUMENT_EXCEPTION.getMessage());
        assertThatThrownBy(() -> sut.create(blank)).hasMessage(AdminErrorCode.ILLEGAL_ARGUMENT_EXCEPTION.getMessage());
        assertThatThrownBy(() -> sut.create(tooLong)).hasMessage(AdminErrorCode.ILLEGAL_ARGUMENT_EXCEPTION.getMessage());
    }

    @Test
    @DisplayName("카테고리 생성 서비스")
    void testCreateCategoryService() {
        String name = "새 카테고리";

        sut.create(name);

        verify(saveCategoryPort, times(1)).save(name);
    }

}