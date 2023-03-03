package com.juanmuscaria.cursed.cursed_tomcat.forgeCompat;

import com.juanmuscaria.cursed.cursed_tomcat.TForge;
import net.minecraftforge.eventbus.api.SubscribeEvent;
import net.minecraftforge.fml.event.server.FMLServerAboutToStartEvent;
import net.minecraftforge.fml.event.server.FMLServerStoppingEvent;

public class Forge1_15 extends Forge {
    public Forge1_15(TForge instance) {
        super(instance);
    }

    @SubscribeEvent
    public void onServerStart(FMLServerAboutToStartEvent event) {
        instance.startTomcat();
    }

    @SubscribeEvent
    public void onServerStop(FMLServerStoppingEvent event) {
        instance.stopSilently();
    }
}
