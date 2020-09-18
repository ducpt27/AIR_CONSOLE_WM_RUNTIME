package com.airconsole.wm.schedule;

import com.airconsole.wm.config.MonitorConfigurator;
import com.airconsole.wm.config.ThreadConfigurator;
import com.airconsole.wm.handle.manager.SessionManager;
import com.airconsole.wm.schedule.data.RuntimeInformation;
import com.airconsole.wm.thread.AbsThread;

public class ThreadMonitorSystem extends AbsThread {

    public ThreadMonitorSystem(){
        super(true, ThreadConfigurator.MONITOR_LOG_DELAY);
        this.setName("MONITOR_SYSTEM");
    }

    @Override
    protected void execute() throws Exception {
        Runtime runtime = Runtime.getRuntime();
        int availableProcessors = runtime.availableProcessors();
        long freeMemory = runtime.freeMemory() / 1024;
        long maxMemory = runtime.maxMemory() / 1024;
        long totalMemory = runtime.totalMemory() / 1024;
        int totalRoom = SessionManager.roomMap.size();
        int totalUser = SessionManager.roomMap.values().stream().mapToInt(room -> room.getMemberList().size()).sum();

        RuntimeInformation runtimeInformation = new RuntimeInformation();
        runtimeInformation.setAvailableProcessors(availableProcessors);
        runtimeInformation.setFreeMemory(freeMemory);
        runtimeInformation.setMaxMemory(maxMemory);
        runtimeInformation.setTotalMemory(totalMemory);
        runtimeInformation.setTotalRoom(totalRoom);
        runtimeInformation.setTotalUser(totalUser);

        if (MonitorConfigurator.SHOW_LOG == 1){
            logger.debug("+------------------------------------------------------------------------------------------------ +");
            logger.debug("| Available Processors | Free Memory | Max Memory | Total Memory | Total Room | Total User Online |");
            logger.debug("+------------------------------------------------------------------------------------------------ +");
            logger.debug(String.format("| %20s | %11s | %10s | %12s | %10s | %17s |",
                    availableProcessors, freeMemory, maxMemory, totalMemory, totalRoom, totalUser));
            logger.debug("+------------------------------------------------------------------------------------------------ +");
        }
    }
}
