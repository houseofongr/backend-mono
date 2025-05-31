package com.aoo.admin.application.service.universe;

import com.aoo.admin.application.port.in.universe.UpdateUniverseCommand;
import com.aoo.admin.application.port.in.universe.UpdateUniverseUseCase;
import com.aoo.admin.application.port.out.universe.FindUniversePort;
import com.aoo.admin.application.port.out.universe.UpdateUniversePort;
import com.aoo.admin.application.service.AdminErrorCode;
import com.aoo.admin.application.service.AdminException;
import com.aoo.admin.domain.universe.Universe;
import com.aoo.common.application.port.in.MessageDto;
import com.aoo.file.application.port.in.DeleteFileUseCase;
import com.aoo.file.application.port.in.UploadFileResult;
import com.aoo.file.application.port.in.UploadPublicAudioUseCase;
import com.aoo.file.application.port.in.UploadPublicImageUseCase;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.multipart.MultipartFile;

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
    public MessageDto update(Long universeId, UpdateUniverseCommand command) {

        Universe targetUniverse = findUniversePort.load(universeId).orElseThrow(() -> new AdminException(AdminErrorCode.UNIVERSE_NOT_FOUND));

        targetUniverse.updateBasicInfo(command.title(), command.description(), command.category(), command.publicStatus());
        targetUniverse.updateSocialInfo(command.hashtags());

        updateUniversePort.update(targetUniverse);

        return new MessageDto(universeId + "번 유니버스가 수정되었습니다.");
    }

    @Override
    public MessageDto updateThumbnail(Long universeId, MultipartFile thumbnail) {

        if (thumbnail == null) throw new AdminException(AdminErrorCode.UNIVERSE_FILE_REQUIRED);
        if (thumbnail.getSize() >= 2 * 1024 * 1024) throw new AdminException(AdminErrorCode.EXCEEDED_FILE_SIZE);

        Universe targetUniverse = findUniversePort.load(universeId).orElseThrow(() -> new AdminException(AdminErrorCode.UNIVERSE_NOT_FOUND));

        Long beforeThumbnailId = targetUniverse.getThumbnailId();
        UploadFileResult.FileInfo uploadedThumbnail = uploadPublicImageUseCase.publicUpload(thumbnail);

        targetUniverse.updateThumbnail(uploadedThumbnail.id());
        updateUniversePort.update(targetUniverse);
        deleteFileUseCase.deleteFile(beforeThumbnailId);

        return new MessageDto(universeId + "번 유니버스의 썸네일이 수정되었습니다.");
    }

    @Override
    public MessageDto updateThumbMusic(Long universeId, MultipartFile thumbMusic) {

        if (thumbMusic == null) throw new AdminException(AdminErrorCode.UNIVERSE_FILE_REQUIRED);
        if (thumbMusic.getSize() >= 2 * 1024 * 1024) throw new AdminException(AdminErrorCode.EXCEEDED_FILE_SIZE);

        Universe targetUniverse = findUniversePort.load(universeId).orElseThrow(() -> new AdminException(AdminErrorCode.UNIVERSE_NOT_FOUND));

        Long beforeThumbMusicId = targetUniverse.getThumbMusicId();
        UploadFileResult.FileInfo uploadedThumbMusic = uploadPublicAudioUseCase.publicUpload(thumbMusic);

        targetUniverse.updateThumbMusic(uploadedThumbMusic.id());
        updateUniversePort.update(targetUniverse);
        deleteFileUseCase.deleteFile(beforeThumbMusicId);

        return new MessageDto(universeId + "번 유니버스의 썸뮤직이 수정되었습니다.");
    }

    @Override
    public MessageDto updateInnerImage(Long universeId, MultipartFile innerImage) {

        if (innerImage == null) throw new AdminException(AdminErrorCode.UNIVERSE_FILE_REQUIRED);
        if (innerImage.getSize() >= 5 * 1024 * 1024) throw new AdminException(AdminErrorCode.EXCEEDED_FILE_SIZE);

        Universe targetUniverse = findUniversePort.load(universeId).orElseThrow(() -> new AdminException(AdminErrorCode.UNIVERSE_NOT_FOUND));

        Long beforeInnerImageId = targetUniverse.getInnerImageId();
        UploadFileResult.FileInfo uploadedInnerImage = uploadPublicImageUseCase.publicUpload(innerImage);

        targetUniverse.updateInnerImage(uploadedInnerImage.id());
        updateUniversePort.update(targetUniverse);
        deleteFileUseCase.deleteFile(beforeInnerImageId);

        return new MessageDto(universeId + "번 유니버스의 내부 이미지가 수정되었습니다.");
    }
}
