package com.airconsole.wm.handle.processor.room.remote;

import com.airconsole.wm.dictionary.ErrorCode;
import com.airconsole.wm.dictionary.KeySendEvent;
import com.airconsole.wm.handle.data.BaseResponse;
import com.airconsole.wm.handle.data.request.RoomReq;
import com.airconsole.wm.handle.manager.SessionManager;
import com.airconsole.wm.handle.manager.data.Member;
import com.airconsole.wm.handle.manager.data.Room;
import com.airconsole.wm.handle.processor.BaseProcessor;

import javax.websocket.Session;

public class JoinRoomCmdProcessor extends BaseProcessor {

    private RoomReq joinRoomReq;

    public JoinRoomCmdProcessor(String cmd, Session session, String jsonRequest, Class<?> classRequest) {
        super(cmd, session, jsonRequest, classRequest);
    }

    @Override
    public void handle() throws Exception {
        Long roomId = SessionManager.roomMap
                .keySet()
                .stream()
                .filter(p -> p.equals(joinRoomReq.getRoom_id()))
                .findFirst()
                .orElse(null);
        logger.info(SessionManager.roomMap);

        SessionManager.roomMap.values().forEach(p -> {
            Member temp = p.getMemberList().get(session);
            if (temp != null) {
                temp.setIn_room_id(null);
                p.getMemberList().remove(session);
            }
        });

        if (roomId == null) {
            send(new BaseResponse(ErrorCode.NOT_FOUND, cmd));
            return;
        }

        Member member = new Member(session);

        Room room = SessionManager.roomMap.get(roomId);
        if (room == null) {
            send(new BaseResponse(ErrorCode.ROOM_NOT_FOUND, cmd));
            return;
        }

        if (room.addMember(member)) {
            member.setIn_room_id(room.getRoom_id());
            send(new BaseResponse(ErrorCode.SUCCESS, cmd));
            sendTo(new BaseResponse(KeySendEvent.NEW_MEMBER_JOIN, room.getMemberList()), room.getScreen().getSession());
        } else {
            send(new BaseResponse(ErrorCode.ROOM_FULL, cmd));
        }
    }

    @Override
    protected boolean validateData() throws Exception {
        joinRoomReq = (RoomReq) objRequest;
        return true;
    }
}
