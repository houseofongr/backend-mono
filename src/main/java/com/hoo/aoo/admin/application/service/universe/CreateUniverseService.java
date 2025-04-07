package com.hoo.aoo.admin.application.service.universe;

import com.hoo.aoo.admin.application.port.in.universe.CreateUniverseCommand;
import com.hoo.aoo.admin.application.port.in.universe.CreateUniverseUseCase;
import com.hoo.aoo.admin.application.port.out.universe.CreateUniversePort;
import com.hoo.aoo.admin.application.port.out.universe.SaveUniversePort;
import com.hoo.aoo.admin.domain.universe.Universe;
import com.hoo.aoo.common.application.port.in.MessageDto;
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
public class CreateUniverseService implements CreateUniverseUseCase {

    private final CreateUniversePort createUniversePort;
    private final UploadPublicImageUseCase uploadPublicImageUseCase;
    private final UploadPublicAudioUseCase uploadPublicAudioUseCase;
    private final SaveUniversePort saveUniversePort;

    @Override
    public MessageDto create(CreateUniverseCommand command) {

        UploadFileResult thumbnail = uploadPublicImageUseCase.publicUpload(List.of(command.fileMap().get("thumbnail")));
        UploadFileResult thumbMusic = uploadPublicAudioUseCase.publicUpload(List.of(command.fileMap().get("thumbMusic")));

        Universe universe = createUniversePort.createUniverse(command, thumbnail.fileInfos().getFirst().id(), thumbMusic.fileInfos().getFirst().id());

        return new MessageDto(saveUniversePort.save(universe) + "번 유니버스가 생성되었습니다.");
    }

}
