package com.hoo.aoo.admin.application.service.house;

import com.hoo.aoo.admin.application.port.in.house.CreateHouseMetadata;
import com.hoo.aoo.admin.application.port.in.house.CreateHouseResult;
import com.hoo.aoo.admin.application.port.in.house.CreateHouseUseCase;
import com.hoo.aoo.admin.application.port.out.house.SaveHousePort;
import com.hoo.aoo.admin.application.service.AdminErrorCode;
import com.hoo.aoo.admin.application.service.AdminException;
import com.hoo.aoo.admin.domain.exception.AreaLimitExceededException;
import com.hoo.aoo.admin.domain.exception.AxisLimitExceededException;
import com.hoo.aoo.admin.domain.house.House;
import com.hoo.aoo.admin.domain.house.Detail;
import com.hoo.aoo.admin.domain.house.room.Room;
import com.hoo.aoo.common.application.port.in.CreateHousePort;
import com.hoo.aoo.file.application.port.in.UploadFileResult;
import com.hoo.aoo.file.application.port.in.UploadPrivateImageUseCase;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.multipart.MultipartFile;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;

@Service
@RequiredArgsConstructor
public class CreateHouseService implements CreateHouseUseCase {

    private final SaveHousePort saveHousePort;
    private final UploadPrivateImageUseCase uploadPrivateImageUseCase;
    private final CreateHousePort createHousePort;

    @Override
    @Transactional
    public CreateHouseResult create(CreateHouseMetadata metadata, Map<String, MultipartFile> fileMap) throws AdminException {

        Detail detail = new Detail(metadata.house().title(), metadata.house().author(), metadata.house().description());

        List<Room> rooms = new ArrayList<>();

        try {
            List<MultipartFile> uploadingFiles = new ArrayList<>(metadata.rooms().stream().map(room -> fileMap.get(room.form())).toList());
            uploadingFiles.add(fileMap.get(metadata.house().houseForm()));
            uploadingFiles.add(fileMap.get(metadata.house().borderForm()));

            UploadFileResult uploadFileResult = uploadPrivateImageUseCase.privateUpload(uploadingFiles);

            Long basicImageId = null, borderImageId = null;
            for (UploadFileResult.FileInfo fileInfo : uploadFileResult.fileInfos()) {
                if (fileInfo.realName().equals(fileMap.get(metadata.house().houseForm()).getOriginalFilename()))
                    basicImageId = fileInfo.id();

                else if (fileInfo.realName().equals(fileMap.get(metadata.house().borderForm()).getOriginalFilename()))
                    borderImageId = fileInfo.id();

                else
                    for (CreateHouseMetadata.RoomData room : metadata.rooms()) {
                        if (fileInfo.realName().equals(fileMap.get(room.form()).getOriginalFilename())) {
                            rooms.add(Room.create(room.name(), room.x(), room.y(), room.z(), room.width(), room.height(), fileInfo.id()));
                        }
                    }
            }

            House newHouse = createHousePort.createHouse(detail, metadata.house().width(), metadata.house().height(),  basicImageId, borderImageId, rooms);
            Long savedId = saveHousePort.save(newHouse);

            return new CreateHouseResult(savedId);

        } catch (AxisLimitExceededException e) {
            throw new AdminException(AdminErrorCode.AXIS_PIXEL_LIMIT_EXCEED);

        } catch (AreaLimitExceededException e) {
            throw new AdminException(AdminErrorCode.AREA_SIZE_LIMIT_EXCEED);

        }
    }
}
