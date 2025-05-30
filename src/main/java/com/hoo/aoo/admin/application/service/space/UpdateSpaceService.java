package com.hoo.aoo.admin.application.service.space;

import com.hoo.aoo.admin.application.port.in.space.UpdateSpaceCommand;
import com.hoo.aoo.admin.application.port.in.space.UpdateSpaceUseCase;
import com.hoo.aoo.admin.application.port.out.space.FindSpacePort;
import com.hoo.aoo.admin.application.port.out.space.UpdateSpacePort;
import com.hoo.aoo.admin.application.service.AdminErrorCode;
import com.hoo.aoo.admin.application.service.AdminException;
import com.hoo.aoo.admin.domain.universe.space.Space;
import com.hoo.aoo.common.application.port.in.MessageDto;
import com.hoo.aoo.file.application.port.in.DeleteFileUseCase;
import com.hoo.aoo.file.application.port.in.UploadFileResult;
import com.hoo.aoo.file.application.port.in.UploadPublicImageUseCase;
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
    public MessageDto update(Long spaceId, UpdateSpaceCommand command) {
        Space space = findSpacePort.loadSingle(spaceId).orElseThrow(() -> new AdminException(AdminErrorCode.SPACE_NOT_FOUND));

        space.updateBasicInfo(command.title(), command.description());
        space.updatePosInfo(command.dx(), command.dy(), command.scaleX(), command.scaleY());

        updateSpacePort.update(space);

        return new MessageDto(space.getId() + "번 스페이스가 수정되었습니다.");
    }

    @Override
    public MessageDto updateInnerImage(Long spaceId, MultipartFile innerImage) {

        if (innerImage == null) throw new AdminException(AdminErrorCode.SPACE_FILE_REQUIRED);
        if (innerImage.getSize() >= 5 * 1024 * 1024) throw new AdminException(AdminErrorCode.EXCEEDED_FILE_SIZE);

        Space targetSpace = findSpacePort.loadSingle(spaceId).orElseThrow(() -> new AdminException(AdminErrorCode.SPACE_NOT_FOUND));

        Long beforeInnerImageId = targetSpace.getInnerImageId();
        UploadFileResult.FileInfo uploadedInnerImage = uploadPublicImageUseCase.publicUpload(innerImage);

        targetSpace.updateInnerImage(uploadedInnerImage.id());
        updateSpacePort.update(targetSpace);
        deleteFileUseCase.deleteFile(beforeInnerImageId);

        return new MessageDto(spaceId + "번 스페이스의 내부 이미지가 수정되었습니다.");
    }
}
