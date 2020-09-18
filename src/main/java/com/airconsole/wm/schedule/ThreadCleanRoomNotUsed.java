package com.airconsole.wm.schedule;

import com.airconsole.wm.config.ThreadConfigurator;
import com.airconsole.wm.handle.manager.SessionManager;
import com.airconsole.wm.handle.manager.data.Room;
import com.airconsole.wm.thread.AbsThread;

import java.util.Set;

public class ThreadCleanRoomNotUsed extends AbsThread {

    private long time_clean = 60000;

    public ThreadCleanRoomNotUsed(long time_clean) {
        super(true, 1000);
        if (time_clean > 0) {
            this.time_clean = time_clean;
        }
        this.setName("CLEAN_ROOM_NOT_USED");
    }

    @Override
    protected void execute() throws Exception {
        delay = ThreadConfigurator.CLEAN_ROOM_DELAY;
        Set<Long> keySet = SessionManager.roomMap.keySet();
        for (Long key : keySet) {
            Room room = SessionManager.roomMap.get(key);
            if (room.getMemberList() == null || room.getMemberList().isEmpty()) {
                long time_live = System.currentTimeMillis() - room.getCreate_time();
                if (time_live > (ThreadConfigurator.ROOM_TIME_IDLE * time_clean)) {
                    room.setIs_closed(1);
                }
            }
        }

        SessionManager.roomMap.keySet().forEach(key ->{
           if(SessionManager.roomMap.get(key).getIs_closed() == 1){
               SessionManager.roomMap.remove(key);
           }
        });
    }
}
