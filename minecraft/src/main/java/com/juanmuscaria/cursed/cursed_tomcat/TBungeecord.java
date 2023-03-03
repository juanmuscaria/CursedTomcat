package com.juanmuscaria.cursed.cursed_tomcat;

import net.md_5.bungee.api.plugin.Plugin;

import java.nio.file.Paths;

public class TBungeecord extends Plugin {

    @Override
    public void onEnable() {
        startTomcat();
    }

    @Override
    public void onDisable() {
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
            tomcat = TomcatBootstrap.configure(Paths.get(getDataFolder().getAbsolutePath()));
            tomcat.start();
            return true;
        } catch (Exception e) {
            getLogger().severe("Unable to initialize Apache Tomcat!");
            e.printStackTrace();
        }
        return false;
    }
}
