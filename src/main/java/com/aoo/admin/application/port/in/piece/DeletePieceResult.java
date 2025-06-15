package com.aoo.admin.application.port.in.piece;

import java.util.List;

public record DeletePieceResult(
    String message,
    List<Long> deletedSoundIds,
    List<Long> deletedImageFileIds,
    List<Long> deletedAudioFileIds
) {
}
