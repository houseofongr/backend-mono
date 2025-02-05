package com.hoo.aoo.aar.adapter.out.persistence;

import com.hoo.aoo.aar.adapter.out.persistence.mapper.HomeMapper;
import com.hoo.aoo.aar.application.port.in.home.*;
import com.hoo.aoo.aar.application.port.out.home.CheckOwnerPort;
import com.hoo.aoo.aar.application.port.out.home.QueryHomePort;
import com.hoo.aoo.common.adapter.out.persistence.repository.HomeJpaRepository;
import com.hoo.aoo.common.adapter.out.persistence.repository.ItemJpaRepository;
import com.hoo.aoo.common.adapter.out.persistence.repository.RoomJpaRepository;
import com.hoo.aoo.common.adapter.out.persistence.repository.SoundSourceJpaRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Component;

@Component("AARHomePersistenceAdapter")
@RequiredArgsConstructor
public class HomePersistenceAdapter implements QueryHomePort, CheckOwnerPort {

    private final HomeJpaRepository homeJpaRepository;
    private final RoomJpaRepository roomJpaRepository;
    private final ItemJpaRepository itemJpaRepository;
    private final SoundSourceJpaRepository soundSourceJpaRepository;
    private final HomeMapper homeMapper;

    @Override
    public QueryUserHomesResult queryUserHomes(Long userId) {
        return homeMapper.mapToQueryUserHomes(homeJpaRepository.findAllByUserId(userId));
    }

    @Override
    public QueryHomeRoomsResult queryHomeRooms(Long homeId) {
        return homeMapper.mapToQueryHomeRooms(homeJpaRepository.findById(homeId).orElseThrow());
    }

    @Override
    public QueryRoomItemsResult queryRoomItems(Long roomId) {
        return homeMapper.mapToQueryRoomItems(roomJpaRepository.findById(roomId).orElseThrow());
    }

    @Override
    public QueryItemSoundSourcesResult queryItemSoundSources(Long itemId) {
        return homeMapper.mapToQueryItemSoundSources(itemJpaRepository.findById(itemId).orElseThrow());
    }

    @Override
    public QuerySoundSourceResult querySoundSource(Long soundSourceId) {
        return homeMapper.mapToQuerySoundSource(soundSourceJpaRepository.findById(soundSourceId).orElseThrow());
    }

    @Override
    public QuerySoundSourcesPathResult querySoundSourcesPath(Long userId) {
        return homeMapper.mapToQuerySoundSourcesPathResult(soundSourceJpaRepository.findAllByIdWithPathEntity(userId));
    }

    @Override
    public boolean checkHome(Long userId, Long homeId) {
        return homeJpaRepository.existsByUserIdAndId(userId, homeId);
    }

    @Override
    public boolean checkRoom(Long homeId, Long roomId) {
        Long houseId = homeJpaRepository.findById(homeId).orElseThrow().getHouse().getId();
        return roomJpaRepository.existsByHouseIdAndId(houseId, roomId);
    }

    @Override
    public boolean checkItem(Long userId, Long itemId) {
        return itemJpaRepository.existsByUserIdAndItemId(userId, itemId);
    }

    @Override
    public boolean checkSoundSource(Long userId, Long soundSourceId) {
        return soundSourceJpaRepository.existsByUserIdAndId(userId, soundSourceId);
    }
}
