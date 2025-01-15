package com.hoo.aoo.admin.application.port.in;

public record UpdateHouseInfoCommand(
        Long persistenceId,
        String title,
        String author,
        String description
) {
}
