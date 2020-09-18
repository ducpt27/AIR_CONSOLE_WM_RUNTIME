package com.airconsole.wm.handle.manager;

public class RoomManager {
    private static long room_id = 123456;
    public static synchronized Long getNewRoomId(){
        return room_id++;
    }
}