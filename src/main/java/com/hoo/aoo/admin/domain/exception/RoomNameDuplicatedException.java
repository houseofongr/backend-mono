package com.hoo.aoo.admin.domain.exception;

public class RoomNameDuplicatedException extends Exception {
    public RoomNameDuplicatedException(String houseTitle, String name) {
        super("house '" + houseTitle + "' already has room named '" + name + "'.");
    }
}
