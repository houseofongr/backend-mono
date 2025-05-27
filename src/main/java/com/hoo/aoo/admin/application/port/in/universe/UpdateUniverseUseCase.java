package com.hoo.aoo.admin.application.port.in.universe;

import com.hoo.aoo.common.application.port.in.MessageDto;
import org.springframework.web.multipart.MultipartFile;

public interface UpdateUniverseUseCase {
    MessageDto update(UpdateUniverseCommand command);
    MessageDto updateThumbnail(Long universeId, MultipartFile thumbnail);
}
