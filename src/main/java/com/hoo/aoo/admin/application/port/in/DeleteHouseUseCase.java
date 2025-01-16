package com.hoo.aoo.admin.application.port.in;

import com.hoo.aoo.common.adapter.in.web.MessageDto;

public interface DeleteHouseUseCase {
    MessageDto delete(Long id);
}
