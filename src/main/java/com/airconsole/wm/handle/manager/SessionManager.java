package com.airconsole.wm.handle.manager;

import com.airconsole.wm.handle.manager.data.Room;
import java.util.concurrent.ConcurrentHashMap;

public class SessionManager {
    public static ConcurrentHashMap<Long, Room> roomMap = new ConcurrentHashMap<>();
}