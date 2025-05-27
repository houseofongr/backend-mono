package com.hoo.aoo.admin.application.service.universe;

import com.hoo.aoo.admin.application.port.in.universe.SearchUniverseCommand;
import com.hoo.aoo.admin.application.port.in.universe.SearchUniverseResult;
import com.hoo.aoo.admin.application.port.in.universe.SearchUniverseUseCase;
import com.hoo.aoo.admin.application.port.out.universe.FindUniversePort;
import com.hoo.aoo.admin.application.service.AdminErrorCode;
import com.hoo.aoo.admin.application.service.AdminException;
import com.hoo.aoo.admin.domain.universe.Category;
import com.hoo.aoo.common.adapter.out.persistence.condition.UniverseSearchType;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.Arrays;

@Service
@Transactional(readOnly = true)
@RequiredArgsConstructor
public class SearchUniverseService implements SearchUniverseUseCase {

    private final FindUniversePort findUniversePort;

    @Override
    public SearchUniverseResult search(SearchUniverseCommand command) {
        if (!validCategorySearch(command)) throw new AdminException(AdminErrorCode.INVALID_SEARCH_TYPE);

        return findUniversePort.search(command);
    }

    private boolean validCategorySearch(SearchUniverseCommand command) {
        return !(command.searchType() != null &&
                 Arrays.stream(Category.values())
                         .noneMatch(e -> e.name().equalsIgnoreCase(command.keyword()))
        );
    }
}
