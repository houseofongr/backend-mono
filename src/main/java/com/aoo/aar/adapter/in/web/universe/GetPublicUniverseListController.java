package com.aoo.aar.adapter.in.web.universe;

import com.aoo.aar.adapter.in.web.authn.security.Jwt;
import com.aoo.aar.application.port.in.universe.SearchPublicUniverseCommand;
import com.aoo.aar.application.port.in.universe.SearchPublicUniverseResult;
import com.aoo.aar.application.port.in.universe.SearchPublicUniverseUseCase;
import com.aoo.admin.application.port.in.universe.SearchUniverseCommand;
import jakarta.validation.constraints.NotNull;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Pageable;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequiredArgsConstructor
public class GetPublicUniverseListController {

    private final SearchPublicUniverseUseCase useCase;

    @GetMapping("/aar/universes")
    ResponseEntity<SearchPublicUniverseResult> search(
            Pageable pageable,
            @Jwt("userId") Long userId,
            @RequestParam(required = false) String searchType,
            @RequestParam(required = false) String keyword,
            @RequestParam(required = false) String sortType,
            @RequestParam(required = false) Boolean isAsc
    ) {
        return ResponseEntity.ok(useCase.search(new SearchPublicUniverseCommand(pageable, userId, searchType, keyword, sortType, isAsc)));
    }
}
