package com.aoo.admin.application.service.universe;

import com.aoo.admin.application.port.in.universe.CreateUniverseCommand;
import com.aoo.admin.application.port.in.universe.CreateUniverseUseCase;
import com.aoo.admin.application.port.out.universe.CreateUniversePort;
import com.aoo.admin.application.port.out.universe.SaveUniversePort;
import com.aoo.admin.domain.universe.Universe;
import com.aoo.common.application.port.in.MessageDto;
import com.aoo.file.application.port.in.UploadFileResult;
import com.aoo.file.application.port.in.UploadPublicAudioUseCase;
import com.aoo.file.application.port.in.UploadPublicImageUseCase;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
@Transactional
@RequiredArgsConstructor
public class CreateUniverseService implements CreateUniverseUseCase {

    private final CreateUniversePort createUniversePort;
    private final UploadPublicImageUseCase uploadPublicImageUseCase;
    private final UploadPublicAudioUseCase uploadPublicAudioUseCase;
    private final SaveUniversePort saveUniversePort;

    @Override
    public MessageDto create(CreateUniverseCommand command) {

        UploadFileResult.FileInfo thumbMusic = uploadPublicAudioUseCase.publicUpload(command.fileMap().get("thumbMusic"));
        UploadFileResult.FileInfo thumbnail = uploadPublicImageUseCase.publicUpload(command.fileMap().get("thumbnail"));
        UploadFileResult.FileInfo innerImage = uploadPublicImageUseCase.publicUpload(command.fileMap().get("innerImage"));

        Universe universe = createUniversePort.createUniverse(command, thumbMusic.id(), thumbnail.id(), innerImage.id());

        return new MessageDto(saveUniversePort.save(universe) + "번 유니버스가 생성되었습니다.");
    }

}
