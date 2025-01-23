package com.hoo.aoo.common.adapter;

import com.hoo.aoo.common.application.port.out.IssueIdPort;

import java.util.Random;
import java.util.random.RandomGenerator;

public class MockIdAdapter implements IssueIdPort {

    @Override
    public Long issueHouseId() {
        return Random.from(RandomGenerator.getDefault()).nextLong();
    }
}
