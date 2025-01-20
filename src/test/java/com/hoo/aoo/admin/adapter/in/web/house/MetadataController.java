package com.hoo.aoo.admin.adapter.in.web.house;

import com.hoo.aoo.admin.application.service.house.Metadata;
import com.hoo.aoo.common.FixtureRepository;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
public class MetadataController {

    @GetMapping("/mock/admin/house/metadata")
    ResponseEntity<Metadata> getMetadata() {
        return new ResponseEntity<>(FixtureRepository.getMetadata(), HttpStatus.OK);
    }

}
