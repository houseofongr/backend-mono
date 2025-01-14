package com.hoo.aoo.admin.adapter.in.web.house;

import com.hoo.aoo.admin.adapter.out.persistence.SearchType;
import com.hoo.aoo.admin.application.port.in.ReadHouseListCommand;
import com.hoo.aoo.admin.application.port.in.ReadHouseListResult;
import com.hoo.aoo.admin.application.port.in.ReadHouseListUseCase;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Pageable;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequiredArgsConstructor
public class ReadHouseListController {

    private final ReadHouseListUseCase readHouseListUseCase;

    @GetMapping("/admin/houses")
    public ResponseEntity<ReadHouseListResult> getList(Pageable pageable,
                                                       @RequestParam(required = false) SearchType searchType,
                                                       @RequestParam(required = false) String keyword) {

        ReadHouseListCommand command = new ReadHouseListCommand(pageable, searchType, keyword);
        ReadHouseListResult result = readHouseListUseCase.getList(command);

        return new ResponseEntity<>(result, HttpStatus.OK);
    }
}
