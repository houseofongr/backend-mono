package com.hoo.aoo.admin.adapter.in.web.item;

import com.hoo.aoo.admin.application.port.in.item.CreateItemResult;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

@RestController
@RequiredArgsConstructor
public class PostItemController {

    @PostMapping("/admin/rooms/{roomId}/items")
    public ResponseEntity<CreateItemResult> createItem(@PathVariable Long roomId) {
        return new ResponseEntity<>(new CreateItemResult(List.of(1L, 2L, 3L)), HttpStatus.CREATED);
    }
}
