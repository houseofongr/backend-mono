package com.hoo.aoo.admin.application.service;

import com.hoo.aoo.admin.application.port.in.house.CreateHouseUseCase;
import com.hoo.aoo.admin.application.port.in.house.HouseIdResult;
import com.hoo.aoo.admin.application.port.out.SaveHousePort;
import com.hoo.aoo.admin.domain.exception.AreaLimitExceededException;
import com.hoo.aoo.admin.domain.exception.AxisLimitExceededException;
import com.hoo.aoo.admin.domain.exception.HouseRelationshipException;
import com.hoo.aoo.admin.domain.house.House;
import com.hoo.aoo.admin.domain.house.HouseId;
import com.hoo.aoo.admin.domain.house.room.Room;
import com.hoo.aoo.file.application.port.in.UploadImageResult;
import com.hoo.aoo.file.application.port.in.UploadPrivateImageUseCase;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.multipart.MultipartFile;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

@Service
@RequiredArgsConstructor
public class CreateHouseService implements CreateHouseUseCase {

    public static final String BASIC_HOUSE_IMAGE_ID = "basicImageId";
    public static final String HOUSE_BORDER_IMAGE_ID = "borderImageId";

    private final SaveHousePort saveHousePort;
    private final UploadPrivateImageUseCase uploadPrivateImageUseCase;

    @Override
    @Transactional
    public HouseIdResult create(Metadata metadata, Map<String, MultipartFile> fileMap) throws AdminException {

        HouseId houseId = new HouseId(metadata.house().title(), metadata.house().author(), metadata.house().description());

        List<Room> rooms = new ArrayList<>();

        Map<String, Long> imageFileIdMap = new HashMap<>();

        try {
            for (Metadata.Room room : metadata.rooms()) {

                UploadImageResult uploadImageResult = uploadPrivateImageUseCase.privateUpload(fileMap.get(room.form()));

                Room newRoom = Room.create(houseId, room.name(), room.x(), room.y(), room.z(), room.width(), room.height());

                rooms.add(newRoom);

                imageFileIdMap.put(room.name(), uploadImageResult.fileInfos().getFirst().id());
            }

            UploadImageResult houseUploadResult = uploadPrivateImageUseCase.privateUpload(fileMap.get(metadata.house().houseForm()));
            UploadImageResult borderUploadResult = uploadPrivateImageUseCase.privateUpload(fileMap.get(metadata.house().borderForm()));

            imageFileIdMap.put(BASIC_HOUSE_IMAGE_ID,houseUploadResult.fileInfos().getFirst().id());
            imageFileIdMap.put(HOUSE_BORDER_IMAGE_ID,borderUploadResult.fileInfos().getFirst().id());

            House newHouse = House.create(houseId, metadata.house().width(), metadata.house().height(), rooms);

            Long savedId = saveHousePort.save(newHouse, rooms, imageFileIdMap);

            return new HouseIdResult(savedId);

        } catch (AxisLimitExceededException e) {
            throw new AdminException(AdminErrorCode.AXIS_PIXEL_LIMIT_EXCEED);

        } catch (AreaLimitExceededException e) {
            throw new AdminException(AdminErrorCode.AREA_SIZE_LIMIT_EXCEED);

        } catch (HouseRelationshipException e) {
            throw new AdminException(AdminErrorCode.ILLEGAL_HOUSE_RELATIONSHIP);

        }
    }
}
