package com.hoo.aoo.admin.application.port.in;

public interface QueryHouseListUseCase {
    QueryHouseListResult getList(QueryHouseListCommand command);
}
