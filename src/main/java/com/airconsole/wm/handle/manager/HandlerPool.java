package com.airconsole.wm.handle.manager;

import com.airconsole.wm.handle.EventGatewayHandler;
import com.airconsole.wm.thread.AbsThread;

import java.util.ArrayList;
import java.util.List;

public class HandlerPool {
    private List<AbsThread> handlers = new ArrayList<>();
    private int roomEventSize;

    public HandlerPool(int RoomEventSize) {
        this.roomEventSize = RoomEventSize;
    }

    public void init() {
        for (int i = 0; i < roomEventSize; i++) {
            EventGatewayHandler handler = new EventGatewayHandler();
            handler.setName("ROOM_EVENT_HANDLER " + (i + 1));
            handlers.add(handler);
        }
    }

    public void start() {
        handlers.forEach(AbsThread::start);
    }
}
