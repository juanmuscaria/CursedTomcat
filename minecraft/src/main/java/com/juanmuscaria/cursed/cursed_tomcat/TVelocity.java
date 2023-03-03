package com.juanmuscaria.cursed.cursed_tomcat;

import com.google.inject.Inject;
import com.velocitypowered.api.event.Subscribe;
import com.velocitypowered.api.event.proxy.ProxyInitializeEvent;
import com.velocitypowered.api.event.proxy.ProxyShutdownEvent;
import com.velocitypowered.api.plugin.Plugin;
import com.velocitypowered.api.plugin.annotation.DataDirectory;
import org.slf4j.Logger;

import java.nio.file.Path;

@Plugin(id = "tomcat4velocity",
        name = "Tomcat4Velocity",
        description = "Run tomcat on your proxy",
        version = Metadata.VERSION,
        authors = "juanmuscaria")
public class TVelocity {

    private final Logger logger;
    Path dataDirectory;

    @Inject
    public TVelocity(Logger logger, @DataDirectory Path dataDirectory) {
        this.logger = logger;
        this.dataDirectory = dataDirectory;
    }

    @Subscribe
    public void onProxyInitialization(ProxyInitializeEvent event) {
        startTomcat();
    }

    @Subscribe
    public void onProxyShutdown(ProxyShutdownEvent event) {
        stopSilently();
    }

    ITomcatContainer tomcat;
    private void stopSilently() {
        try {
            tomcat.stopAndAwait();
        } catch (Exception ignored) {}
    }

    private boolean startTomcat() {
        try {
            tomcat = TomcatBootstrap.configure(dataDirectory);
            tomcat.start();
            return true;
        } catch (Exception e) {
            logger.error("Unable to initialize Apache Tomcat!");
            e.printStackTrace();
        }
        return false;
    }
}
