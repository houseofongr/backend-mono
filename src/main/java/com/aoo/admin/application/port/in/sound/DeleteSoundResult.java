package com.aoo.admin.application.port.in.sound;

public record DeleteSoundResult(
        String message,
        Long deletedAudioId
) {
}
