package com.aoo.admin.application.service.category;

import com.aoo.admin.application.port.out.category.DeleteCategoryPort;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;

import static org.mockito.Mockito.*;

class DeleteCategoryServiceTest {

    DeleteCategoryPort deleteCategoryPort = mock();
    DeleteCategoryService sut = new DeleteCategoryService(deleteCategoryPort);

    @Test
    @DisplayName("카테고리 삭제 서비스")
    void testDeleteCategoryService() {
        // given
        Long id = 1L;

        // when
        sut.delete(id);

        // then
        verify(deleteCategoryPort, times(1)).delete(1L);
    }

}