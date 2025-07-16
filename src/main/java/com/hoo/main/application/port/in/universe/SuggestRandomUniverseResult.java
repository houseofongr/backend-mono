package com.hoo.main.application.port.in.universe;

import com.hoo.admin.domain.universe.Universe;

import java.util.List;

public record SuggestRandomUniverseResult(
        List<UniverseSidebarInfo> universes
) {
    public record UniverseSidebarInfo(
            Long id,
            Long thumbnailId,
            String title,
            String author
    ) {
        public static UniverseSidebarInfo from(Universe universe) {
            return new UniverseSidebarInfo(
                    universe.getId(),
                    universe.getFileInfo().getThumbnailId(),
                    universe.getBasicInfo().getTitle(),
                    universe.getAuthorInfo().getNickname()
            );
        }
    }
}
