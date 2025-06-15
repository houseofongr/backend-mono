package com.aoo.admin.application.port.out.piece;

import com.aoo.admin.application.port.in.piece.SearchPieceCommand;
import com.aoo.admin.application.port.in.piece.SearchPieceResult;
import com.aoo.admin.domain.universe.piece.Piece;

public interface FindPiecePort {
    Piece find(Long id);
    Piece findWithSounds(Long id);
    SearchPieceResult search(SearchPieceCommand command);
}
