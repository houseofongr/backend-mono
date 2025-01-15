package com.hoo.aoo.admin.application.service;

import com.hoo.aoo.admin.application.port.in.UpdateHouseUseCase;
import com.hoo.aoo.admin.application.port.out.LoadHousePort;
import com.hoo.aoo.admin.application.port.out.database.UpdateHousePort;
import com.hoo.aoo.admin.domain.exception.AreaLimitExceededException;
import com.hoo.aoo.admin.domain.exception.AxisLimitExceededException;
import com.hoo.aoo.admin.domain.house.House;
import com.hoo.aoo.admin.domain.house.room.Room;
import com.hoo.aoo.common.adapter.in.web.MessageDto;
import com.hoo.aoo.file.application.port.in.UploadImageResult;
import com.hoo.aoo.file.application.port.in.UploadPrivateImageUseCase;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

import java.util.HashMap;
import java.util.Map;
import java.util.Optional;

@Service
@Slf4j
@RequiredArgsConstructor
public class UpdateHouseService implements UpdateHouseUseCase {

    private final LoadHousePort loadHousePort;
    private final UpdateHousePort updateHousePort;
    private final UploadPrivateImageUseCase uploadPrivateImageUseCase;

    @Override
    public MessageDto update(Long houseId, Metadata metadata, Map<String, MultipartFile> fileMap) {

        Map<String, Long> imageIdMap = new HashMap<>();
        try {
            House houseInDB = loadHousePort.load(houseId)
                    .orElseThrow(() -> new AdminException(AdminErrorCode.HOUSE_NOT_FOUND));

            houseInDB.update(metadata.house().title(), metadata.house().author(), metadata.house().description(), metadata.house().width(), metadata.house().height());

            if (fileMap.containsKey(metadata.house().houseForm())) {
                UploadImageResult uploadImageResult = uploadPrivateImageUseCase.privateUpload(fileMap.get(metadata.house().houseForm()));
                imageIdMap.put("houseImageId", uploadImageResult.fileInfos().getFirst().id());
            }

            if (fileMap.containsKey(metadata.house().borderForm())) {
                UploadImageResult uploadImageResult = uploadPrivateImageUseCase.privateUpload(fileMap.get(metadata.house().borderForm()));
                imageIdMap.put("borderImageId", uploadImageResult.fileInfos().getFirst().id());
            }

            loop:
            for (Metadata.Room roomData : metadata.rooms()) {

                if (roomData.originalName() == null) continue;

                for (Room roomInDB : houseInDB.getRooms()) {

                    if (!roomInDB.getId().getName().equals(roomData.originalName())) continue;

                    roomInDB.update(roomData.name(), roomData.x(), roomData.y(), roomData.z(), roomData.width(), roomData.height());

                    if (fileMap.containsKey(roomData.form())) {
                        UploadImageResult uploadImageResult = uploadPrivateImageUseCase.privateUpload(fileMap.get(roomData.form()));
                        imageIdMap.put(roomInDB.getId().getName(), uploadImageResult.fileInfos().getFirst().id());
                    }

                    continue loop;
                }

                log.error("Room name {} is not found in this house.", roomData.originalName());
                throw new AdminException(AdminErrorCode.ROOM_NOT_FOUND);
            }

            updateHousePort.update(houseInDB, imageIdMap);

            return new MessageDto(houseId + "번 하우스 수정이 완료되었습니다.");

        } catch (AxisLimitExceededException e) {

            log.error(e.getMessage());
            throw new AdminException(AdminErrorCode.AXIS_PIXEL_LIMIT_EXCEED);

        } catch (AreaLimitExceededException e) {

            log.error(e.getMessage());
            throw new AdminException(AdminErrorCode.AREA_SIZE_LIMIT_EXCEED);

        }
    }
}
