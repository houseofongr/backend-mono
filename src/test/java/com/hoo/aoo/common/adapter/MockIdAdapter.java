package com.hoo.aoo.common.adapter;

import com.hoo.aoo.common.application.port.out.IssueIdPort;

import java.util.Random;
import java.util.random.RandomGenerator;

public class MockIdAdapter implements IssueIdPort {

    @Override
    public Long issueHouseId() {
        return Random.from(RandomGenerator.getDefault()).nextLong();
    }

    @Override
    public Long issueRoomId() {
        return Random.from(RandomGenerator.getDefault()).nextLong();
    }

    @Override
    public Long issueHomeId() {
        return Random.from(RandomGenerator.getDefault()).nextLong();
    }

    @Override
    public Long issueItemId() {
        return Random.from(RandomGenerator.getDefault()).nextLong();
    }

    @Override
    public Long issueSoundSourceId() {
        return Random.from(RandomGenerator.getDefault()).nextLong();
    }

    @Override
    public Long issueDeletedUserId() {
        return Random.from(RandomGenerator.getDefault()).nextLong();
    }

    @Override
    public Long issueUniverseId() {
        return Random.from(RandomGenerator.getDefault()).nextLong();
    }

    @Override
    public Long issueUserId() {
        return Random.from(RandomGenerator.getDefault()).nextLong();
    }

    @Override
    public Long issueSnsAccountId() {
        return Random.from(RandomGenerator.getDefault()).nextLong();
    }
}
