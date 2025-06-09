package com.aoo.admin.application.port.out.piece;

import com.aoo.admin.domain.universe.space.piece.Piece;

public interface FindPiecePort {
    Piece find(Long pieceId);
}
