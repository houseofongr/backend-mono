package com.hoo.aoo.admin.domain.exception;

import com.hoo.aoo.admin.domain.house.HouseId;

public class RoomNameNotFoundException extends Exception {
    public RoomNameNotFoundException(String houseTitle, String name) {
        super("house '" + houseTitle + "' doesn't have room named '" + name + "'.");
    }
}
