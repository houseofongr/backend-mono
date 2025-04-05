package com.hoo.aoo.admin.adapter.in.web.universe;

import com.hoo.aoo.admin.application.port.in.universe.SearchUniverseCommand;
import com.hoo.aoo.admin.application.port.in.universe.SearchUniverseResult;
import com.hoo.aoo.admin.application.port.in.universe.SearchUniverseUseCase;
import com.hoo.aoo.common.adapter.out.persistence.condition.UniverseSearchType;
import com.hoo.aoo.common.adapter.out.persistence.condition.UniverseSortType;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Pageable;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequiredArgsConstructor
public class GetUniverseController {

    private final SearchUniverseUseCase useCase;

    @GetMapping("/admin/universes")
    public ResponseEntity<SearchUniverseResult> search(
            Pageable pageable,
            @RequestParam(required = false) UniverseSortType sortType,
            @RequestParam(required = false) Boolean isAsc,
            @RequestParam(required = false) UniverseSearchType searchType,
            @RequestParam(required = false) String keyword
    ) {
        return ResponseEntity.ok(useCase.search(new SearchUniverseCommand(pageable, sortType, isAsc, searchType, keyword)));
    }
}
