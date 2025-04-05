package com.hoo.aoo.admin.application.port.out.universe;

import com.hoo.aoo.admin.application.port.in.universe.SearchUniverseCommand;
import com.hoo.aoo.admin.application.port.in.universe.SearchUniverseResult;

public interface FindUniversePort {
    SearchUniverseResult search(SearchUniverseCommand command);
}
