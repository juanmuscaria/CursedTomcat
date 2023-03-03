package com.juanmuscaria.cursed.cursed_tomcat;

import net.fabricmc.api.ModInitializer;

import java.nio.file.Paths;

public class TFabric implements ModInitializer {
    @Override
    public void onInitialize() {
        startTomcat();
        // TODO: Safe way to stop tomcat on fabric?
    }

    ITomcatContainer tomcat;
    private void stopSilently() {
        try {
            tomcat.stopAndAwait();
        } catch (Exception ignored) {}
    }

    private boolean startTomcat() {
        try {
            tomcat = TomcatBootstrap.configure(Paths.get("tomcat4fabric"), true);
            tomcat.start();
            return true;
        } catch (Exception e) {
            System.err.println("[Tomcat4Fabric] Unable to initialize Apache Tomcat!");
            e.printStackTrace();
        }
        return false;
    }
}
