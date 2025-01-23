package com.hoo.aoo.admin.application.service.item;

import com.hoo.aoo.admin.application.port.in.item.CreateItemMetadata;
import com.hoo.aoo.admin.application.port.in.item.CreateItemResult;
import com.hoo.aoo.admin.application.port.in.item.CreateItemUseCase;
import com.hoo.aoo.admin.application.port.out.item.SaveItemPort;
import com.hoo.aoo.admin.application.port.out.room.FindRoomPort;
import com.hoo.aoo.admin.application.service.AdminErrorCode;
import com.hoo.aoo.admin.application.service.AdminException;
import com.hoo.aoo.admin.domain.house.room.Room;
import com.hoo.aoo.admin.domain.item.*;
import com.hoo.aoo.file.application.port.in.UploadFileResult;
import com.hoo.aoo.file.application.port.in.UploadPrivateAudioUseCase;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.multipart.MultipartFile;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;

@Service
@RequiredArgsConstructor
public class CreateItemService implements CreateItemUseCase {

    private final FindRoomPort findRoomPort;
    private final SaveItemPort saveItemPort;
    private final UploadPrivateAudioUseCase uploadPrivateAudioUseCase;

    @Override
    @Transactional
    public CreateItemResult create(Long userId, Long roomId, CreateItemMetadata metadata, Map<String, MultipartFile> fileMap) {

        findRoomPort.load(roomId).orElseThrow(() -> new AdminException(AdminErrorCode.ROOM_NOT_FOUND));

        UploadFileResult uploadFileResult = uploadPrivateAudioUseCase.privateUpload(
                metadata.items().stream().map(itemData -> fileMap.get(itemData.form())).toList(), userId);

        List<Item> newItems = new ArrayList<>();
        for (CreateItemMetadata.ItemData itemData : metadata.items()) {

            UploadFileResult.FileInfo fileInfo = uploadFileResult.fileInfos().stream()
                    .filter(fi -> fi.realName().equals(fileMap.get(itemData.form()).getOriginalFilename())).findFirst()
                    .orElseThrow(() -> new AdminException(AdminErrorCode.INVALID_CREATE_ITEM_METADATA));

            newItems.add(Item.create(roomId, itemData.name(), createShape(itemData), List.of(fileInfo.id())));
        }

        return new CreateItemResult(saveItemPort.save(newItems));
    }

    private Shape createShape(CreateItemMetadata.ItemData itemData) {
        switch (itemData.itemType()) {
            case RECTANGLE -> {
                return new Rectangle(itemData.rectangleData().x(), itemData.rectangleData().y(), itemData.rectangleData().width(), itemData.rectangleData().height(), itemData.rectangleData().angle());
            }
            case CIRCLE -> {
                return new Circle(itemData.circleData().x(), itemData.circleData().y(), itemData.circleData().radius());
            }
            case ELLIPSE -> {
                return new Ellipse(itemData.ellipseData().x(), itemData.ellipseData().y(), itemData.ellipseData().width(), itemData.ellipseData().height(), itemData.ellipseData().angle());
            }
            default -> throw new AdminException(AdminErrorCode.ILLEGAL_SHAPE_TYPE, "잘못된 아이템 형태 : " + itemData.itemType());
        }
    }
}
