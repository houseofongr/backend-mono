package com.aoo.admin.application.service.soundsource;

import com.aoo.admin.application.port.in.soundsource.CreateSoundSourceMetadata;
import com.aoo.admin.application.port.in.soundsource.CreateSoundSourceResult;
import com.aoo.admin.application.port.in.soundsource.CreateSoundSourceUseCase;
import com.aoo.admin.application.port.out.item.FindItemPort;
import com.aoo.admin.application.port.out.soundsource.CreateSoundSourcePort;
import com.aoo.admin.application.port.out.soundsource.SaveSoundSourcePort;
import com.aoo.admin.application.service.AdminErrorCode;
import com.aoo.admin.application.service.AdminException;
import com.aoo.admin.domain.item.Item;
import com.aoo.admin.domain.item.soundsource.SoundSource;
import com.aoo.file.application.port.in.UploadFileResult;
import com.aoo.file.application.port.in.UploadPrivateAudioUseCase;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.multipart.MultipartFile;

import java.util.List;

@Service
@RequiredArgsConstructor
public class CreateSoundSourceService implements CreateSoundSourceUseCase {

    private final FindItemPort findItemPort;
    private final CreateSoundSourcePort createSoundSourcePort;
    private final SaveSoundSourcePort saveSoundSourcePort;
    private final UploadPrivateAudioUseCase uploadPrivateAudioUseCase;

    @Override
    @Transactional
    public CreateSoundSourceResult createSoundSource(Long itemId, CreateSoundSourceMetadata metadata, MultipartFile soundFile) {

        Item item = findItemPort.loadItem(itemId)
                .orElseThrow(() -> new AdminException(AdminErrorCode.ITEM_NOT_FOUND));

        UploadFileResult uploadFileResult = uploadPrivateAudioUseCase.privateUpload(List.of(soundFile), item.getUserId().getId());

        SoundSource soundSource = createSoundSourcePort.createSoundSource(itemId, uploadFileResult.fileInfos().getFirst().id(), metadata.name(), metadata.description(), metadata.isActive());

        Long savedSoundSourceId = saveSoundSourcePort.saveSoundSource(soundSource);

        return new CreateSoundSourceResult(savedSoundSourceId);
    }
}
