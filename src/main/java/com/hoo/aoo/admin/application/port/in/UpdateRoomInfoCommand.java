package com.hoo.aoo.admin.application.port.in;

public record UpdateRoomInfoCommand(
        Long persistenceId,
        String originalName,
        String newName
) {
}
