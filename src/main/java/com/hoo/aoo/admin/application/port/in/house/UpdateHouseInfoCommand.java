package com.hoo.aoo.admin.application.port.in.house;

public record UpdateHouseInfoCommand(
        Long persistenceId,
        String title,
        String author,
        String description
) {
}
