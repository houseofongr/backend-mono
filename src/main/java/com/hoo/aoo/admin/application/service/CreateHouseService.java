package com.hoo.aoo.admin.application.service;

import com.hoo.aoo.admin.application.port.in.HouseIdResult;
import com.hoo.aoo.admin.application.port.in.CreateHouseUseCase;
import com.hoo.aoo.admin.application.port.out.database.SaveHousePort;
import com.hoo.aoo.admin.domain.exception.AreaLimitExceededException;
import com.hoo.aoo.admin.domain.exception.AxisLimitExceededException;
import com.hoo.aoo.admin.domain.exception.HouseRelationshipException;
import com.hoo.aoo.admin.domain.exception.RoomDuplicatedException;
import com.hoo.aoo.admin.domain.house.House;
import com.hoo.aoo.admin.domain.house.HouseId;
import com.hoo.aoo.admin.domain.room.Room;
import com.hoo.aoo.file.application.port.in.UploadImageResult;
import com.hoo.aoo.file.application.port.in.UploadPrivateImageUseCase;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Component;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.multipart.MultipartFile;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;

@Slf4j
@Component
@RequiredArgsConstructor
public class CreateHouseService implements CreateHouseUseCase {

    private final SaveHousePort saveHousePort;
    private final UploadPrivateImageUseCase uploadPrivateImageUseCase;

    @Override
    @Transactional
    public HouseIdResult create(Metadata metadata, Map<String, MultipartFile> fileMap) throws AdminException {

        HouseId houseId = new HouseId(metadata.house().title(), metadata.house().author(), metadata.house().description());

        List<Room> rooms = new ArrayList<>();

        try {
            for (Metadata.Room room : metadata.rooms()) {

                UploadImageResult uploadImageResult = uploadPrivateImageUseCase.privateUpload(List.of(fileMap.get(room.formName())));

                Room newRoom = Room.create(houseId, room.name(), room.x(), room.y(), room.z(), room.width(), room.height(), uploadImageResult.fileInfos().getFirst().id());

                rooms.add(newRoom);
            }

            UploadImageResult houseUploadResult = uploadPrivateImageUseCase.privateUpload(List.of(fileMap.get(metadata.house().houseFormName())));
            UploadImageResult borderUploadResult = uploadPrivateImageUseCase.privateUpload(List.of(fileMap.get(metadata.house().borderFormName())));

            House newHouse = House.create(houseId,
                    metadata.house().width(),
                    metadata.house().height(),
                    rooms.stream().map(Room::getId).toList());

            Long savedId = saveHousePort.save(newHouse,
                    rooms,
                    houseUploadResult.fileInfos().getFirst().id(),
                    borderUploadResult.fileInfos().getFirst().id());

            return new HouseIdResult(savedId);

        } catch (AxisLimitExceededException e) {

            log.error(e.getMessage());
            throw new AdminException(AdminErrorCode.PIXEL_LIMIT_EXCEED);

        } catch (AreaLimitExceededException e) {

            log.error(e.getMessage());
            throw new AdminException(AdminErrorCode.IMAGE_SIZE_EXCEED);

        } catch (RoomDuplicatedException e) {

            log.error(e.getMessage());
            throw new AdminException(AdminErrorCode.ROOM_NAME_DUPLICATED);

        } catch (HouseRelationshipException e) {

            log.error(e.getMessage());
            throw new AdminException(AdminErrorCode.ILLEGAL_HOUSE_RELATIONSHIP);

        }
    }
}
