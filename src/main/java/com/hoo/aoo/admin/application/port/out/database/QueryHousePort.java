package com.hoo.aoo.admin.application.port.out.database;

import com.hoo.aoo.admin.application.port.in.ReadHouseListCommand;
import com.hoo.aoo.admin.domain.house.House;

import java.util.List;

public interface QueryHousePort {
    List<House> query(ReadHouseListCommand command);
}
