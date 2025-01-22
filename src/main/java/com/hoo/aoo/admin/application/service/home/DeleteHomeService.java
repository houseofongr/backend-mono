package com.hoo.aoo.admin.application.service.home;

import com.hoo.aoo.admin.application.port.in.home.DeleteHomeUseCase;
import com.hoo.aoo.admin.application.port.out.home.DeleteHomePort;
import com.hoo.aoo.common.adapter.in.web.MessageDto;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
@RequiredArgsConstructor
public class DeleteHomeService implements DeleteHomeUseCase {

    private final DeleteHomePort deleteHomePort;

    @Override
    @Transactional
    public MessageDto delete(Long id) {
        deleteHomePort.delete(id);
        return new MessageDto(id + "번 홈이 삭제되었습니다.");
    }
}
