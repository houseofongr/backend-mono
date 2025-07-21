package com.hoo.main.application.port.in.universe;

import java.util.Arrays;
import java.util.List;

public record SuggestRandomUniverseCommand(
        Integer size,
        List<Long> exceptIds
) {

    public static SuggestRandomUniverseCommand convertToCommand(Integer size, String exceptIds) {

        if (exceptIds == null || exceptIds.isBlank()) {
            return new SuggestRandomUniverseCommand(size, List.of());
        }

        return new SuggestRandomUniverseCommand(size,
                Arrays.stream(exceptIds.split(","))
                        .map(String::trim)
                        .map(Long::valueOf)
                        .toList()
        );
    }
}
