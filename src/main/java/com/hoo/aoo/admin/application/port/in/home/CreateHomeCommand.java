package com.hoo.aoo.admin.application.port.in.home;

public record CreateHomeCommand(
        Long userId,
        Long houseId
) {
}
