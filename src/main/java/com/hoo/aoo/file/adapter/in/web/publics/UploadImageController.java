package com.hoo.aoo.file.adapter.in.web.publics;

import com.hoo.aoo.file.application.port.in.UploadImageUseCase;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.multipart.MultipartFile;

import java.util.List;

@RestController
@RequiredArgsConstructor
public class UploadImageController {

    private final UploadImageUseCase uploadImageUseCase;

    @PostMapping("/public/images")
    public ResponseEntity<?> upload(@RequestParam(value = "images") List<MultipartFile> images) {
        Response response =
                new Response(List.of(
                        new Response.FileInfo(1L, "image.png", "23.456MB"),
                        new Response.FileInfo(2L, "image2.png", "34.567MB")));
        return new ResponseEntity<>(response, HttpStatus.CREATED);
    }

    private record Response(
            List<FileInfo> fileInfos
    ) {
        private record FileInfo(
                Long id,
                String name,
                String size
        ) {
        }
    }
}
