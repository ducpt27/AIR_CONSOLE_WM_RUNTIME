package com.airconsole.wm.config;

public class MonitorConfigurator extends AbsConfigurator{
    public static int SHOW_LOG = 0;

    public MonitorConfigurator(String name, String pathFile, long delayTime) {
        super(name, baseFilePath + pathFile, delayTime);
    }

    @Override
    public void readConfig() throws Exception {
        SHOW_LOG = getInt("SHOW_LOG", SHOW_LOG);
    }
}
