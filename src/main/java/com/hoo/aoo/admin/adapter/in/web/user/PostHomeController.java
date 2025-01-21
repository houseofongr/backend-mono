package com.hoo.aoo.admin.adapter.in.web.user;

import com.hoo.aoo.admin.application.port.in.user.CreateHomeCommand;
import com.hoo.aoo.admin.application.port.in.user.CreateHomeResult;
import com.hoo.aoo.common.adapter.in.web.MessageDto;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequiredArgsConstructor
public class PostHomeController {

    @PostMapping("/admin/homes")
    ResponseEntity<CreateHomeResult> create(@RequestBody CreateHomeCommand command) {
        return new ResponseEntity<>(new CreateHomeResult(1L), HttpStatus.CREATED);
    }

}
