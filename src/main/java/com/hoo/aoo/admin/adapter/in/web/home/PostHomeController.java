package com.hoo.aoo.admin.adapter.in.web.home;

import com.hoo.aoo.admin.application.port.in.home.CreateHomeCommand;
import com.hoo.aoo.admin.application.port.in.home.CreateHomeResult;
import com.hoo.aoo.admin.application.port.in.home.CreateHomeUseCase;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequiredArgsConstructor
public class PostHomeController {

    private final CreateHomeUseCase createHomeUseCase;

    @PostMapping("/admin/homes")
    ResponseEntity<CreateHomeResult> create(@RequestBody CreateHomeCommand command) {
        return new ResponseEntity<>(createHomeUseCase.create(command), HttpStatus.CREATED);
    }

}
