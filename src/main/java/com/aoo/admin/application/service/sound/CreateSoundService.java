package com.aoo.admin.application.service.sound;

import com.aoo.admin.application.port.in.sound.CreateSoundCommand;
import com.aoo.admin.application.port.in.sound.CreateSoundResult;
import com.aoo.admin.application.port.in.sound.CreateSoundUseCase;
import com.aoo.admin.application.port.out.sound.CreateSoundPort;
import com.aoo.admin.application.port.out.sound.SaveSoundPort;
import com.aoo.admin.domain.universe.piece.sound.Sound;
import com.aoo.file.application.port.in.UploadFileResult;
import com.aoo.file.application.port.in.UploadPublicAudioUseCase;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
@Transactional
@RequiredArgsConstructor
public class CreateSoundService implements CreateSoundUseCase {

    private final UploadPublicAudioUseCase uploadPublicImageUseCase;
    private final CreateSoundPort createSoundPort;
    private final SaveSoundPort saveSoundPort;

    @Override
    public CreateSoundResult create(CreateSoundCommand command) {
        UploadFileResult.FileInfo newAudioFile = uploadPublicImageUseCase.publicUpload(command.audioFile());
        Sound newSound = createSoundPort.createSound(newAudioFile.id(), command);
        Long newSoundId = saveSoundPort.save(newSound);

        return new CreateSoundResult(
                String.format("[#%d]번 사운드가 생성되었습니다.", newSoundId),
                newSoundId,
                newSound.getBasicInfo().getPieceId(),
                newSound.getFileInfo().getAudioId(),
                newSound.getBasicInfo().getTitle(),
                newSound.getBasicInfo().getDescription()
        );
    }
}
