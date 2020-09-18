package com.airconsole.wm.handle;

import com.airconsole.wm.dictionary.ErrorCode;
import com.airconsole.wm.event.EventGateway;
import com.airconsole.wm.dictionary.KeyEvent;
import com.airconsole.wm.handle.data.BaseResponse;
import com.airconsole.wm.handle.data.EmitData;
import com.airconsole.wm.handle.data.request.QuestionReq;
import com.airconsole.wm.handle.data.request.RoomReq;
import com.airconsole.wm.handle.processor.question.AnswerQuestionCmdPrs;
import com.airconsole.wm.handle.processor.BaseProcessor;
import com.airconsole.wm.handle.processor.question.GetQuestionCmdPrs;
import com.airconsole.wm.handle.processor.room.remote.NewRoomCmdProcessor;
import com.airconsole.wm.handle.processor.room.remote.ExitRoomCmdProcessor;
import com.airconsole.wm.handle.processor.room.remote.JoinRoomCmdProcessor;
import com.airconsole.wm.thread.AbsThread;
import com.airconsole.wm.utils.GsonUtils;

import javax.websocket.Session;

public class EventGatewayHandler extends AbsThread {
    private EmitData emitData;

    public EventGatewayHandler() {
        super(true, 5);
    }

    @Override
    protected void execute() throws Exception {
        emitData = EventGateway.emitDataQueue.take();

        String cmd = emitData.getCmd();
        String data = emitData.getData();
        Session session = emitData.getSession();
        BaseProcessor processor = null;

        switch (cmd) {
            case KeyEvent.NEW_ROOM:
                processor = new NewRoomCmdProcessor(cmd, session);
                break;
            case KeyEvent.JOIN_ROOM:
                processor = new JoinRoomCmdProcessor(cmd, session, data, RoomReq.class);
                break;
            case KeyEvent.EXIT_ROOM:
                processor = new ExitRoomCmdProcessor(cmd, session, data, RoomReq.class);
                break;
            case KeyEvent.GET_QUESTION:
                processor = new GetQuestionCmdPrs(cmd, session, data, QuestionReq.class);
                break;
            case KeyEvent.ANSWER:
                processor = new AnswerQuestionCmdPrs(cmd, session);
                break;
            default:
                session.getBasicRemote().sendText(GsonUtils.toJson(new BaseResponse(ErrorCode.UN_SUPPORT, cmd)));
                break;
        }

        if (processor != null) {
            processor.execute();
        }
    }
}