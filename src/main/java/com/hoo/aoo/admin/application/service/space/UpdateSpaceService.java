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

import java.util.List;

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
}
