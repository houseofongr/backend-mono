package com.hoo.aoo.admin.application.port.in.item;

import org.springframework.web.multipart.MultipartFile;

import java.util.Map;

public interface CreateItemUseCase {
    CreateItemResult create(Long userId, Long roomId, CreateItemMetadata metadata, Map<String, MultipartFile> fileMap);
}
