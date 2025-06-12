package com.aoo.admin.application.port.out.piece;

import com.aoo.admin.domain.universe.piece.Piece;

public interface SavePiecePort {
    Long save(Piece piece);
}
