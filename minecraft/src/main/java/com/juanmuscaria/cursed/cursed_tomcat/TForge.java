package com.juanmuscaria.cursed.cursed_tomcat;

import com.juanmuscaria.cursed.cursed_tomcat.forgeCompat.Forge1_12;
import com.juanmuscaria.cursed.cursed_tomcat.forgeCompat.Forge1_15;
import com.juanmuscaria.cursed.cursed_tomcat.forgeCompat.Forge1_18;
import net.minecraftforge.common.MinecraftForge;
import net.minecraftforge.fml.common.Mod;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

import java.nio.file.Paths;

@Mod(value = "tomcat4forge",
        modid = "tomcat4forge",
        name = "Tomcat4Forge")
public class TForge {
    private static final Logger logger = LogManager.getLogger();
    boolean isolate = true;

    public TForge() {
        try {
            Object eventBus = MinecraftForge.class.getDeclaredField("EVENT_BUS").get(null);

            if (classExist("net.minecraftforge.fml.common.event.FMLServerAboutToStartEvent")) {
                eventBus.getClass().getMethod("register", Object.class).invoke(eventBus, new Forge1_12(this));
            } else if (classExist("net.minecraftforge.fml.event.server.FMLServerAboutToStartEvent")) {
                eventBus.getClass().getMethod("register", Object.class).invoke(eventBus, new Forge1_15(this));
            } else if (classExist("net.minecraftforge.event.server.ServerStartingEvent")) {
                eventBus.getClass().getMethod("register", Object.class).invoke(eventBus, new Forge1_18(this));
            } else {
                throw new IllegalStateException("This forge version is not compatible!");
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
    }


    ITomcatContainer tomcat;
    public void stopSilently() {
        try {
            tomcat.stopAndAwait();
        } catch (Exception ignored) {}
    }

    public boolean startTomcat() {
        try {
            tomcat = TomcatBootstrap.configure(Paths.get("tomcat4forge"), isolate, true);
            tomcat.start();
            return true;
        } catch (Exception e) {
            logger.error("Unable to initialize Apache Tomcat!");
            e.printStackTrace();
        }
        return false;
    }

    public static boolean classExist(String className) {
        try {
            Class.forName(className);
            return true;
        } catch (Throwable e) {
            return false;
        }
    }
}
