package com.hoo.aoo.admin.application.port.in.soundsource;

public record UpdateSoundSourceCommand(
        String name,
        String description,
        Boolean isActive
) {
}
