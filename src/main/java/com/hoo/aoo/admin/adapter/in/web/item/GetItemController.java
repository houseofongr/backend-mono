package com.hoo.aoo.admin.adapter.in.web.item;

import com.hoo.aoo.admin.application.port.in.item.QueryItemResult;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

@RestController
@RequiredArgsConstructor
public class GetItemController {

    @GetMapping("/admin/items/{itemId}")
    public ResponseEntity<QueryItemResult> getItem() {
        QueryItemResult result = new QueryItemResult(
          "설이", List.of(new QueryItemResult.SoundSourceInfo(
                  1L, "골골송", "2025년 골골송", "2025.01.24.", "2025.01.24.", 1L)
        ));
        return ResponseEntity.ok(result);
    }
}
