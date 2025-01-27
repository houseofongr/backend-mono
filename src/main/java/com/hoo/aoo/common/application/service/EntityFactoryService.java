package com.hoo.aoo.common.application.service;

import com.hoo.aoo.aar.application.port.out.database.snsaccount.CreateSnsAccountPort;
import com.hoo.aoo.aar.application.port.out.database.user.CreateUserPort;
import com.hoo.aoo.aar.domain.user.User;
import com.hoo.aoo.aar.domain.user.snsaccount.SnsAccount;
import com.hoo.aoo.aar.domain.user.snsaccount.SnsDomain;
import com.hoo.aoo.admin.application.port.out.home.CreateHomePort;
import com.hoo.aoo.admin.application.port.out.house.CreateHousePort;
import com.hoo.aoo.admin.application.port.out.house.CreateRoomPort;
import com.hoo.aoo.admin.application.port.out.item.CreateItemPort;
import com.hoo.aoo.admin.application.port.out.soundsource.CreateSoundSourcePort;
import com.hoo.aoo.admin.domain.exception.AreaLimitExceededException;
import com.hoo.aoo.admin.domain.exception.AxisLimitExceededException;
import com.hoo.aoo.admin.domain.home.Home;
import com.hoo.aoo.admin.domain.house.House;
import com.hoo.aoo.admin.domain.house.HouseDetail;
import com.hoo.aoo.admin.domain.item.Item;
import com.hoo.aoo.admin.domain.item.Shape;
import com.hoo.aoo.admin.domain.room.Room;
import com.hoo.aoo.admin.domain.soundsource.SoundSource;
import com.hoo.aoo.common.application.port.out.IssueIdPort;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
@RequiredArgsConstructor
public class EntityFactoryService implements CreateUserPort, CreateSnsAccountPort, CreateHousePort, CreateRoomPort, CreateHomePort, CreateItemPort, CreateSoundSourcePort {

    private final IssueIdPort issueIdPort;

    @Override
    public User createUser(SnsAccount snsAccount, Boolean termsOfUseAgreement, Boolean personalInformationAgreement) {
        Long newId = issueIdPort.issueUserId();
        return User.register(newId, termsOfUseAgreement, personalInformationAgreement, snsAccount);
    }

    @Override
    public SnsAccount createSnsAccount(SnsDomain snsDomain, String snsId, String realName, String nickname, String email) {
        Long newId = issueIdPort.issueSnsAccountId();
        return SnsAccount.register(newId, snsDomain, snsId, realName, nickname, email);
    }

    @Override
    public House createHouse(HouseDetail houseDetail, Float width, Float height, Long defaultImageFileId, Long borderImageFileId, List<Room> rooms) throws AreaLimitExceededException {
        Long newId = issueIdPort.issueHouseId();
        return House.create(newId, houseDetail, width, height, defaultImageFileId, borderImageFileId, rooms);
    }

    @Override
    public Room createRoom(String name, Float x, Float y, Float z, Float width, Float height, Long imageFileId) throws AxisLimitExceededException, AreaLimitExceededException {
        Long newId = issueIdPort.issueRoomId();
        return Room.create(newId, name, x, y, z, width, height, imageFileId);
    }

    @Override
    public Home createHome(House house, com.hoo.aoo.admin.domain.user.User user) {
        Long newId = issueIdPort.issueHomeId();
        return Home.create(newId, house, user);
    }

    @Override
    public Item createItem(Long userId, Long roomId, String name, Shape shape) {
        Long newId = issueIdPort.issueItemId();
        return Item.create(newId, userId, roomId, name, shape);
    }

    @Override
    public SoundSource createSoundSource(Long itemId, Long audioFileId, String name, String description, Boolean active) {
        Long newId = issueIdPort.issueSoundSourceId();
        return SoundSource.create(newId, itemId, audioFileId, name, description, active);
    }
}
