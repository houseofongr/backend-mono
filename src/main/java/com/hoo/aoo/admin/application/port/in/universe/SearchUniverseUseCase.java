package com.hoo.aoo.admin.application.port.in.universe;

public interface SearchUniverseUseCase {
    SearchUniverseResult search(SearchUniverseCommand command);
    SearchUniverseResult.UniverseInfo search(Long universeId);
}
