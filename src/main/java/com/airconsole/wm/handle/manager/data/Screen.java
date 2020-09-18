package com.airconsole.wm.handle.manager.data;

import lombok.Data;
import lombok.ToString;

import javax.websocket.Session;

@Data
@ToString
public class Screen {
    private Session session;
    private Long room_id;

    public Screen(Session session, Long room_id) {
        this.session = session;
        this.room_id = room_id;
    }
}
