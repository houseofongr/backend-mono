package com.aoo.admin.application.service.category;

import com.aoo.admin.application.port.in.category.UpdateCategoryResult;
import com.aoo.admin.application.port.in.category.UpdateCategoryUseCase;
import com.aoo.admin.application.port.out.category.UpdateCategoryPort;
import com.aoo.admin.application.service.AdminErrorCode;
import com.aoo.admin.application.service.AdminException;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class UpdateCategoryService implements UpdateCategoryUseCase {

    private final UpdateCategoryPort updateCategoryPort;

    @Override
    public UpdateCategoryResult update(Long categoryId, String name) {

        if (name == null || name.isBlank() || name.length() > 100)
            throw new AdminException(AdminErrorCode.ILLEGAL_ARGUMENT_EXCEPTION);

        return updateCategoryPort.update(categoryId, name);
    }
}
