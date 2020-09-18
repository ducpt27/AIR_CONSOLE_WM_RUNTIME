package com.airconsole.wm.handle.data;

import lombok.Data;
import lombok.ToString;

@Data
@ToString
public class BaseResponse {
    private int code;
    private String cmd;
    private Object data;

    public BaseResponse(int code, String cmd) {
        this.code = code;
        this.cmd = cmd;
    }

    public BaseResponse(String cmd, Object data) {
        this.cmd = cmd;
        this.data = data;
    }

    public BaseResponse(int code, String cmd, Object data) {
        this.code = code;
        this.cmd = cmd;
        this.data = data;
    }
}
