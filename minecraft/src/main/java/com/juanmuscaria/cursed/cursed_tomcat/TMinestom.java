package com.juanmuscaria.cursed.cursed_tomcat;

import net.minestom.server.extensions.Extension;

public class TMinestom extends Extension {

    @Override
    public void initialize() {
        startTomcat();
    }

    @Override
    public void terminate() {
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
            tomcat = TomcatBootstrap.configure(getDataDirectory());
            tomcat.start();
            return true;
        } catch (Exception e) {
            getLogger().error("Unable to initialize Apache Tomcat!");
            e.printStackTrace();
        }
        return false;
    }
}
