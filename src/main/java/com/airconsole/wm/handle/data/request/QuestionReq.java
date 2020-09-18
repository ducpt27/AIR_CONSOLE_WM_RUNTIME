package com.airconsole.wm.handle.data.request;

import com.airconsole.wm.handle.data.BaseRequest;
import lombok.Data;
import lombok.ToString;

@Data
@ToString
public class QuestionReq extends BaseRequest {
    private int room_id;
    private int level;
}
