package com.aoo.admin.application.service.piece;

import com.aoo.admin.application.port.in.piece.DeletePieceResult;
import com.aoo.admin.application.port.in.piece.DeletePieceUseCase;
import com.aoo.admin.application.port.out.piece.DeletePiecePort;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
@Transactional
@RequiredArgsConstructor
public class DeletePieceService implements DeletePieceUseCase {

    private final DeletePiecePort deletePiecePort;

    // TODO : 사운드 및 이미지, 음원 삭제
    @Override
    public DeletePieceResult delete(Long pieceId) {
        deletePiecePort.delete(pieceId);
        return new DeletePieceResult(String.format("[#%d]번 피스가 삭제되었습니다.", pieceId));
    }
}
