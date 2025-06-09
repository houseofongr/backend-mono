package com.aoo.admin.application.port.out.universe;

import com.aoo.admin.application.port.in.universe.SearchUniverseCommand;
import com.aoo.admin.application.port.in.universe.SearchUniverseResult;
import com.aoo.admin.domain.universe.TraversalComponents;
import com.aoo.admin.domain.universe.Universe;

public interface FindUniversePort {
    Universe load(Long id);

    SearchUniverseResult search(SearchUniverseCommand command);

    SearchUniverseResult.UniverseDetailInfo find(Long id);

    TraversalComponents findTreeComponents(Long id);
}
