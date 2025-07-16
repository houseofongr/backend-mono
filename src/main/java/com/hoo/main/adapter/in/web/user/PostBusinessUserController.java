package com.hoo.main.adapter.in.web.user;

import com.hoo.main.application.port.in.user.CreateBusinessUserCommand;
import com.hoo.main.application.port.in.user.CreateBusinessUserResult;
import com.hoo.main.application.port.in.user.CreateBusinessUserUseCase;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;

@Controller
@RequiredArgsConstructor
public class PostBusinessUserController {

    private final CreateBusinessUserUseCase useCase;

    @PostMapping("/users/business")
    ResponseEntity<CreateBusinessUserResult> create(@RequestBody CreateBusinessUserCommand command) {
        return new ResponseEntity<>(useCase.create(command), HttpStatus.CREATED);
    }
}
