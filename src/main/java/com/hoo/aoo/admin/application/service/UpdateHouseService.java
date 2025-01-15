package com.hoo.aoo.admin.application.service;

import com.hoo.aoo.admin.application.port.in.UpdateHouseUseCase;
import com.hoo.aoo.admin.application.port.out.LoadHousePort;
import com.hoo.aoo.admin.application.port.out.database.UpdateHousePort;
import com.hoo.aoo.admin.domain.exception.AreaLimitExceededException;
import com.hoo.aoo.admin.domain.house.House;
import com.hoo.aoo.common.adapter.in.web.MessageDto;
import com.hoo.aoo.file.application.port.in.UploadPrivateImageUseCase;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

import java.util.Map;

@Service
@RequiredArgsConstructor
public class UpdateHouseService implements UpdateHouseUseCase {

    private final LoadHousePort loadHousePort;
    private final UpdateHousePort updateHousePort;
    private final UploadPrivateImageUseCase uploadPrivateImageUseCase;

    @Override
    public MessageDto update(Long houseId, Metadata metadata, Map<String, MultipartFile> fileMap) {

        try {
            House houseInDB = loadHousePort.load(houseId);

            houseInDB.update(metadata.house().title(), metadata.house().author(), metadata.house().description(), metadata.house().width(), metadata.house().height());

            for (Metadata.Room roomData : metadata.rooms()) {

            }

            return null;

        } catch (AreaLimitExceededException e) {
            throw new RuntimeException(e);
        }
    }
}
