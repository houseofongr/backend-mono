package com.aoo.admin.application.port.in.home;

import com.aoo.common.application.port.in.MessageDto;

public interface DeleteHomeUseCase {
    MessageDto deleteHome(Long id);
}
