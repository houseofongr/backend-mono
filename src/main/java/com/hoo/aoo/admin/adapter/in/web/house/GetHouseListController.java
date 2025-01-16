package com.hoo.aoo.admin.adapter.in.web.house;

import com.hoo.aoo.admin.adapter.out.persistence.SearchType;
import com.hoo.aoo.admin.application.port.in.QueryHouseListCommand;
import com.hoo.aoo.admin.application.port.in.QueryHouseListResult;
import com.hoo.aoo.admin.application.port.in.QueryHouseListUseCase;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Pageable;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequiredArgsConstructor
public class GetHouseListController {

    private final QueryHouseListUseCase queryHouseListUseCase;

    @GetMapping("/admin/houses")
    public ResponseEntity<QueryHouseListResult> getList(Pageable pageable,
                                                        @RequestParam(required = false) SearchType searchType,
                                                        @RequestParam(required = false) String keyword) {

        QueryHouseListCommand command = new QueryHouseListCommand(pageable, searchType, keyword);
        QueryHouseListResult result = queryHouseListUseCase.getList(command);

        return new ResponseEntity<>(result, HttpStatus.OK);
    }
}
