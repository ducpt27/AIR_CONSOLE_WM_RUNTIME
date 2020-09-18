package com.airconsole.wm.handle.processor.question;

import com.airconsole.wm.handle.processor.BaseProcessor;

import javax.websocket.Session;

public class AnswerQuestionCmdPrs extends BaseProcessor {

    public AnswerQuestionCmdPrs(String cmd, Session session) {
        super(cmd, session);
    }

    @Override
    public void handle() throws Exception {

    }
}
