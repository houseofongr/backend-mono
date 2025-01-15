package com.hoo.aoo.admin.application.port.in;

import com.hoo.aoo.admin.application.service.Metadata;
import com.hoo.aoo.common.adapter.in.web.MessageDto;
import org.springframework.web.multipart.MultipartFile;

import java.util.Map;

public interface UpdateHouseUseCase {
    MessageDto update(Metadata metadata, Map<String, MultipartFile> fileMap);
}
