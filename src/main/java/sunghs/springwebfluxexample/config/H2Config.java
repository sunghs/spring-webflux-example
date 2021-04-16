package sunghs.springwebfluxexample.config;

import java.sql.SQLException;
import org.h2.tools.Server;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.Profile;
import org.springframework.context.event.ContextClosedEvent;
import org.springframework.context.event.ContextRefreshedEvent;
import org.springframework.context.event.EventListener;

@Configuration
@Profile("local")
public class H2Config {

    private final Server webServer;
    private final Server tcpServer;

    public H2Config() throws SQLException {
        webServer = Server.createWebServer("-webPort", "8081", "-tcpAllowOthers");
        tcpServer = Server.createTcpServer("-tcpPort", "9091", "-tcpAllowOthers");
    }

    @EventListener(ContextRefreshedEvent.class)
    public void h2Start() throws SQLException {
        webServer.start();
        tcpServer.start();
    }

    @EventListener(ContextClosedEvent.class)
    public void h2Stop() {
        webServer.stop();
        tcpServer.stop();
    }
}
