package com.hoo.aoo.admin.application.service.item;

import com.hoo.aoo.admin.application.port.in.item.CreateItemCommand;
import com.hoo.aoo.admin.application.port.in.item.CreateItemResult;
import com.hoo.aoo.admin.application.port.in.item.CreateItemUseCase;
import com.hoo.aoo.admin.application.port.in.item.ItemData;
import com.hoo.aoo.admin.application.port.out.home.FindHomePort;
import com.hoo.aoo.admin.application.port.out.item.CreateItemPort;
import com.hoo.aoo.admin.application.port.out.item.SaveItemPort;
import com.hoo.aoo.admin.application.port.out.room.FindRoomPort;
import com.hoo.aoo.admin.application.port.out.user.FindUserPort;
import com.hoo.aoo.admin.application.service.AdminErrorCode;
import com.hoo.aoo.admin.application.service.AdminException;
import com.hoo.aoo.admin.domain.item.*;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

@Service
@RequiredArgsConstructor
public class CreateItemService implements CreateItemUseCase {

    private final FindUserPort findUserPort;
    private final FindHomePort findHomePort;
    private final FindRoomPort findRoomPort;
    private final SaveItemPort saveItemPort;
    private final CreateItemPort createItemPort;

    @Override
    @Transactional
    public CreateItemResult create(Long userId, Long homeId, Long roomId, CreateItemCommand command) {

        if (!findUserPort.exist(userId)) throw new AdminException(AdminErrorCode.USER_NOT_FOUND);
        if (!findHomePort.exist(homeId)) throw new AdminException(AdminErrorCode.HOME_NOT_FOUND);
        if (!findRoomPort.exist(roomId)) throw new AdminException(AdminErrorCode.ROOM_NOT_FOUND);

        List<Item> newItems = command.items().stream().map(itemData ->
                createItemPort.createItem(roomId, itemData.name(), createShape(itemData))
        ).toList();

        return new CreateItemResult(saveItemPort.save(userId, homeId, roomId, newItems));
    }

    private Shape createShape(ItemData itemData) {
        switch (itemData.itemType()) {
            case RECTANGLE -> {
                return new Rectangle(itemData.rectangleData().x(), itemData.rectangleData().y(), itemData.rectangleData().width(), itemData.rectangleData().height(), itemData.rectangleData().rotation());
            }
            case CIRCLE -> {
                return new Circle(itemData.circleData().x(), itemData.circleData().y(), itemData.circleData().radius());
            }
            case ELLIPSE -> {
                return new Ellipse(itemData.ellipseData().x(), itemData.ellipseData().y(), itemData.ellipseData().radiusX(), itemData.ellipseData().radiusY(), itemData.ellipseData().rotation());
            }
            default ->
                    throw new AdminException(AdminErrorCode.ILLEGAL_SHAPE_TYPE, "잘못된 아이템 형태 : " + itemData.itemType());
        }
    }
}
