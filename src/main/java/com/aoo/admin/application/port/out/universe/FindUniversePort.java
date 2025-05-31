package com.aoo.admin.application.port.out.universe;

import com.aoo.admin.application.port.in.universe.SearchUniverseCommand;
import com.aoo.admin.application.port.in.universe.SearchUniverseResult;
import com.aoo.admin.domain.universe.Universe;

import java.util.Optional;

public interface FindUniversePort {
    Optional<Universe> load(Long id);
    SearchUniverseResult search(SearchUniverseCommand command);
    SearchUniverseResult.UniverseInfo find(Long id);
}
