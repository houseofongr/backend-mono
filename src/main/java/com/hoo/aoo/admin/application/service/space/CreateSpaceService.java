package com.hoo.aoo.admin.application.service.space;

import com.hoo.aoo.admin.application.port.in.space.CreateSpaceCommand;
import com.hoo.aoo.admin.application.port.in.space.CreateSpaceUseCase;
import com.hoo.aoo.admin.application.port.out.space.CreateSpacePort;
import com.hoo.aoo.admin.application.port.out.space.FindSpacePort;
import com.hoo.aoo.admin.application.port.out.space.SaveSpacePort;
import com.hoo.aoo.admin.application.service.AdminErrorCode;
import com.hoo.aoo.admin.application.service.AdminException;
import com.hoo.aoo.admin.domain.universe.space.Space;
import com.hoo.aoo.common.application.port.in.MessageDto;
import com.hoo.aoo.file.application.port.in.UploadFileResult;
import com.hoo.aoo.file.application.port.in.UploadPublicImageUseCase;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

@Service
@Transactional
@RequiredArgsConstructor
public class CreateSpaceService implements CreateSpaceUseCase {

    private final FindSpacePort findSpacePort;
    private final UploadPublicImageUseCase uploadPublicImageUseCase;
    private final CreateSpacePort createSpacePort;
    private final SaveSpacePort saveSpacePort;

    @Override
    public MessageDto create(CreateSpaceCommand command) {
        Space parentSpace = command.parentSpaceId() == -1? null :
                findSpacePort.loadSingle(command.parentSpaceId()).orElseThrow(() -> new AdminException(AdminErrorCode.SPACE_NOT_FOUND));

        UploadFileResult uploadFileResult = uploadPublicImageUseCase.publicUpload(List.of(command.imageFile()));

        Space space = createSpacePort.createSpace(command, parentSpace, uploadFileResult.fileInfos().getFirst().id());
        Long savedId = saveSpacePort.save(space, command.universeId());

        return new MessageDto(savedId + "번 스페이스가 생성되었습니다.");
    }
}
