package com.juanmuscaria.cursed.cursed_tomcat;

import com.google.inject.Inject;
import org.slf4j.Logger;
import org.spongepowered.api.config.ConfigDir;
import org.spongepowered.api.event.Listener;
import org.spongepowered.api.event.game.state.GameStartedServerEvent;
import org.spongepowered.api.event.game.state.GameStoppedServerEvent;
import org.spongepowered.api.plugin.Plugin;

import java.nio.file.Path;

@Plugin(id = "tomcat4sponge",
        name = "Tomcat4Sponge",
        description = "Run tomcat on your minecraft server")
public class TSponge {
    @Inject
    private Logger logger;
    @Inject
    @ConfigDir(sharedRoot = false)
    private Path pluginDir;

    @Listener
    public void onServerStart(GameStartedServerEvent event) {
        startTomcat();
    }

    @Listener
    public void onServerStop(GameStoppedServerEvent event) {
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
            tomcat = TomcatBootstrap.configure(pluginDir, true);
            tomcat.start();
            return true;
        } catch (Exception e) {
            logger.error("Unable to initialize Apache Tomcat!");
            e.printStackTrace();
        }
        return false;
    }
}
