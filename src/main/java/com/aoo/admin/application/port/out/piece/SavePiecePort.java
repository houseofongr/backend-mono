package com.aoo.admin.application.port.out.piece;

import com.aoo.admin.domain.universe.space.element.Piece;

public interface SavePiecePort {
    Long save(Piece piece);
}
