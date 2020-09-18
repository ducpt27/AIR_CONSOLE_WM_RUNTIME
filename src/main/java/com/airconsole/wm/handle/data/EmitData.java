package com.airconsole.wm.handle.data;

import lombok.Data;
import lombok.ToString;

import javax.websocket.Session;

@Data
@ToString
public class EmitData {
    private String cmd;
    private String data;
    private Session session;
}
