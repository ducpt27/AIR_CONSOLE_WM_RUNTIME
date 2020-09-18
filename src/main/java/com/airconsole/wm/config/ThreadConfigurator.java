package com.airconsole.wm.config;

public class ThreadConfigurator extends AbsConfigurator {
    public static int EVENT_GATEWAY_HANDLER_NUMBER = 10;
    public static int ROOM_TIME_IDLE = 30;
    public static long CLEAN_ROOM_DELAY = 60000;
    public static long MONITOR_LOG_DELAY = 1000;

    public ThreadConfigurator(String name, String pathFile) {
        super(name, baseFilePath + pathFile);
    }

    @Override
    public void readConfig() throws Exception {
        EVENT_GATEWAY_HANDLER_NUMBER = getInt("ROOM_EVENT_HANDLER[0].SIZE", EVENT_GATEWAY_HANDLER_NUMBER);
        ROOM_TIME_IDLE = getInt("ROOM_TIME_IDLE", ROOM_TIME_IDLE);
        CLEAN_ROOM_DELAY = getLong("CLEAN_ROOM_DELAY", CLEAN_ROOM_DELAY);
        MONITOR_LOG_DELAY = getLong("MONITOR_LOG_DELAY", MONITOR_LOG_DELAY);
    }
}
