package com.hoo.aoo.common.adapter.out.persistence.repository;

import com.hoo.aoo.aar.application.port.in.home.QuerySoundSourcesPathCommand;
import com.hoo.aoo.admin.application.port.in.soundsource.QuerySoundSourceListCommand;
import com.hoo.aoo.common.adapter.out.persistence.entity.SoundSourceJpaEntity;
import org.springframework.data.domain.Page;

import java.util.List;

public interface SoundSourceQueryDslRepository {
    boolean existsByUserIdAndId(Long userId, Long soundSourceId);
    Page<SoundSourceJpaEntity> findAllWithRelatedEntity(QuerySoundSourceListCommand command);
    Page<SoundSourceJpaEntity> findAllActivatedByIdWithPathEntity(QuerySoundSourcesPathCommand command);
}
