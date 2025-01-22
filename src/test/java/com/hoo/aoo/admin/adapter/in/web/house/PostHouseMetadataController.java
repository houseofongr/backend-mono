package com.hoo.aoo.admin.adapter.in.web.house;

import com.hoo.aoo.admin.application.port.in.house.CreateHouseMetadata;
import com.hoo.aoo.common.FixtureRepository;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
public class PostHouseMetadataController {

    @GetMapping("/mock/admin/create-house-metadata")
    ResponseEntity<CreateHouseMetadata> getMetadata() {
        return new ResponseEntity<>(FixtureRepository.getCreateHouseMetadata(), HttpStatus.OK);
    }

}
