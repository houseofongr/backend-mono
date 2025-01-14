package com.hoo.aoo.admin.adapter.in.web.house;

import com.hoo.aoo.admin.application.service.Metadata;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

@RestController
public class MetadataController {

    @GetMapping("/mock/admin/house/metadata")
    ResponseEntity<Metadata> getMetadata() {
        Metadata metadata = new Metadata(
                new Metadata.House("cozy house","남상엽","this is cozy house", "houseForm", "borderForm", 5000F,5000F),
                List.of(new Metadata.Room("room1Form","거실",0F,0F,0F,5000F,1000f))
        );
        return new ResponseEntity<>(metadata, HttpStatus.OK);
    }

}
