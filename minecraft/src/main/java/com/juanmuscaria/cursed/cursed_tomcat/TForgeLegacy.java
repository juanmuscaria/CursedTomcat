package com.juanmuscaria.cursed.cursed_tomcat;

import cpw.mods.fml.common.Mod;
import cpw.mods.fml.common.event.FMLServerAboutToStartEvent;
import cpw.mods.fml.common.event.FMLServerStoppingEvent;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

import java.nio.file.Paths;

@Mod(modid = "tomcat4forge", name = "Tomcat4Forge")
public class TForgeLegacy {
    private static final Logger logger = LogManager.getLogger();


    @Mod.EventHandler
    public void onServerStart(FMLServerAboutToStartEvent event) {
        startTomcat();
    }

    @Mod.EventHandler
    public void onServerStop(FMLServerStoppingEvent event) {
        stopSilently();
    }


    ITomcatContainer tomcat;
    private void stopSilently() {
        org.apache.catalina.util.LifecycleMBeanBase
        try {
            tomcat.stopAndAwait();
        } catch (Exception ignored) {}
    }

    private boolean startTomcat() {
        try {
            tomcat = TomcatBootstrap.configure(Paths.get("tomcat4forge"), true);
            tomcat.start();
            return true;
        } catch (Exception e) {
            logger.error("Unable to initialize Apache Tomcat!");
            e.printStackTrace();
        }
        return false;
    }
}
