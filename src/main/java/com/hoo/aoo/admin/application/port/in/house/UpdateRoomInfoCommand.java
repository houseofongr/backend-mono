package com.hoo.aoo.admin.application.port.in.house;

public record UpdateRoomInfoCommand(
        Long persistenceId,
        String originalName,
        String newName
) {
}
