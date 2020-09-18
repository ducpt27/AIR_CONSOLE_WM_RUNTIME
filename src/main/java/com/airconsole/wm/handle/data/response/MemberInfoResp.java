package com.airconsole.wm.handle.data.response;

import lombok.Data;
import lombok.ToString;

@Data
@ToString
public class MemberInfoResp {
    private int is_admin;
    private String name;
}
