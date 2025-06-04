package com.aoo.admin.application.service.category;

import com.aoo.admin.application.port.in.category.CreateCategoryResult;
import com.aoo.admin.application.port.in.category.CreateCategoryUseCase;
import com.aoo.admin.application.port.out.category.SaveCategoryPort;
import com.aoo.admin.application.service.AdminErrorCode;
import com.aoo.admin.application.service.AdminException;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class CreateCategoryService implements CreateCategoryUseCase {

    private final SaveCategoryPort saveCategoryPort;

    @Override
    public CreateCategoryResult create(String name) {
        if (name == null || name.isBlank() || name.length() > 100)
            throw new AdminException(AdminErrorCode.ILLEGAL_ARGUMENT_EXCEPTION);

        return saveCategoryPort.save(name);
    }
}
