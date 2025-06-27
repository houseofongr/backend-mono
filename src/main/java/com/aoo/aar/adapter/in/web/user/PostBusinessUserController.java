package com.aoo.aar.adapter.in.web.user;

import com.aoo.aar.application.port.in.user.CreateTempBusinessUserCommand;
import com.aoo.aar.application.port.in.user.CreateTempBusinessUserResult;
import com.aoo.aar.application.port.in.user.CreateTempBusinessUserUseCase;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;

@Controller
@RequiredArgsConstructor
public class PostBusinessUserController {

    private final CreateTempBusinessUserUseCase useCase;

    @PostMapping("/aar/users/business")
    ResponseEntity<CreateTempBusinessUserResult> create(@RequestBody CreateTempBusinessUserCommand command) {
        return new ResponseEntity<>(useCase.create(command), HttpStatus.CREATED);
    }
}
