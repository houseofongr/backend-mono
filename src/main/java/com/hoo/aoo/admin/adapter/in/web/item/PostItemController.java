package com.hoo.aoo.admin.adapter.in.web.item;

import com.hoo.aoo.admin.application.port.in.house.CreateHouseMetadata;
import com.hoo.aoo.admin.application.port.in.item.CreateItemMetadata;
import com.hoo.aoo.admin.application.port.in.item.CreateItemResult;
import com.hoo.aoo.admin.application.port.in.item.CreateItemUseCase;
import com.hoo.aoo.admin.application.service.AdminErrorCode;
import com.hoo.aoo.admin.application.service.AdminException;
import jakarta.servlet.http.HttpServletRequest;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.multipart.MultipartHttpServletRequest;

import static com.hoo.aoo.common.util.GsonUtil.gson;

@RestController
@RequiredArgsConstructor
public class PostItemController {

    private final CreateItemUseCase createItemUseCase;

    @PostMapping("/admin/users/{userId}/homes/{homeId}/rooms/{roomId}/items")
    public ResponseEntity<CreateItemResult> createItem(
            @PathVariable Long userId,
            @PathVariable Long homeId,
            @PathVariable Long roomId,
            @RequestParam String metadata,
            HttpServletRequest request) {

        if (request instanceof MultipartHttpServletRequest multipartRequest) {

            return new ResponseEntity<>(
                    createItemUseCase.create(userId, homeId, roomId, gson.fromJson(metadata, CreateItemMetadata.class), multipartRequest.getFileMap()),
                    HttpStatus.CREATED);
        }

        else throw new AdminException(AdminErrorCode.INVALID_CREATE_ITEM_METADATA);
    }
}
