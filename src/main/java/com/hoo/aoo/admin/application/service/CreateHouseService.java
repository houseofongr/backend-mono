package com.hoo.aoo.admin.application.service;

import com.hoo.aoo.admin.application.port.in.CreateHouseResult;
import com.hoo.aoo.admin.application.port.in.CreateHouseUseCase;
import com.hoo.aoo.admin.application.port.out.database.SaveHousePort;
import com.hoo.aoo.admin.application.port.out.database.SaveRoomPort;
import com.hoo.aoo.admin.domain.exception.AreaLimitExceededException;
import com.hoo.aoo.admin.domain.exception.AxisLimitExceededException;
import com.hoo.aoo.admin.domain.house.House;
import com.hoo.aoo.admin.domain.house.HouseId;
import com.hoo.aoo.admin.domain.room.Room;
import com.hoo.aoo.admin.domain.room.RoomId;
import com.hoo.aoo.file.application.port.in.UploadImageResult;
import com.hoo.aoo.file.application.port.in.UploadPrivateImageUseCase;
import com.nimbusds.jose.shaded.gson.Gson;
import lombok.RequiredArgsConstructor;
import org.springframework.web.multipart.MultipartFile;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;

@RequiredArgsConstructor
public class CreateHouseService implements CreateHouseUseCase {

    private final SaveRoomPort saveRoomPort;
    private final SaveHousePort saveHousePort;
    private final UploadPrivateImageUseCase uploadPrivateImageUseCase;
    private final Gson gson = new Gson();

    @Override
    public CreateHouseResult create(Map<String, Object> formData) throws AdminException, AxisLimitExceededException, AreaLimitExceededException {

        Object formMetadata = formData.get("metadata");

        if (!(formMetadata instanceof String))
            throw new AdminException(AdminErrorCode.INVALID_METADATA);

        Metadata metadata = gson.fromJson((String) formMetadata, Metadata.class);

        HouseId houseId = new HouseId(metadata.house().title(), metadata.house().author(), metadata.house().description());

        List<RoomId> rooms = new ArrayList<>();
        List<CreateHouseResult.Room> roomCreateResult = new ArrayList<>();
        for (Metadata.Room room : metadata.rooms()) {

            UploadImageResult uploadImageResult = uploadPrivateImageUseCase.privateUpload(List.of(getImageFile(room.formName(), formData)));

            Room newRoom = Room.create(houseId, room.name(), room.x(), room.y(), room.z(), room.width(), room.height(), uploadImageResult.fileInfos().getFirst().id());
            Long roomJpaId = saveRoomPort.save(newRoom);

            roomCreateResult.add(new CreateHouseResult.Room(roomJpaId, newRoom.getImage().getImageId(), newRoom.getId().getName()));
            rooms.add(newRoom.getId());
        }

        UploadImageResult houseUploadResult = uploadPrivateImageUseCase.privateUpload(List.of(getImageFile(metadata.house().houseFormName(), formData)));
        UploadImageResult borderUploadResult = uploadPrivateImageUseCase.privateUpload(List.of(getImageFile(metadata.house().houseFormName(), formData)));

        House newHouse = House.create(houseId,
                metadata.house().width(),
                metadata.house().height(),
                houseUploadResult.fileInfos().getFirst().id(),
                borderUploadResult.fileInfos().getFirst().id(),
                rooms);

        Long houseJpaId = saveHousePort.save(newHouse);

        return new CreateHouseResult(
                new CreateHouseResult.House(houseJpaId,
                        newHouse.getImages().getBasicImageId(),
                        newHouse.getImages().getBorderImageId(),
                        newHouse.getId().getTitle(),
                        newHouse.getId().getAuthor(),
                        newHouse.getRooms().size()),
                roomCreateResult);
    }

    private MultipartFile getImageFile(String formName, Map<String, Object> formData) {

        Object formImageData = formData.get(formName);

        if (!(formImageData instanceof MultipartFile))
            throw new AdminException(AdminErrorCode.IMAGE_FILE_NOT_FOUND);

        return (MultipartFile) formImageData;
    }

}
