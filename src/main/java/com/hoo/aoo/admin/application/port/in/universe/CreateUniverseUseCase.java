package com.hoo.aoo.admin.application.port.in.universe;

import com.hoo.aoo.common.application.port.in.MessageDto;
import org.springframework.web.multipart.MultipartFile;

import java.util.Map;

public interface CreateUniverseUseCase {
    MessageDto create(CreateUniverseCommand command, Map<String, MultipartFile> fileMap);
}
