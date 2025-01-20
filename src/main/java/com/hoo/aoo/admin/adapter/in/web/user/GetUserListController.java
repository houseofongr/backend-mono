package com.hoo.aoo.admin.adapter.in.web.user;

import com.hoo.aoo.aar.domain.account.SnsDomain;
import com.hoo.aoo.admin.application.port.in.user.QueryUserInfoResult;
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
    public ResponseEntity<QueryUserInfoResult> getUsers() {
        QueryUserInfoResult result = new QueryUserInfoResult(new PageImpl<>(
                List.of(new QueryUserInfoResult.UserInfo(1L, "남상엽","leaf", "010-0000-1111", "2025. 01. 20.", List.of(new QueryUserInfoResult.SnsAccountInfo(SnsDomain.KAKAO, "test@example.com"))),
                        new QueryUserInfoResult.UserInfo(2L, "윤선영", "young", "010-1111-2222", "2024. 12. 19.",List.of(new QueryUserInfoResult.SnsAccountInfo(SnsDomain.KAKAO, "test@example.com"))),
                        new QueryUserInfoResult.UserInfo(3L, "주아랑", "rang", "010-2222-3333", "2024. 12. 19.",List.of(new QueryUserInfoResult.SnsAccountInfo(SnsDomain.KAKAO, "test@example.com")))
                ),
                PageRequest.of(1, 10), 3
        ));
        return new ResponseEntity<>(result, HttpStatus.OK);
    }
}
