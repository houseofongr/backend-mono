package com.hoo.main.adapter.in.web.user;

import com.hoo.main.application.port.in.user.SearchUserNicknameResult;
import com.hoo.main.application.port.in.user.SearchUserNicknameUseCase;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequiredArgsConstructor
public class GetUserNicknameExistController {

    private final SearchUserNicknameUseCase useCase;

    @GetMapping("/users/nickname/{nickname}")
    ResponseEntity<SearchUserNicknameResult> search(@PathVariable String nickname) {
        return ResponseEntity.ok(useCase.search(nickname));
    }
}
