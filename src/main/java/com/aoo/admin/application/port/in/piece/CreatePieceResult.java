package com.aoo.admin.application.port.in.piece;

import com.aoo.admin.domain.universe.space.element.Piece;

public record CreatePieceResult(
        String message,
        Long pieceId,
        String title,
        String description,
        Float startX,
        Float startY,
        Float endX,
        Float endY
) {
    public static CreatePieceResult from(Piece piece, Long pieceId) {
        return new CreatePieceResult(
                String.format("[#%d]번 피스가 생성되었습니다.", pieceId),
                pieceId,
                piece.getBasicInfo().getTitle(),
                piece.getBasicInfo().getDescription(),
                piece.getPosInfo().getSx(),
                piece.getPosInfo().getSy(),
                piece.getPosInfo().getEx(),
                piece.getPosInfo().getEy()
        );
    }
}
