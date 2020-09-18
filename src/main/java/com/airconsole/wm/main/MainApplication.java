package com.airconsole.wm.main;

import com.airconsole.wm.config.MonitorConfigurator;
import com.airconsole.wm.config.ServerConfig;
import com.airconsole.wm.config.ThreadConfigurator;
import com.airconsole.wm.handle.manager.HandlerPool;
import com.airconsole.wm.schedule.ThreadCleanRoomNotUsed;
import com.airconsole.wm.schedule.ThreadMonitorSystem;
import com.airconsole.wm.server.TyrusServer;
import com.airconsole.wm.thread.AbsThread;

public class MainApplication {
    public static TyrusServer server;
    public static HandlerPool handlerPool;
    public static ThreadCleanRoomNotUsed threadCleanRoomNotUsed;
    public static ThreadMonitorSystem threadMonitorSystem;

    public static void main(String[] args) {

        try{
            enableConfigurators();
            enableThreadPools();
            enableServer();
        }
        catch (Exception e){
            e.printStackTrace();
            shutdown();
        }
    }

    public static void enableConfigurators() throws Exception{
        ServerConfig serverConfig = new ServerConfig();
        serverConfig.start();
        serverConfig.join();

        ThreadConfigurator threadConfigurator = new ThreadConfigurator("THREAD_CONFIGURATION","thread.xml");
        threadConfigurator.start();
        threadConfigurator.join();

        MonitorConfigurator monitorConfigurator = new MonitorConfigurator("MONITOR_CONFIGURATION","monitor.xml",10000);
        monitorConfigurator.start();
    }

    public static void enableServer() throws Exception{
        server = new TyrusServer();
        server.start();
    }

    public static void shutdown(){
        server.shutdown();
        AbsThread.stopAllThread();
    }

    public static void enableThreadPools() throws Exception{
        handlerPool = new HandlerPool(ThreadConfigurator.EVENT_GATEWAY_HANDLER_NUMBER);
        handlerPool.init();
        handlerPool.start();

        threadCleanRoomNotUsed = new ThreadCleanRoomNotUsed(ThreadConfigurator.CLEAN_ROOM_DELAY);
        threadCleanRoomNotUsed.start();

        threadMonitorSystem = new ThreadMonitorSystem();
        threadMonitorSystem.start();
    }
}
