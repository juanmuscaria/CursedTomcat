package com.juanmuscaria.cursed.cursed_tomcat.forgeCompat;

import com.juanmuscaria.cursed.cursed_tomcat.TForge;

public abstract class Forge {
    protected final TForge instance;

    public Forge(TForge instance) {
        this.instance = instance;
    }
}
