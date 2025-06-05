package com.aoo.admin.application.service.category;

import com.aoo.admin.application.port.in.category.SearchCategoryResult;
import com.aoo.admin.application.port.in.category.SearchCategoryUseCase;
import com.aoo.admin.application.port.out.category.FindCategoryPort;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class SearchCategoryService implements SearchCategoryUseCase {

    private final FindCategoryPort findCategoryPort;

    @Override
    public SearchCategoryResult search() {
        return findCategoryPort.findAll();
    }
}
