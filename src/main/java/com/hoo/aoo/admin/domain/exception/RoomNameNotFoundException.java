package com.hoo.aoo.admin.domain.exception;

public class RoomNameNotFoundException extends Exception {
    public RoomNameNotFoundException(String houseTitle, String name) {
        super("house '" + houseTitle + "' doesn't have room named '" + name + "'.");
    }
}
