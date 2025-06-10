package com.aoo.admin.application.service.space;

import com.aoo.admin.application.port.in.space.DeleteSpaceResult;
import com.aoo.admin.application.port.out.piece.DeletePiecePort;
import com.aoo.admin.application.port.out.space.DeleteSpacePort;
import com.aoo.admin.application.port.out.space.FindSpacePort;
import com.aoo.admin.application.port.out.universe.FindUniversePort;
import com.aoo.admin.domain.universe.MockTreeInfo;
import com.aoo.admin.domain.universe.TraversalComponents;
import com.aoo.admin.domain.universe.space.Space;
import com.aoo.common.application.service.MockEntityFactoryService;
import com.aoo.file.application.port.in.DeleteFileUseCase;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;

import static org.assertj.core.api.Assertions.assertThat;
import static org.mockito.Mockito.*;

class DeleteSpaceServiceTest {

    FindSpacePort findSpacePort = mock();
    FindUniversePort findUniversePort = mock();
    DeleteFileUseCase deleteFileUseCase = mock();
    DeleteSpacePort deleteSpacePort = mock();
    DeletePiecePort deletePiecePort = mock();

    DeleteSpaceService sut = new DeleteSpaceService(findSpacePort, findUniversePort, deleteFileUseCase, deleteSpacePort, deletePiecePort);

    @Test
    @DisplayName("스페이스 삭제 서비스")
    void testDeleteSpaceService() {
        // given
        Long spaceId = 2L;
        Space space = MockEntityFactoryService.getParentSpace();
        TraversalComponents traversalComponent = MockTreeInfo.getTraversalComponent();

        // when
        when(findSpacePort.findUniverseId(spaceId)).thenReturn(1L);
        when(findUniversePort.findTreeComponents(1L)).thenReturn(traversalComponent);
        DeleteSpaceResult result = sut.delete(spaceId);

        // then
        verify(deleteSpacePort, times(1)).deleteAll(anyList());
        verify(deletePiecePort, times(1)).deleteAll(anyList());
        verify(deleteFileUseCase, times(1)).deleteFiles(anyList());
        assertThat(result.message()).matches("\\[#\\d+]번 스페이스가 삭제되었습니다.");
        assertThat(result.deletedAudioFileIds()).isEmpty();
        assertThat(result.deletedImageFileIds()).hasSize(4);
        assertThat(result.deletedSpaceIds()).hasSize(3)
                .anyMatch(id -> id.equals(2L))
                .anyMatch(id -> id.equals(4L))
                .anyMatch(id -> id.equals(5L));

        assertThat(result.deletedPieceIds()).hasSize(4)
                .anyMatch(id -> id.equals(4L))
                .anyMatch(id -> id.equals(5L))
                .anyMatch(id -> id.equals(6L))
                .anyMatch(id -> id.equals(7L));
    }

}