package com.airconsole.wm.handle.processor.question;

import com.airconsole.wm.handle.data.request.QuestionReq;
import com.airconsole.wm.handle.manager.SessionManager;
import com.airconsole.wm.handle.manager.data.Room;
import com.airconsole.wm.handle.processor.BaseProcessor;

import javax.websocket.Session;

public class GetQuestionCmdPrs extends BaseProcessor {

    private QuestionReq questionReq;

    public GetQuestionCmdPrs(String cmd, Session session, String jsonRequest, Class<?> classRequest) {
        super(cmd, session, jsonRequest, classRequest);
    }

    @Override
    public void handle() throws Exception {
        Integer room_id = questionReq.getRoom_id();
        Room room = SessionManager.roomMap.get(room_id);
        if (room != null) {
            sendTo(cmd, room.getScreen().getSession());
        }
    }

    @Override
    protected boolean validateData() throws Exception {
        questionReq = (QuestionReq) objRequest;
        return true;
    }
}