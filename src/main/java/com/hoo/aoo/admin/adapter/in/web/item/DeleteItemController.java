package com.hoo.aoo.admin.adapter.in.web.item;

import com.hoo.aoo.common.adapter.in.web.MessageDto;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequiredArgsConstructor
public class DeleteItemController {

    @DeleteMapping("/admin/items/{itemId}")
    public ResponseEntity<MessageDto> delete(@PathVariable Long itemId) {
        return ResponseEntity.ok(new MessageDto(itemId + "번 아이템이 삭제되었습니다."));
    }

}
