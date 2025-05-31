package com.aoo.admin.application.port.in.home;

public record CreateHomeResult(
        Long createdHomeId,
        String createdHomeName
) {
}
