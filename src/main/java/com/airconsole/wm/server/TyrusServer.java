package com.airconsole.wm.server;

import com.airconsole.wm.config.ServerConfig;
import com.airconsole.wm.event.EventGateway;
import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.glassfish.tyrus.server.Server;

import java.util.HashSet;
import java.util.Set;

public class TyrusServer {
    private final Log logger = LogFactory.getLog(this.getClass());
    private Server server;

    public void start() {
        Set<Class<?>> endpoints = new HashSet<>();
        endpoints.add(EventGateway.class);

        logger.info(String.format("[SOCKET ENDPOINT REGISTER] - %s", endpoints.toString()));
        server = new Server(ServerConfig.HOST_NAME, ServerConfig.PORT, ServerConfig.CONTEXT_PATH, null, endpoints);
        try {
            server.start();
            logger.info("============= SOCKET SERVER STARTED ==============");
            System.in.read();
        } catch (Exception ex) {
            logger.warn(ex);
            server.stop();
        }
    }

    public void shutdown() {
        server.stop();
    }
}
