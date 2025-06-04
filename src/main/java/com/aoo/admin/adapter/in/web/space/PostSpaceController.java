package com.aoo.admin.adapter.in.web.space;

import com.aoo.admin.application.port.in.space.CreateSpaceCommand;
import com.aoo.admin.application.port.in.space.CreateSpaceResult;
import com.aoo.admin.application.port.in.space.CreateSpaceUseCase;
import com.aoo.common.application.port.in.MessageDto;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.multipart.MultipartFile;

import static com.aoo.common.util.GsonUtil.gson;

@RestController
@RequiredArgsConstructor
public class PostSpaceController {

    private final CreateSpaceUseCase useCase;

    @PostMapping("/admin/spaces")
    public ResponseEntity<CreateSpaceResult> create(@RequestParam String metadata,
                                                    @RequestParam("image") MultipartFile imageFile) {

        CreateSpaceCommand baseCommand = gson.fromJson(metadata, CreateSpaceCommand.class);
        CreateSpaceCommand fullCommand = CreateSpaceCommand.from(baseCommand,imageFile);

        return new ResponseEntity<>(useCase.create(fullCommand), HttpStatus.CREATED);
    }
}
