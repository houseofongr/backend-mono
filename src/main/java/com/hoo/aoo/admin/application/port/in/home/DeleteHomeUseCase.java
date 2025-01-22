package com.hoo.aoo.admin.application.port.in.home;

import com.hoo.aoo.common.adapter.in.web.MessageDto;

public interface DeleteHomeUseCase {
    MessageDto delete(Long id);
}
