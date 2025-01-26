package com.hoo.aoo.admin.application.service.item;

import com.hoo.aoo.admin.application.port.in.item.QueryItemResult;
import com.hoo.aoo.admin.application.port.in.item.QueryItemUseCase;
import com.hoo.aoo.admin.application.port.out.item.FindItemPort;
import com.hoo.aoo.admin.application.service.AdminErrorCode;
import com.hoo.aoo.admin.application.service.AdminException;
import com.hoo.aoo.admin.domain.item.Item;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
@RequiredArgsConstructor
public class QueryItemService implements QueryItemUseCase {

    private final FindItemPort findItemPort;

    @Override
    @Transactional(readOnly = true)
    public QueryItemResult queryItem(Long id) {
        Item item = findItemPort.load(id)
                .orElseThrow(() -> new AdminException(AdminErrorCode.ITEM_NOT_FOUND));

        return QueryItemResult.of(item);
    }
}
