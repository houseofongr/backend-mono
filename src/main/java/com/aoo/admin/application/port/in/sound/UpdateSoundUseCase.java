package com.aoo.admin.application.port.in.sound;

public interface UpdateSoundUseCase {
    UpdateSoundResult updateDetail(Long soundId, UpdateSoundCommand command);
}
