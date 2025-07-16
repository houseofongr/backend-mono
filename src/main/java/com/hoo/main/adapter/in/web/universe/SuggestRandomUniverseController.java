package com.hoo.main.adapter.in.web.universe;

import com.hoo.main.application.port.in.universe.SuggestRandomUniverseCommand;
import com.hoo.main.application.port.in.universe.SuggestRandomUniverseResult;
import com.hoo.main.application.port.in.universe.SuggestRandomUniverseUseCase;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequiredArgsConstructor
public class SuggestRandomUniverseController {

    private final SuggestRandomUniverseUseCase useCase;

    @GetMapping("/universes/random/{size}")
    ResponseEntity<SuggestRandomUniverseResult> suggestRandom(
            @PathVariable Integer size,
            @RequestBody SuggestRandomUniverseCommand command
    ) {

        return ResponseEntity.ok(useCase.suggestRandomSidebarUniverse(size, command));
    }

}
