package com.aoo.admin.application.service.piece;

import com.aoo.admin.application.port.in.piece.CreatePieceCommand;
import com.aoo.admin.application.port.in.piece.CreatePieceResult;
import com.aoo.admin.application.port.out.piece.CreatePiecePort;
import com.aoo.admin.application.port.out.piece.SavePiecePort;
import com.aoo.admin.domain.universe.piece.Piece;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;

import static org.assertj.core.api.Assertions.assertThat;
import static org.mockito.Mockito.*;

class CreatePieceServiceTest {

    CreatePiecePort createPiecePort = mock();
    SavePiecePort savePiecePort = mock();
    CreatePieceService sut = new CreatePieceService(createPiecePort, savePiecePort);

    @Test
    @DisplayName("피스 생성 서비스")
    void TestCreatePieceService() {
        // given
        CreatePieceCommand command = new CreatePieceCommand(1L, 1L, "피스", "피스는 조각입니다.", 0.1f, 0.2f, 0.3f, 0.4f, false);
        Piece newPiece = Piece.create(1L, -1L, command.universeId(), command.parentSpaceId(), command.title(), command.description(), command.startX(), command.startY(), command.endX(), command.endY(), command.hidden());

        // when
        when(createPiecePort.createPieceWithoutImageFile(command)).thenReturn(newPiece);
        when(savePiecePort.save(any())).thenReturn(1L);
        CreatePieceResult result = sut.create(command);

        // then
        assertThat(result.message()).matches("\\[#\\d+]번 피스가 생성되었습니다.");
        assertThat(result.pieceId()).isEqualTo(1L);
        assertThat(result.title()).isEqualTo("피스");
        assertThat(result.description()).isEqualTo("피스는 조각입니다.");
        assertThat(result.startX()).isEqualTo(0.1f);
        assertThat(result.startY()).isEqualTo(0.2f);
        assertThat(result.endX()).isEqualTo(0.3f);
        assertThat(result.endY()).isEqualTo(0.4f);
    }
}