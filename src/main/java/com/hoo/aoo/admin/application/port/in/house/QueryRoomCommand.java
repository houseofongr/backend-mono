package com.hoo.aoo.admin.application.port.in.house;

public record QueryRoomCommand(
        Long houseId,
        String roomName
) {
}
