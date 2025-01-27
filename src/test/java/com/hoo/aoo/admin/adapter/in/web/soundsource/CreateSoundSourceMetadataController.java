package com.hoo.aoo.admin.adapter.in.web.soundsource;

import com.hoo.aoo.admin.application.port.in.house.CreateHouseMetadata;
import com.hoo.aoo.admin.application.port.in.house.CreateHouseMetadataTest;
import com.hoo.aoo.admin.application.port.in.soundsource.CreateSoundSourceMetadata;
import com.hoo.aoo.common.util.GsonUtil;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;

import static com.hoo.aoo.common.util.GsonUtil.gson;

@RestController
public class CreateSoundSourceMetadataController {

    @GetMapping("/mock/admin/create-soundsource-metadata")
    ResponseEntity<CreateSoundSourceMetadata> getMetadata() {

        //language=JSON
        String data = """
                {
                  "name" : "골골송",
                  "description" : "2025년 설이가 보내는 골골송",
                  "isActive" : true
                }
                """;

        return new ResponseEntity<>(gson.fromJson(data, CreateSoundSourceMetadata.class), HttpStatus.OK);
    }

}
