package com.airconsole.wm.handle.processor;

import com.airconsole.wm.dictionary.ErrorCode;
import com.airconsole.wm.handle.data.BaseResponse;
import com.airconsole.wm.utils.GsonUtils;
import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;

import javax.websocket.Session;
import java.io.IOException;

public abstract class BaseProcessor {
    protected Session session;
    protected String cmd;
    protected final Log logger = LogFactory.getLog(this.getClass());
    protected long begin;
    protected String jsonRequest;
    protected Class<?> classRequest;
    protected Object objRequest;

    public BaseProcessor(String cmd, Session session) {
        this.cmd = cmd;
        this.session = session;
        this.begin = System.currentTimeMillis();
    }

    public BaseProcessor(String cmd, Session session, String jsonRequest, Class<?> classRequest) {
        this.session = session;
        this.cmd = cmd;
        this.jsonRequest = jsonRequest;
        this.classRequest = classRequest;
    }

    public void execute() {

        try {
            objRequest = GsonUtils.fromJson(jsonRequest, classRequest);
            if (objRequest == null) {
                send(new BaseResponse(ErrorCode.REQUEST_INVALID, cmd));
            }

        } catch (Exception e) {
            logger.warn(e.getMessage(), e);
            return;
        }

        try {
            if (!validateData()) {
                logger.info(String.format("[%s] invalid in %s ms by %s", cmd, (System.currentTimeMillis() - begin), (session == null ? "unknown" : session.getId())));
                send(new BaseResponse(999, "REQUEST INVALID"));
                return;
            }
            handle();
        } catch (Exception e) {
            logger.warn(String.format("[%s] throw exception caused by %s", (session == null ? "unknown" : session.getId()), e));
            e.printStackTrace();
        }
        logger.info(String.format("[%s] completed in %s ms by %s", cmd, (System.currentTimeMillis() - begin), (session == null ? "unknown" : session.getId())));
    }

    public abstract void handle() throws Exception;

    protected void send(Object objResp) throws IOException {
        String strResponse = GsonUtils.toJson(objResp);
        session.getBasicRemote().sendText(strResponse);
    }

    protected void sendTo(Object data, Session receiver) throws IOException {
        String strResponse = GsonUtils.toJson(data);
        receiver.getBasicRemote().sendText(strResponse);
    }

    protected boolean validateData() throws Exception{
        return true;
    }
}
