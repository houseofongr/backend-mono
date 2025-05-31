package com.aoo.admin.application.service.universe;

import com.aoo.admin.application.port.in.universe.DeleteUniverseUseCase;
import com.aoo.admin.application.port.out.universe.DeleteUniversePort;
import com.aoo.admin.application.port.out.universe.FindUniversePort;
import com.aoo.admin.application.service.AdminErrorCode;
import com.aoo.admin.application.service.AdminException;
import com.aoo.admin.domain.universe.Universe;
import com.aoo.common.application.port.in.MessageDto;
import com.aoo.file.application.port.in.DeleteFileUseCase;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
@Transactional
@RequiredArgsConstructor
public class DeleteUniverseService implements DeleteUniverseUseCase {

    private final FindUniversePort findUniversePort;
    private final DeleteFileUseCase deleteFileUseCase;
    private final DeleteUniversePort deleteUniversePort;


    @Override
    public MessageDto delete(Long id) {

        Universe universe = findUniversePort.load(id).orElseThrow(() -> new AdminException(AdminErrorCode.UNIVERSE_NOT_FOUND));

        deleteFileUseCase.deleteFile(universe.getThumbnailId());
        deleteFileUseCase.deleteFile(universe.getThumbMusicId());

        deleteUniversePort.delete(universe);

        return new MessageDto(id + "번 유니버스가 삭제되었습니다.");
    }
}
