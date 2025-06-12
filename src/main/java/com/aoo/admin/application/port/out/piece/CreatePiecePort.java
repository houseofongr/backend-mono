package com.aoo.admin.application.port.out.piece;

import com.aoo.admin.application.port.in.piece.CreatePieceCommand;
import com.aoo.admin.domain.universe.piece.Piece;

public interface CreatePiecePort {
    Piece createPieceWithoutImageFile(CreatePieceCommand command);
}
