package com.hoo.aoo.aar.application.port.out.home;

public interface CheckOwnerPort {
    boolean checkHome(Long userId, Long homeId);
    boolean checkRoom(Long homeId, Long roomId);
    boolean checkItem(Long userId, Long itemId);
}
