package com.hoo.aoo.admin.application.service.universe;

import com.hoo.aoo.admin.application.port.in.universe.UpdateUniverseCommand;
import com.hoo.aoo.admin.application.port.in.universe.UpdateUniverseUseCase;
import com.hoo.aoo.admin.application.port.out.universe.FindUniversePort;
import com.hoo.aoo.admin.application.port.out.universe.UpdateUniversePort;
import com.hoo.aoo.admin.application.service.AdminErrorCode;
import com.hoo.aoo.admin.application.service.AdminException;
import com.hoo.aoo.admin.domain.universe.Universe;
import com.hoo.aoo.common.application.port.in.MessageDto;
import com.hoo.aoo.file.application.port.in.DeleteFileUseCase;
import com.hoo.aoo.file.application.port.in.UploadFileResult;
import com.hoo.aoo.file.application.port.in.UploadPublicAudioUseCase;
import com.hoo.aoo.file.application.port.in.UploadPublicImageUseCase;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

@Service
@Transactional
@RequiredArgsConstructor
public class UpdateUniverseService implements UpdateUniverseUseCase {

    private final FindUniversePort findUniversePort;
    private final UploadPublicImageUseCase uploadPublicImageUseCase;
    private final UploadPublicAudioUseCase uploadPublicAudioUseCase;
    private final DeleteFileUseCase deleteFileUseCase;
    private final UpdateUniversePort updateUniversePort;

    @Override
    public MessageDto update(UpdateUniverseCommand command) {
        Universe targetUniverse = findUniversePort.load(command.targetId())
                .orElseThrow(() -> new AdminException(AdminErrorCode.UNIVERSE_NOT_FOUND));

        targetUniverse.updateBasicInfo(command.title(), command.description(), command.category(), command.publicStatus());
        targetUniverse.updateSocialInfo(command.tags());

        if (command.fileMap().containsKey("thumbnail")) {
            UploadFileResult thumbnail = uploadPublicImageUseCase.publicUpload(List.of(command.fileMap().get("thumbnail")));
            deleteFileUseCase.deleteFile(targetUniverse.getThumbnailId());
            targetUniverse.updateThumbnail(thumbnail.fileInfos().getFirst().id());
        }

        if (command.fileMap().containsKey("thumbMusic")) {
            UploadFileResult thumbMusic = uploadPublicAudioUseCase.publicUpload(List.of(command.fileMap().get("thumbMusic")));
            deleteFileUseCase.deleteFile(targetUniverse.getThumbMusicId());
            targetUniverse.updateThumbMusic( thumbMusic.fileInfos().getFirst().id());
        }

        updateUniversePort.update(targetUniverse);

        return new MessageDto(command.targetId() + "번 유니버스가 수정되었습니다.");
    }
}
