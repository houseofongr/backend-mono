package com.hoo.aoo.admin.application.port.in.home;

import com.hoo.aoo.common.application.port.in.MessageDto;

public interface DeleteHomeUseCase {
    MessageDto deleteHome(Long id);
}
