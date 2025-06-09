package com.aoo.admin.application.service.universe;

import com.aoo.admin.application.port.in.universe.UpdateUniverseCommand;
import com.aoo.admin.application.port.in.universe.UpdateUniverseResult;
import com.aoo.admin.application.port.in.universe.UpdateUniverseUseCase;
import com.aoo.admin.application.port.out.universe.FindUniversePort;
import com.aoo.admin.application.port.out.universe.UpdateUniversePort;
import com.aoo.admin.application.service.AdminErrorCode;
import com.aoo.admin.application.service.AdminException;
import com.aoo.admin.domain.universe.Universe;
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
    public UpdateUniverseResult.Detail updateDetail(Long universeId, UpdateUniverseCommand command) {

        Universe targetUniverse = findUniversePort.load(universeId);

        targetUniverse.updateBasicInfo(command.title(), command.description(), command.authorId(), command.category(), command.publicStatus());
        targetUniverse.updateSocialInfo(command.hashtags());

        return updateUniversePort.updateDetail(targetUniverse);
    }

    @Override
    public UpdateUniverseResult.ThumbMusic updateThumbMusic(Long universeId, MultipartFile thumbMusic) {

        if (thumbMusic == null) throw new AdminException(AdminErrorCode.UNIVERSE_FILE_REQUIRED);
        if (thumbMusic.getSize() > 2 * 1024 * 1024) throw new AdminException(AdminErrorCode.EXCEEDED_FILE_SIZE);

        Universe targetUniverse = findUniversePort.load(universeId);

        Long beforeThumbMusicId = targetUniverse.getFileInfo().getThumbMusicId();
        UploadFileResult.FileInfo uploadedThumbMusic = uploadPublicAudioUseCase.publicUpload(thumbMusic);

        targetUniverse.updateThumbMusic(uploadedThumbMusic.id());

        updateUniversePort.updateThumbMusic(targetUniverse);
        deleteFileUseCase.deleteFile(beforeThumbMusicId);

        return UpdateUniverseResult.ThumbMusic.of(targetUniverse.getId(), beforeThumbMusicId, uploadedThumbMusic.id());
    }

    @Override
    public UpdateUniverseResult.Thumbnail updateThumbnail(Long universeId, MultipartFile thumbnail) {

        if (thumbnail == null) throw new AdminException(AdminErrorCode.UNIVERSE_FILE_REQUIRED);
        if (thumbnail.getSize() > 2 * 1024 * 1024) throw new AdminException(AdminErrorCode.EXCEEDED_FILE_SIZE);

        Universe targetUniverse = findUniversePort.load(universeId);

        Long beforeThumbnailId = targetUniverse.getFileInfo().getThumbnailId();
        UploadFileResult.FileInfo uploadedThumbnail = uploadPublicImageUseCase.publicUpload(thumbnail);

        targetUniverse.updateThumbnail(uploadedThumbnail.id());

        updateUniversePort.updateThumbnail(targetUniverse);
        deleteFileUseCase.deleteFile(beforeThumbnailId);

        return UpdateUniverseResult.Thumbnail.of(targetUniverse.getId(), beforeThumbnailId, uploadedThumbnail.id());
    }

    @Override
    public UpdateUniverseResult.InnerImage updateInnerImage(Long universeId, MultipartFile innerImage) {

        if (innerImage == null) throw new AdminException(AdminErrorCode.UNIVERSE_FILE_REQUIRED);
        if (innerImage.getSize() > 100 * 1024 * 1024) throw new AdminException(AdminErrorCode.EXCEEDED_FILE_SIZE);

        Universe targetUniverse = findUniversePort.load(universeId);

        Long beforeInnerImageId = targetUniverse.getFileInfo().getInnerImageId();
        UploadFileResult.FileInfo uploadedInnerImage = uploadPublicImageUseCase.publicUpload(innerImage);

        targetUniverse.updateInnerImage(uploadedInnerImage.id());

        updateUniversePort.updateInnerImage(targetUniverse);
        deleteFileUseCase.deleteFile(beforeInnerImageId);

        return UpdateUniverseResult.InnerImage.of(targetUniverse.getId(), beforeInnerImageId, uploadedInnerImage.id());
    }
}
