package com.hoo.aoo.admin.adapter.in.web.item;

import com.hoo.aoo.common.adapter.in.web.MessageDto;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PatchMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequiredArgsConstructor
public class PatchItemController {

    @PatchMapping("/admin/items/{itemId}")
    public ResponseEntity<MessageDto> updateItem(@PathVariable Long itemId) {
        return ResponseEntity.ok(new MessageDto(itemId + "번 아이템의 정보가 수정되었습니다."));
    }
}
