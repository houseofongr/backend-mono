package com.aoo.admin.application.service.space;

import com.aoo.admin.application.port.in.space.CreateSpaceCommand;
import com.aoo.admin.application.port.in.space.CreateSpaceResult;
import com.aoo.admin.application.port.in.space.CreateSpaceUseCase;
import com.aoo.admin.application.port.out.space.CreateSpacePort;
import com.aoo.admin.application.port.out.space.FindSpacePort;
import com.aoo.admin.application.port.out.space.SaveSpacePort;
import com.aoo.admin.application.service.AdminErrorCode;
import com.aoo.admin.application.service.AdminException;
import com.aoo.admin.domain.universe.space.Space;
import com.aoo.common.application.port.in.MessageDto;
import com.aoo.file.application.port.in.UploadFileResult;
import com.aoo.file.application.port.in.UploadPublicImageUseCase;
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
    public CreateSpaceResult create(CreateSpaceCommand command) {
        Space parentSpace = command.parentSpaceId() == -1? null :
                findSpacePort.loadSingle(command.parentSpaceId()).orElseThrow(() -> new AdminException(AdminErrorCode.SPACE_NOT_FOUND));

        UploadFileResult uploadFileResult = uploadPublicImageUseCase.publicUpload(List.of(command.imageFile()));

        Space space = createSpacePort.createSpace(command, parentSpace, uploadFileResult.fileInfos().getFirst().id());
        return saveSpacePort.save(command.universeId(), space);
    }
}
