package com.aoo.admin.application.service.piece;

import com.aoo.admin.application.port.in.piece.CreatePieceCommand;
import com.aoo.admin.application.port.in.piece.CreatePieceResult;
import com.aoo.admin.application.port.in.piece.CreatePieceUseCase;
import com.aoo.admin.application.port.out.piece.CreatePiecePort;
import com.aoo.admin.application.port.out.piece.SavePiecePort;
import com.aoo.admin.domain.universe.piece.Piece;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
@Transactional
@RequiredArgsConstructor
public class CreatePieceService implements CreatePieceUseCase {

    private final CreatePiecePort createPiecePort;
    private final SavePiecePort savePiecePort;

    @Override
    public CreatePieceResult create(CreatePieceCommand command) {
        Piece newPiece = createPiecePort.createPieceWithoutImageFile(command);
        Long newPieceId = savePiecePort.save(newPiece);

        return CreatePieceResult.from(newPieceId, newPiece);
    }
}
