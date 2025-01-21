package com.hoo.aoo.admin.application.port.out.user;

import com.hoo.aoo.admin.application.port.in.user.CreateHomeCommand;
import com.hoo.aoo.admin.application.port.in.user.CreateHomeResult;

public interface SaveHomePort {
    CreateHomeResult save(CreateHomeCommand command);
}
