package com.hoo.aoo.admin.adapter.in.web.house;

import com.hoo.aoo.admin.application.port.in.CreateHouseResult;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.util.MultiValueMap;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.multipart.MultipartFile;

import java.util.List;

@RestController
@RequiredArgsConstructor
public class CreateHouseController {

    @PostMapping("/admin/houses")
    public ResponseEntity<CreateHouseResult> create(@RequestParam MultiValueMap<String, MultipartFile> formData) {

        CreateHouseResult result = new CreateHouseResult(
                new CreateHouseResult.House(1L,1L,2L,"cozy house","leaf",2),
                List.of(
                        new CreateHouseResult.Room(1L,3L,"거실"),
                        new CreateHouseResult.Room(2L,4L,"주방")
                )
        );

        return new ResponseEntity<>(result, HttpStatus.CREATED);
    }
}
