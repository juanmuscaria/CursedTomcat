package com.juanmuscaria.cursed.cursed_tomcat.forgeCompat;

import com.juanmuscaria.cursed.cursed_tomcat.TForge;
import net.minecraftforge.event.server.ServerStartingEvent;
import net.minecraftforge.event.server.ServerStoppingEvent;
import net.minecraftforge.eventbus.api.SubscribeEvent;

public class Forge1_18 extends Forge {
    public Forge1_18(TForge instance) {
        super(instance);
    }

    @SubscribeEvent
    public void onServerStarting(ServerStartingEvent event) {
        instance.startTomcat();
    }

    @SubscribeEvent
    public void onServerStopping(ServerStoppingEvent event) {
        instance.stopSilently();
    }

}
