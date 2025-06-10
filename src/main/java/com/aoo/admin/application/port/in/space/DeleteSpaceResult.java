package com.aoo.admin.application.port.in.space;

import java.util.List;

public record DeleteSpaceResult(
        String message,
        List<Long> deletedSpaceIds,
        List<Long> deletedPieceIds,
        List<Long> deletedImageFileIds,
        List<Long> deletedAudioFileIds
) {
}
