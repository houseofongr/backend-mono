package com.aoo.admin.application.service.space;

import com.aoo.admin.application.port.in.space.UpdateSpaceCommand;
import com.aoo.admin.application.port.in.space.UpdateSpaceResult;
import com.aoo.admin.application.port.in.space.UpdateSpaceUseCase;
import com.aoo.admin.application.port.out.space.FindSpacePort;
import com.aoo.admin.application.port.out.space.UpdateSpacePort;
import com.aoo.admin.application.service.AdminErrorCode;
import com.aoo.admin.application.service.AdminException;
import com.aoo.admin.domain.universe.space.Space;
import com.aoo.common.application.port.in.MessageDto;
import com.aoo.file.application.port.in.DeleteFileUseCase;
import com.aoo.file.application.port.in.UploadFileResult;
import com.aoo.file.application.port.in.UploadPublicImageUseCase;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.multipart.MultipartFile;

@Service
@Transactional
@RequiredArgsConstructor
public class UpdateSpaceService implements UpdateSpaceUseCase {

    private final FindSpacePort findSpacePort;
    private final UploadPublicImageUseCase uploadPublicImageUseCase;
    private final DeleteFileUseCase deleteFileUseCase;
    private final UpdateSpacePort updateSpacePort;

    @Override
    public UpdateSpaceResult.Detail updateDetail(Long spaceId, UpdateSpaceCommand command) {
        Space space = findSpacePort.loadSingle(spaceId).orElseThrow(() -> new AdminException(AdminErrorCode.SPACE_NOT_FOUND));

        space.updateBasicInfo(command.title(), command.description());
        space.updatePosInfo(command.dx(), command.dy(), command.scaleX(), command.scaleY());

        return updateSpacePort.update(space);
    }

    @Override
    public UpdateSpaceResult.InnerImage updateInnerImage(Long spaceId, MultipartFile innerImage) {

        if (innerImage == null) throw new AdminException(AdminErrorCode.SPACE_FILE_REQUIRED);
        if (innerImage.getSize() >= 5 * 1024 * 1024) throw new AdminException(AdminErrorCode.EXCEEDED_FILE_SIZE);

        Space targetSpace = findSpacePort.loadSingle(spaceId).orElseThrow(() -> new AdminException(AdminErrorCode.SPACE_NOT_FOUND));

        Long beforeInnerImageId = targetSpace.getFileInfo().getInnerImageId();
        UploadFileResult.FileInfo uploadedInnerImage = uploadPublicImageUseCase.publicUpload(innerImage);

        targetSpace.updateInnerImage(uploadedInnerImage.id());
        updateSpacePort.update(targetSpace);
        deleteFileUseCase.deleteFile(beforeInnerImageId);

        return UpdateSpaceResult.InnerImage.of(targetSpace.getId(), beforeInnerImageId, uploadedInnerImage.id());
    }
}
