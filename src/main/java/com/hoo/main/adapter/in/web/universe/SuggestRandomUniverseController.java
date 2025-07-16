package com.hoo.main.adapter.in.web.universe;

import com.hoo.main.application.port.in.universe.SuggestRandomUniverseCommand;
import com.hoo.main.application.port.in.universe.SuggestRandomUniverseResult;
import com.hoo.main.application.port.in.universe.SuggestRandomUniverseUseCase;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequiredArgsConstructor
public class SuggestRandomUniverseController {

    private final SuggestRandomUniverseUseCase useCase;

    @GetMapping("/universes/random")
    ResponseEntity<SuggestRandomUniverseResult> suggestRandom(
            @RequestParam(defaultValue = "4") Integer size,
            @RequestBody(required = false) SuggestRandomUniverseCommand command
    ) {

        if (command == null) command = new SuggestRandomUniverseCommand(List.of());
        return ResponseEntity.ok(useCase.suggestRandomSidebarUniverse(size, command));
    }

}
