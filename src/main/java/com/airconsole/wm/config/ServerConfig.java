package com.airconsole.wm.config;

public class ServerConfig extends AbsConfigurator{
    public static String HOST_NAME;
    public static int PORT;
    public static String CONTEXT_PATH;

    public ServerConfig() {
        super("SERVER_CONFIGURATION", baseFilePath + "server.xml");
    }

    @Override
    public void readConfig() throws Exception {
        PORT = getInt("PORT",8080);
        HOST_NAME = getString("HOST_NAME", HOST_NAME);
        CONTEXT_PATH = getString("CONTEXT_PATH","");
    }
}
