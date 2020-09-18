package com.airconsole.wm.handle.manager.data;

import lombok.Data;
import lombok.ToString;

import javax.websocket.Session;
import java.util.Map;

@Data
@ToString
public class Room {
    private Long room_id;
    private Screen screen;
    private int is_open;
    private Map<Session, Member> memberList;
    private long create_time;
    private int is_closed;
    private int max_member = 1;

    public Room(){
        this.create_time = System.currentTimeMillis();
    }

    public boolean addMember(Member member) {
        if (member == null) {
            return false;
        }
        if (memberList != null && memberList.size() < max_member) {
            if (memberList.size() == 0) {
                member.setIs_admin(1);
            }
            memberList.put(member.getSession(), member);
            return true;
        }
        return false;
    }

    public boolean removeMember(Session session) {
        if (session == null) {
            return false;
        }
        if (memberList != null && !memberList.isEmpty()) {
            memberList.remove(session);
        }
        return false;
    }

    public boolean removeMember(Member member) {
        if (member == null) {
            return false;
        }
        if (memberList != null && !memberList.isEmpty()) {
            memberList.remove(member);
            if (member.getIs_admin() == 1 && !memberList.isEmpty()) {
                memberList.get(0).setIs_admin(1);
            }
            return true;
        }
        return true;
    }

    public void setIs_closed(int is_closed) {
        if (is_closed == 1) {
            memberList = null;
        }
    }

    public Screen getScreen() {
        return screen;
    }
}
