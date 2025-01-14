package com.hoo.aoo.admin.domain.exception;

import com.hoo.aoo.admin.domain.room.RoomId;

public class RoomDuplicatedException extends Exception {
    public RoomDuplicatedException(RoomId roomId) {
        super("Room name " + roomId.getName() + " is duplicated.");
    }
}
