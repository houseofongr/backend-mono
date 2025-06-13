package com.aoo.common.adapter.out.persistence.repository;

import com.aoo.admin.application.port.in.piece.SearchPieceCommand;
import com.aoo.common.adapter.out.persistence.entity.SoundJpaEntity;
import org.springframework.data.domain.Page;

public interface PieceQueryDslRepository {
    Page<SoundJpaEntity> searchAll(SearchPieceCommand command);
}
