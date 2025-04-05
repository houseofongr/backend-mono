package com.hoo.aoo.admin.application.service.universe;

import com.hoo.aoo.admin.application.port.in.universe.CreateUniverseCommand;
import com.hoo.aoo.admin.application.port.in.universe.CreateUniverseUseCase;
import com.hoo.aoo.admin.application.port.out.universe.CreateUniversePort;
import com.hoo.aoo.admin.application.port.out.universe.SaveUniversePort;
import com.hoo.aoo.admin.application.service.AdminErrorCode;
import com.hoo.aoo.admin.application.service.AdminException;
import com.hoo.aoo.admin.domain.universe.Universe;
import com.hoo.aoo.common.application.port.in.MessageDto;
import com.hoo.aoo.file.application.port.in.UploadFileResult;
import com.hoo.aoo.file.application.port.in.UploadPublicAudioUseCase;
import com.hoo.aoo.file.application.port.in.UploadPublicImageUseCase;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.multipart.MultipartFile;

import java.util.List;
import java.util.Map;

@Service
@Transactional
@RequiredArgsConstructor
public class CreateUniverseService implements CreateUniverseUseCase {

    private final CreateUniversePort createUniversePort;
    private final UploadPublicImageUseCase uploadPublicImageUseCase;
    private final UploadPublicAudioUseCase uploadPublicAudioUseCase;
    private final SaveUniversePort saveUniversePort;

    @Override
    public MessageDto create(CreateUniverseCommand command, Map<String, MultipartFile> fileMap) {

        if (!verifyFileMap(fileMap)) throw new AdminException(AdminErrorCode.ILLEGAL_UNIVERSE_CREATE_FILE);
        if (!verifyCommand(command)) throw new AdminException(AdminErrorCode.ILLEGAL_UNIVERSE_PARAMETER);

        UploadFileResult thumbnail = uploadPublicImageUseCase.publicUpload(List.of(fileMap.get("thumbnail")));
        UploadFileResult thumbMusic = uploadPublicAudioUseCase.publicUpload(List.of(fileMap.get("thumbMusic")));

        Universe universe = createUniversePort.createUniverse(command, thumbnail.fileInfos().getFirst().id(), thumbMusic.fileInfos().getFirst().id());

        return new MessageDto(saveUniversePort.save(universe) + "번 유니버스가 생성되었습니다.");
    }

    private boolean verifyCommand(CreateUniverseCommand command) {
        return command.title() != null &&
               !command.title().isBlank() &&
               command.tag().size() <= 10 &&
               String.join("", command.tag()).length() <= 500;
    }

    private boolean verifyFileMap(Map<String, MultipartFile> fileMap) {
        return fileMap.containsKey("thumbnail") &&
               fileMap.containsKey("thumbMusic") &&
               fileMap.get("thumbnail").getSize() <= 2 * 1024 * 1024 &&
               fileMap.get("thumbMusic").getSize() <= 2 * 1024 * 1024;
    }
}
