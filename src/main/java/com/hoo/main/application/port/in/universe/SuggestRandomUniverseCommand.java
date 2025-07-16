package com.hoo.main.application.port.in.universe;

import java.util.List;

public record SuggestRandomUniverseCommand(
        List<Long> exceptIds
) {
}
