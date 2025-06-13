package com.aoo.admin.application.service.sound;

import com.aoo.admin.application.port.in.sound.UpdateSoundCommand;
import com.aoo.admin.application.port.in.sound.UpdateSoundResult;
import com.aoo.admin.application.port.in.sound.UpdateSoundUseCase;
import com.aoo.admin.application.port.out.sound.FindSoundPort;
import com.aoo.admin.application.port.out.sound.UpdateSoundPort;
import com.aoo.admin.domain.universe.piece.sound.Sound;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
@Transactional
@RequiredArgsConstructor
public class UpdateSoundService implements UpdateSoundUseCase {

    private final FindSoundPort findSoundPort;
    private final UpdateSoundPort updateSoundPort;

    @Override
    public UpdateSoundResult updateDetail(Long soundId, UpdateSoundCommand command) {
        Sound sound = findSoundPort.find(soundId);
        sound.getBasicInfo().update(command.title(), command.description());

        updateSoundPort.update(sound);

        return new UpdateSoundResult(
                String.format("[#%d]번 사운드의 상세정보가 수정되었습니다.", sound.getId()),
                sound.getBasicInfo().getTitle(),
                sound.getBasicInfo().getDescription()
        );
    }
}
