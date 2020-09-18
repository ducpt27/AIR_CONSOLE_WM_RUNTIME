package com.airconsole.wm.event;

import com.airconsole.wm.dictionary.KeyEvent;
import com.airconsole.wm.handle.data.EmitData;
import com.airconsole.wm.utils.GsonUtils;
import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;

import javax.websocket.*;
import javax.websocket.server.ServerEndpoint;
import java.util.concurrent.LinkedBlockingQueue;

@ServerEndpoint(value = "/ws")
public class EventGateway {
    protected Log logger = LogFactory.getLog(this.getClass());
    public static LinkedBlockingQueue<EmitData> emitDataQueue = new LinkedBlockingQueue<>();

    @OnOpen
    public void onOpen(Session session) throws Exception {
        logger.info(String.format("New session [%s] connected to server !",session.getId()));
    }

    @OnMessage
    public void onMessage(String message, Session session) throws Exception {
        EmitData emitData = GsonUtils.fromJson(message, EmitData.class);
        if (emitData == null)
            return;
        emitData.setSession(session);
        emitDataQueue.put(emitData);
    }

    @OnClose
    public void onClose(Session session) throws Exception {
        EmitData emitData = new EmitData();
        emitData.setSession(session);
        emitData.setCmd(KeyEvent.EXIT_ROOM);
        emitDataQueue.put(emitData);

        logger.info(String.format("Session [%s] disconnected to server !",session.getId()));
    }

    @OnError
    public void onError(Throwable error){
        error.printStackTrace();
    }

}
