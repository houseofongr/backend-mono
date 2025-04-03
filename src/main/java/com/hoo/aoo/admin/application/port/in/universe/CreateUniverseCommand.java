package com.hoo.aoo.admin.application.port.in.universe;

import com.hoo.aoo.admin.domain.universe.Category;
import com.hoo.aoo.admin.domain.universe.PublicStatus;

import java.util.List;

public record CreateUniverseCommand(
        String title,
        String description,
        List<String> tag,
        Category category,
        PublicStatus publicStatus
) {

}
