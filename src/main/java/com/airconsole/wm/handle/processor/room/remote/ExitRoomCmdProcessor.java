package com.airconsole.wm.handle.processor.room.remote;

import com.airconsole.wm.dictionary.ErrorCode;
import com.airconsole.wm.handle.data.BaseResponse;
import com.airconsole.wm.handle.data.request.RoomReq;
import com.airconsole.wm.handle.data.response.RoomInfoResp;
import com.airconsole.wm.handle.manager.SessionManager;
import com.airconsole.wm.handle.manager.data.Room;
import com.airconsole.wm.handle.processor.BaseProcessor;

import javax.websocket.Session;

public class ExitRoomCmdProcessor extends BaseProcessor {

    private RoomReq roomReq;

    public ExitRoomCmdProcessor(String cmd, Session session, String jsonRequest, Class<?> classRequest) {
        super(cmd, session, jsonRequest, classRequest);
    }

    @Override
    public void handle() throws Exception {
        boolean isSuccess = false;
        Room room = null;
        if (roomReq != null && roomReq.getRoom_id() != null) {
            room = SessionManager.roomMap.get(roomReq.getRoom_id());
            isSuccess = room.removeMember(session);
        } else {
            for (Room temp : SessionManager.roomMap.values()) {
                isSuccess = temp.removeMember(session);
            }
        }
        if (room == null) {
            return;
        }
        if (isSuccess) {
            send(new BaseResponse(ErrorCode.SUCCESS, cmd));

            RoomInfoResp screenResp = new RoomInfoResp();
            screenResp.setMember_list(room.getMemberList().values());
            sendTo(screenResp, room.getScreen().getSession());
        } else {
            send(new BaseResponse(ErrorCode.FAILED, cmd));
        }
    }

    @Override
    protected boolean validateData() throws Exception {
        roomReq = (RoomReq) objRequest;
        return true;
    }
}
