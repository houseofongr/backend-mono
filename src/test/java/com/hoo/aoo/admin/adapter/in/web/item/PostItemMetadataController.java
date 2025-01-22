package com.hoo.aoo.admin.adapter.in.web.item;

import com.hoo.aoo.admin.application.port.in.house.CreateHouseMetadata;
import com.hoo.aoo.admin.application.port.in.item.CreateItemMetadata;
import com.hoo.aoo.common.FixtureRepository;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
public class PostItemMetadataController {

    @GetMapping("/mock/admin/create-item-metadata")
    ResponseEntity<CreateItemMetadata> getMetadata() {
        return new ResponseEntity<>(FixtureRepository.getCreateItemMetadata(), HttpStatus.OK);
    }

}
