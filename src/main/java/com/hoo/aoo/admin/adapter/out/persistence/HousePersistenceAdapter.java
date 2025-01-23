package com.hoo.aoo.admin.adapter.out.persistence;

import com.hoo.aoo.admin.adapter.out.persistence.entity.HouseJpaEntity;
import com.hoo.aoo.admin.adapter.out.persistence.entity.RoomJpaEntity;
import com.hoo.aoo.admin.adapter.out.persistence.mapper.HouseMapper;
import com.hoo.aoo.admin.adapter.out.persistence.repository.HouseJpaRepository;
import com.hoo.aoo.admin.adapter.out.persistence.repository.RoomJpaRepository;
import com.hoo.aoo.admin.application.port.in.house.QueryHouseListCommand;
import com.hoo.aoo.admin.application.port.in.house.QueryHouseListResult;
import com.hoo.aoo.admin.application.port.in.house.QueryHouseResult;
import com.hoo.aoo.admin.application.port.out.house.*;
import com.hoo.aoo.admin.domain.exception.AreaLimitExceededException;
import com.hoo.aoo.admin.domain.exception.AxisLimitExceededException;
import com.hoo.aoo.admin.domain.exception.HouseRelationshipException;
import com.hoo.aoo.admin.domain.house.House;
import com.hoo.aoo.admin.domain.house.room.Room;
import com.hoo.aoo.common.application.port.in.Pagination;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.stereotype.Component;

import java.util.List;
import java.util.Map;
import java.util.Optional;

import static java.util.stream.Collectors.toList;

@Component
@RequiredArgsConstructor
public class HousePersistenceAdapter implements SaveHousePort, SearchHousePort, UpdateHousePort,  FindHousePort, DeleteHousePort {

    private final HouseJpaRepository houseJpaRepository;
    private final RoomJpaRepository roomJpaRepository;
    private final HouseMapper houseMapper;

    @Override
    public Long save(House house) {

        List<RoomJpaEntity> roomJpaEntities = house.getRooms().stream().map(houseMapper::mapToNewJpaEntity).toList();

        HouseJpaEntity houseJpaEntity = houseMapper.mapToNewJpaEntity(house, roomJpaEntities);

        houseJpaRepository.save(houseJpaEntity);

        return houseJpaEntity.getId();
    }

    public Optional<QueryHouseResult> findResult(Long id) {
        return houseJpaRepository.findById(id).map(houseMapper::mapToQueryHouseResult);
    }

    @Override
    public Optional<House> find(Long id) throws AreaLimitExceededException, AxisLimitExceededException, HouseRelationshipException {

        Optional<HouseJpaEntity> optional = houseJpaRepository.findById(id);

        if (optional.isEmpty()) return Optional.empty();

        List<RoomJpaEntity> roomJpaEntities = roomJpaRepository.findAllByHouseId(id);

        return Optional.ofNullable(houseMapper.mapToDomainEntity(optional.get(), roomJpaEntities));
    }

    @Override
    public QueryHouseListResult search(QueryHouseListCommand command) {
        Page<QueryHouseListResult.HouseInfo> houseInfoPages = houseJpaRepository.searchByCommand(command).map(QueryHouseListResult.HouseInfo::of);
        return new QueryHouseListResult(houseInfoPages.getContent(), Pagination.of(houseInfoPages));
    }

    @Override
    public void update(Long id, House house) {

        HouseJpaEntity entity = houseJpaRepository.findById(id).orElseThrow();

        entity.updateInfo(house.getId().getTitle(), house.getId().getAuthor(), house.getId().getDescription());
    }

    @Override
    public void deleteHouse(Long id) {
        roomJpaRepository.deleteAllByHouseId(id);
        houseJpaRepository.deleteById(id);
    }

}
