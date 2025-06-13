package com.aoo.admin.application.port.in.sound;

public record UpdateSoundResult(

) {
    public record Detail(
            String message,
            String title,
            String description
    ) {

    }

    public record Audio(
            String message,
            Long deletedAudioId,
            Long newAudioId
    ) {

    }
}
