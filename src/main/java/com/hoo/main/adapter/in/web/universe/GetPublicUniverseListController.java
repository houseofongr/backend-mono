package com.hoo.main.adapter.in.web.universe;

import com.hoo.main.adapter.in.web.authn.security.Jwt;
import com.hoo.main.application.port.in.universe.SearchPublicUniverseCommand;
import com.hoo.main.application.port.in.universe.SearchPublicUniverseResult;
import com.hoo.main.application.port.in.universe.SearchPublicUniverseUseCase;
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

    @GetMapping("/universes")
    ResponseEntity<SearchPublicUniverseResult> search(
            Pageable pageable,
            @Jwt(value = "userId", required = false) Long userId,
            @RequestParam(required = false) String searchType,
            @RequestParam(required = false) String keyword,
            @RequestParam(required = false) String sortType,
            @RequestParam(required = false) Boolean isAsc
    ) {
        return ResponseEntity.ok(useCase.search(new SearchPublicUniverseCommand(pageable, userId, searchType, keyword, sortType, isAsc)));
    }
}
