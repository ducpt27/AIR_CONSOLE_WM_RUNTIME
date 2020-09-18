package com.airconsole.wm.handle.data.request;

import com.airconsole.wm.handle.data.BaseRequest;
import lombok.Data;
import lombok.ToString;

@Data
@ToString
public class RoomReq extends BaseRequest {
    private Long room_id;
}
