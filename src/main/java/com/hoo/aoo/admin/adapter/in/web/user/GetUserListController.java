package com.hoo.aoo.admin.adapter.in.web.user;

import com.hoo.aoo.admin.application.port.in.user.QueryUserResult;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.PageImpl;
import org.springframework.data.domain.PageRequest;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

@RestController
@RequiredArgsConstructor
public class GetUserListController {

    @GetMapping("/admin/users")
    public ResponseEntity<QueryUserResult> getUsers() {
        QueryUserResult result = new QueryUserResult(new PageImpl<>(
                List.of(new QueryUserResult.User(1L, "남상엽", "test@example.com", "010-0000-1111", "2024. 12. 19."),
                        new QueryUserResult.User(2L, "윤선영", "test@example.com", "010-1111-2222", "2024. 12. 19."),
                        new QueryUserResult.User(3L, "주아랑", "test@example.com", "010-2222-3333", "2024. 12. 19.")
                ),
                PageRequest.of(1, 10), 3
        ));
        return new ResponseEntity<>(result, HttpStatus.OK);
    }
}
