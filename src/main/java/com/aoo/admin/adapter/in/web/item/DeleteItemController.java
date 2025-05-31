package com.aoo.admin.adapter.in.web.item;

import com.aoo.admin.application.port.in.item.DeleteItemUseCase;
import com.aoo.common.application.port.in.MessageDto;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequiredArgsConstructor
public class DeleteItemController {

    private final DeleteItemUseCase deleteItemUseCase;

    @DeleteMapping("/admin/items/{itemId}")
    public ResponseEntity<MessageDto> delete(@PathVariable Long itemId) {
        return ResponseEntity.ok(deleteItemUseCase.deleteItem(itemId));
    }

}
