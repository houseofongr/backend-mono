package com.hoo.aoo.admin.application.port.out.home;

import com.hoo.aoo.admin.application.port.in.home.CreateHomeCommand;
import com.hoo.aoo.admin.application.port.in.home.CreateHomeResult;
import com.hoo.aoo.admin.domain.home.Home;

public interface SaveHomePort {
    CreateHomeResult save(CreateHomeCommand command, Home home);
}
