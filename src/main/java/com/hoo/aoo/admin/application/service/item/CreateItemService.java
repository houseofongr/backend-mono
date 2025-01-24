package com.hoo.aoo.admin.application.service.item;

import com.hoo.aoo.admin.application.port.in.item.CreateItemMetadata;
import com.hoo.aoo.admin.application.port.in.item.CreateItemResult;
import com.hoo.aoo.admin.application.port.in.item.CreateItemUseCase;
import com.hoo.aoo.admin.application.port.out.home.FindHomePort;
import com.hoo.aoo.admin.application.port.out.item.CreateItemPort;
import com.hoo.aoo.admin.application.port.out.item.SaveItemPort;
import com.hoo.aoo.admin.application.port.out.room.FindRoomPort;
import com.hoo.aoo.admin.application.port.out.user.FindUserPort;
import com.hoo.aoo.admin.application.service.AdminErrorCode;
import com.hoo.aoo.admin.application.service.AdminException;
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
// TODO : 아이템 생성로직, 음원 생성로직 분리
public class CreateItemService implements CreateItemUseCase {

    private final FindUserPort findUserPort;
    private final FindHomePort findHomePort;
    private final FindRoomPort findRoomPort;
    private final SaveItemPort saveItemPort;
    private final CreateItemPort createItemPort;
    private final UploadPrivateAudioUseCase uploadPrivateAudioUseCase;

    @Override
    @Transactional
    public CreateItemResult create(Long userId, Long homeId, Long roomId, CreateItemMetadata metadata, Map<String, MultipartFile> fileMap) {

        if (!findUserPort.exist(userId)) throw new AdminException(AdminErrorCode.USER_NOT_FOUND);
        if (!findHomePort.exist(homeId)) throw new AdminException(AdminErrorCode.HOME_NOT_FOUND);
        if (!findRoomPort.exist(roomId)) throw new AdminException(AdminErrorCode.ROOM_NOT_FOUND);

        UploadFileResult uploadFileResult = uploadPrivateAudioUseCase.privateUpload(
                metadata.items().stream().map(itemData -> fileMap.get(itemData.soundSourceData().form())).toList(), userId);

        List<Item> newItems = new ArrayList<>();
        for (CreateItemMetadata.ItemData itemData : metadata.items()) {

            CreateItemMetadata.SoundSourceData soundSourceData = itemData.soundSourceData();
            UploadFileResult.FileInfo fileInfo = uploadFileResult.fileInfos().stream()
                    .filter(fi -> fi.realName().equals(fileMap.get(soundSourceData.form()).getOriginalFilename())).findFirst()
                    .orElseThrow(() -> new AdminException(AdminErrorCode.INVALID_CREATE_ITEM_METADATA));

            newItems.add(createItemPort.createItem(roomId, itemData.name(), createShape(itemData), List.of()));
        }

        return new CreateItemResult(saveItemPort.save(userId, homeId, roomId, newItems));
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
            default ->
                    throw new AdminException(AdminErrorCode.ILLEGAL_SHAPE_TYPE, "잘못된 아이템 형태 : " + itemData.itemType());
        }
    }
}
