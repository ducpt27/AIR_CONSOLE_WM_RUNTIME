package com.airconsole.wm.handle.processor.room.remote;

import com.airconsole.wm.dictionary.ErrorCode;
import com.airconsole.wm.handle.data.BaseResponse;
import com.airconsole.wm.handle.data.response.RoomInfoResp;
import com.airconsole.wm.handle.manager.RoomManager;
import com.airconsole.wm.handle.manager.SessionManager;
import com.airconsole.wm.handle.manager.data.Room;
import com.airconsole.wm.handle.manager.data.Screen;
import com.airconsole.wm.handle.processor.BaseProcessor;

import javax.websocket.Session;
import java.io.IOException;
import java.util.HashMap;

public class NewRoomCmdProcessor extends BaseProcessor {

    public NewRoomCmdProcessor(String cmd, Session session) {
        super(cmd, session);
    }

    @Override
    public void handle() throws IOException {
        Long roomId = RoomManager.getNewRoomId();

        Room room = new Room();
        room.setRoom_id(roomId);
        room.setScreen(new Screen(session, roomId));
        room.setMemberList(new HashMap<>());

        SessionManager.roomMap.put(roomId, room);
        BaseResponse baseResponse = new BaseResponse(ErrorCode.SUCCESS, cmd, new RoomInfoResp(roomId));
        send(baseResponse);
    }
}
