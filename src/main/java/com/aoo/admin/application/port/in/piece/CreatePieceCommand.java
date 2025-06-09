package com.aoo.admin.application.port.in.piece;

public record CreatePieceCommand(
        Long universeId,
        Long parentSpaceId,
        String title,
        String description,
        Float startX,
        Float startY,
        Float endX,
        Float endY
) {
}
