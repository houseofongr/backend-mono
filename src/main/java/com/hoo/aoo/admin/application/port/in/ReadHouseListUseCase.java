package com.hoo.aoo.admin.application.port.in;

public interface ReadHouseListUseCase {
    ReadHouseListResult getList(ReadHouseListCommand command);
}
