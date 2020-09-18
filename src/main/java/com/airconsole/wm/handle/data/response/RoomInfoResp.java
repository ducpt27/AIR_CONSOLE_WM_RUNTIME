package com.airconsole.wm.handle.data.response;

import com.airconsole.wm.handle.manager.data.Member;
import lombok.Data;
import lombok.NoArgsConstructor;
import lombok.ToString;

import java.util.*;

@Data
@ToString
@NoArgsConstructor
public class RoomInfoResp {
    private Long room_id;
    private List<MemberInfoResp> member_list;

    public RoomInfoResp(Long room_id) {
        this.room_id = room_id;
    }

    public void setMember_list(Collection<Member> memberList) {
        member_list = new ArrayList<>();
        memberList.forEach(p -> {
            MemberInfoResp obj = new MemberInfoResp();
            obj.setIs_admin(p.getIs_admin());
            obj.setName(p.getName());
            member_list.add(obj);
        });

    }
}
