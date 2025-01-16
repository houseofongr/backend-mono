package com.hoo.aoo.admin.application.port.in;

public record QueryRoomCommand(
        Long houseId,
        String roomName
) {
}
