package com.hoo.aoo.admin.application.port.in;

public record UpdateHouseCommand(
        Long persistenceId,
        String title,
        String author,
        String description
) {
}
