package com.hoo.aoo.admin.application.service;

import com.hoo.aoo.admin.application.port.in.CreateHouseResult;
import com.hoo.aoo.admin.application.port.in.CreateHouseUseCase;
import com.hoo.aoo.admin.application.port.out.database.SaveHousePort;
import com.hoo.aoo.admin.domain.exception.AreaLimitExceededException;
import com.hoo.aoo.admin.domain.exception.AxisLimitExceededException;
import com.hoo.aoo.admin.domain.house.House;
import com.hoo.aoo.admin.domain.house.HouseId;
import com.hoo.aoo.admin.domain.room.Room;
import com.hoo.aoo.file.application.port.in.UploadImageResult;
import com.hoo.aoo.file.application.port.in.UploadPrivateImageUseCase;
import com.nimbusds.jose.shaded.gson.Gson;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Component;
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
    public CreateHouseResult create(Metadata metadata, Map<String, MultipartFile> fileMap) throws AdminException {

        HouseId houseId = new HouseId(metadata.house().title(), metadata.house().author(), metadata.house().description());

        List<Room> rooms = new ArrayList<>();
        List<CreateHouseResult.Room> roomCreateResult = new ArrayList<>();

        try {
            for (Metadata.Room room : metadata.rooms()) {

                UploadImageResult uploadImageResult = uploadPrivateImageUseCase.privateUpload(List.of(fileMap.get(room.formName())));

                Room newRoom = Room.create(houseId, room.name(), room.x(), room.y(), room.z(), room.width(), room.height(), uploadImageResult.fileInfos().getFirst().id());

                rooms.add(newRoom);
                roomCreateResult.add(new CreateHouseResult.Room(newRoom.getImage().getImageId(), newRoom.getId().getName()));
            }

            UploadImageResult houseUploadResult = uploadPrivateImageUseCase.privateUpload(List.of(fileMap.get(metadata.house().houseFormName())));
            UploadImageResult borderUploadResult = uploadPrivateImageUseCase.privateUpload(List.of(fileMap.get(metadata.house().borderFormName())));

            House newHouse = House.create(houseId,
                    metadata.house().width(),
                    metadata.house().height(),
                    houseUploadResult.fileInfos().getFirst().id(),
                    borderUploadResult.fileInfos().getFirst().id(),
                    rooms.stream().map(Room::getId).toList());

            Long houseJpaId = saveHousePort.save(newHouse, rooms);

            return new CreateHouseResult(
                    new CreateHouseResult.House(houseJpaId,
                            newHouse.getImages().getBasicImageId(),
                            newHouse.getImages().getBorderImageId(),
                            newHouse.getId().getTitle(),
                            newHouse.getId().getAuthor(),
                            newHouse.getRooms().size()),
                    rooms.stream().map(CreateHouseResult.Room::of).toList());

        } catch (AxisLimitExceededException e) {

            log.error(e.getMessage());
            throw new AdminException(AdminErrorCode.PIXEL_LIMIT_EXCEED);

        } catch (AreaLimitExceededException e) {

            log.error(e.getMessage());
            throw new AdminException(AdminErrorCode.IMAGE_SIZE_EXCEED);

        }
    }
}
