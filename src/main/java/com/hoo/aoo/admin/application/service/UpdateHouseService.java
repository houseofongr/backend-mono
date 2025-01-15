package com.hoo.aoo.admin.application.service;

import com.hoo.aoo.admin.application.port.in.UpdateHouseUseCase;
import com.hoo.aoo.common.adapter.in.web.MessageDto;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

import java.util.Map;

@Service
public class UpdateHouseService implements UpdateHouseUseCase {

    @Override
    public MessageDto update(Metadata metadata, Map<String, MultipartFile> fileMap) {
        return null;
    }
}
