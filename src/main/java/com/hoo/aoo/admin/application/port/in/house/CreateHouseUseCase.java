package com.hoo.aoo.admin.application.port.in.house;

import com.hoo.aoo.admin.application.service.Metadata;
import org.springframework.web.multipart.MultipartFile;

import java.util.Map;

public interface CreateHouseUseCase {
    HouseIdResult create(Metadata metadata, Map<String, MultipartFile> fileMap);
}
