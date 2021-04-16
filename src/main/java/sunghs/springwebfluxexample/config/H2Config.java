package sunghs.springwebfluxexample.config;

import java.sql.SQLException;
import org.h2.tools.Server;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.event.ContextClosedEvent;
import org.springframework.context.event.ContextRefreshedEvent;
import org.springframework.context.event.EventListener;

@Configuration
public class H2Config {

    private final Server server;

    public H2Config() throws SQLException {
        server = Server.createWebServer("-webPort", "8081", "-tcpAllowOthers");
    }

    @EventListener(ContextRefreshedEvent.class)
    public void h2Start() throws SQLException {
        server.start();
    }

    @EventListener(ContextClosedEvent.class)
    public void h2Stop() {
        server.stop();
    }
}
