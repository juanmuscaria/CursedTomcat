package com.juanmuscaria.cursed.cursed_tomcat.forgeCompat;

import com.juanmuscaria.cursed.cursed_tomcat.TForge;
import net.minecraftforge.fml.common.event.FMLServerAboutToStartEvent;
import net.minecraftforge.fml.common.event.FMLServerStoppingEvent;
import net.minecraftforge.fml.common.eventhandler.SubscribeEvent;

public class Forge1_12 extends Forge {
    public Forge1_12(TForge instance) {
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
