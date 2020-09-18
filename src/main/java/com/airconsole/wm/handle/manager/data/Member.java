package com.airconsole.wm.handle.manager.data;

import lombok.Data;
import lombok.ToString;

import javax.websocket.Session;

@Data
@ToString
public class Member {
    private Session session;
    private int is_admin;
    private String name;
    private Long in_room_id;

    public Member() {
    }

    public Member(Session session) {
        this.session = session;
    }
}
