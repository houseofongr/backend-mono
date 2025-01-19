package com.hoo.aoo.admin.adapter.in.web.user;

import com.hoo.aoo.admin.application.port.in.user.QueryUserHomeListResult;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

@RestController
@RequiredArgsConstructor
public class GetHomeListController {

    @GetMapping("/admin/users/{userId}/homes")
    public ResponseEntity<QueryUserHomeListResult> getHomeList(@PathVariable Long userId) {
        QueryUserHomeListResult result = new QueryUserHomeListResult(List.of(
                new QueryUserHomeListResult.Home(1L, "cozy house", "leaf", "this is cozy house.", "2025. 01. 19.", "2025. 01. 19."),
                new QueryUserHomeListResult.Home(2L, "not cozy house", "leaf", "this is not cozy house.", "2025. 01. 20.", "2025. 01. 20.")
        ));

        return new ResponseEntity<>(result, HttpStatus.OK);
    }
}
